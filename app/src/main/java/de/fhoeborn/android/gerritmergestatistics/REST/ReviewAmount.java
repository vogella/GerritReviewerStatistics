package de.fhoeborn.android.gerritmergestatistics.REST;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewAmount {
    private Map<Reviewer, Integer> reviewAndAmount = new HashMap<>();


    public void increaseAmount(Reviewer reviewer) {
        int amount = 1;
        if (reviewAndAmount.containsKey(reviewer)) {
            amount = amount + reviewAndAmount.get(reviewer);
            reviewAndAmount.remove(reviewer);
        }

        reviewAndAmount.put(reviewer, amount);
    }

    public List<String> toStringList() {
        List<String> result = new ArrayList<>();

        for (Reviewer reviewer : reviewAndAmount.keySet()) {
            result.add(reviewer.getName() + " - " + reviewAndAmount.get(reviewer));
        }

        return result;
    }

    public Map<Reviewer, Integer> getMap(){
        return Collections.unmodifiableMap(reviewAndAmount);
    }
}
