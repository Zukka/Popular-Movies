package com.example.android.popularmovies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Favorite {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "film_title")
    private String filmTitle;

    @ColumnInfo(name = "film_poster_url")
    private String filmPosterUrl;

    @ColumnInfo(name = "film_overview")
    private String filmOverView;

    @ColumnInfo(name = "film_vote_average")
    private String filmVoteAverage;

    @ColumnInfo(name = "film_release_date")
    private String filmReleaseDate;

}
