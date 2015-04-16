package de.fhoeborn.android.gerritmergestatistics.REST;

import com.google.gson.annotations.SerializedName;

public class Reviewer {

    @SerializedName("_account_id")
    private long accountId;
    private String name;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reviewer reviewer = (Reviewer) o;

        return accountId == reviewer.accountId;

    }

    @Override
    public int hashCode() {
        return (int) (accountId ^ (accountId >>> 32));
    }

    @Override
    public String toString() {
        return accountId + name;
    }
}
