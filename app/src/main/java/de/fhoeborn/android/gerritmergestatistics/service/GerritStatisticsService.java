package de.fhoeborn.android.gerritmergestatistics.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;

import java.util.List;
import java.util.Map;

import de.fhoeborn.android.gerritmergestatistics.REST.ChangeInfo;
import de.fhoeborn.android.gerritmergestatistics.REST.GerritService;
import de.fhoeborn.android.gerritmergestatistics.REST.ReviewAmount;
import de.fhoeborn.android.gerritmergestatistics.REST.Reviewer;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class GerritStatisticsService extends IntentService {

    private static final String ACTION_FOR_N_CHANGES = "de.fhoeborn.android.gerritmergestatistics.service.action.FOR_N_CHANGES";

    private static final String EXTRA_CHANGES = "de.fhoeborn.android.gerritmergestatistics.service.extra.AMOUNT_CHANGES";

    private GerritService service;

    public static void startActionForNChanges(Context context, int amountChanges) {
        Intent intent = new Intent(context, GerritStatisticsService.class);
        intent.setAction(ACTION_FOR_N_CHANGES);
        intent.putExtra(EXTRA_CHANGES, amountChanges);
        context.startService(intent);
    }


    public GerritStatisticsService() {
        super("GerritStatisticsService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://git.eclipse.org/r")
                .build();
        service = restAdapter.create(GerritService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOR_N_CHANGES.equals(action)) {
                final int amountChanges = intent.getIntExtra(EXTRA_CHANGES, 25);
                handleActionForNChanges(amountChanges);
            }
        }
    }

    private void handleActionForNChanges(int amountOfChanges) {
        ReviewAmount reviewAmount = new ReviewAmount();
        List<ChangeInfo> reviewedChanges = service.getReviewedChanges(amountOfChanges);

        for (ChangeInfo change : reviewedChanges) {
            List<Reviewer> reviewers;
            try {
                reviewers = service.getReviewerForChange(change.getChangeId());
            } catch (RetrofitError error) {
                // No reviewers for that change, ignore change
                continue;
            }
            for (Reviewer reviewer : reviewers) {
                reviewAmount.increaseAmount(reviewer);
            }
        }
        persist(reviewAmount);
    }

    private void persist(ReviewAmount reviewAmount) {
        getContentResolver().delete(ReviewerStatisticsProvider.BASE_URI, null, null);

        ContentValues values = new ContentValues();
        Map<Reviewer, Integer> map = reviewAmount.getMap();
        for (Reviewer reviewer : map.keySet()) {
            values.put(ReviewerStatisticsDatabase.COLUMN_REVIEWER_NAME, reviewer.getName());
            values.put(ReviewerStatisticsDatabase.COLUMN_AMOUNT, map.get(reviewer));
        }
        getContentResolver().insert(ReviewerStatisticsProvider.BASE_URI, values);
    }
}
