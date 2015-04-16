package de.fhoeborn.android.gerritmergestatistics.REST;


import com.google.gson.annotations.SerializedName;

public class ChangeInfo {

    @SerializedName("change_id")
    private String changeId;

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }
}
