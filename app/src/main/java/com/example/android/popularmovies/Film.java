package com.example.android.popularmovies;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by zukka on 20/03/18.
 */

public class Film extends BaseAdapter {
    private String Title;
    private String PosterURL;
    private String OverView;
    private String VoteAverage;
    private String ReleaseDate;

    public Film(String Title, String PosterURL, String OverView, String VoteAverage, String ReleaseDate)
    {
        this.Title = Title;
        this.PosterURL = PosterURL;
        this.OverView = OverView;
        this.VoteAverage = VoteAverage;
        this.ReleaseDate = ReleaseDate;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
