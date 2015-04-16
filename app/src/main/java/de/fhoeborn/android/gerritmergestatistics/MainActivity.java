package de.fhoeborn.android.gerritmergestatistics;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.achartengine.model.CategorySeries;

import de.fhoeborn.android.gerritmergestatistics.service.GerritStatisticsService;
import de.fhoeborn.android.gerritmergestatistics.service.ReviewerStatisticsDatabase;
import de.fhoeborn.android.gerritmergestatistics.service.ReviewerStatisticsProvider;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID_STATISTICS = 0;

    private ListView list;
    private ProgressBar progBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminate(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportLoaderManager().initLoader(LOADER_ID_STATISTICS, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            startStatistics();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startStatistics() {
        setProgressBarIndeterminateVisibility(true);
        GerritStatisticsService.startActionForNChanges(this, 50);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
        switch (loaderID) {
            case LOADER_ID_STATISTICS:
                return new CursorLoader(
                        this,
                        ReviewerStatisticsProvider.BASE_URI,
                        new String[]{ReviewerStatisticsDatabase.COLUMN_ID, ReviewerStatisticsDatabase.COLUMN_REVIEWER_NAME, ReviewerStatisticsDatabase.COLUMN_AMOUNT},
                        null,
                        null,
                        ReviewerStatisticsDatabase.COLUMN_AMOUNT + " DESC"
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        setProgressBarIndeterminateVisibility(false);
        CategorySeries categories = new CategorySeries("Some title");

        data.moveToFirst();
        while (data.moveToNext()) {
            categories.add(data.getString(data.getColumnIndex(ReviewerStatisticsDatabase.COLUMN_REVIEWER_NAME)), data.getInt(data.getColumnIndex(ReviewerStatisticsDatabase.COLUMN_AMOUNT)));

        }
        View view = PieChartView.getNewInstance(this, categories);
        FrameLayout layout = (FrameLayout) findViewById(R.id.chart);
        layout.addView(view);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Nothing to do atm
    }
}
