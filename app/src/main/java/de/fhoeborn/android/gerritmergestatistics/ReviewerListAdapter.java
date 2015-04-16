package de.fhoeborn.android.gerritmergestatistics;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import de.fhoeborn.android.gerritmergestatistics.REST.Reviewer;

public class ReviewerListAdapter extends ArrayAdapter<Reviewer>{

    public ReviewerListAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
