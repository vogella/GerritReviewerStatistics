package de.fhoeborn.android.gerritmergestatistics.REST;


import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface GerritService {
    @GET("/changes/?q=status:merged")
    List<ChangeInfo> getReviewedChanges(@Query("n") int amountOfChanges);

    @GET("/changes/{change-id}/reviewers")
    List<Reviewer> getReviewerForChange(@Path("change-id") String changeId);

}
