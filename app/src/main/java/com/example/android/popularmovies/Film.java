package com.example.android.popularmovies;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by zukka on 20/03/18.
 */

public class Film {
    String Title;
    String PosterURL;
    String OverView;
    String VoteAverage;
    String ReleaseDate;

    public Film(String Title, String PosterURL, String OverView, String VoteAverage, String ReleaseDate)
    {
        this.Title = Title;
        this.PosterURL = PosterURL;
        this.OverView = OverView;
        this.VoteAverage = VoteAverage;
        this.ReleaseDate = ReleaseDate;
    }
}
