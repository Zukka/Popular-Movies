package com.example.android.popularmovies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "film_id")
    private int filmId;

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

    public Favorite(int id, int filmId, String filmTitle, String filmPosterUrl, String filmOverView, String filmVoteAverage, String filmReleaseDate) {
        this.id = id;
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.filmPosterUrl = filmPosterUrl;
        this.filmOverView = filmOverView;
        this.filmVoteAverage = filmVoteAverage;
        this.filmReleaseDate = filmReleaseDate;
    }

    @Ignore
    public Favorite(int filmId, String filmTitle, String filmPosterUrl, String filmOverView, String filmVoteAverage, String filmReleaseDate) {
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.filmPosterUrl = filmPosterUrl;
        this.filmOverView = filmOverView;
        this.filmVoteAverage = filmVoteAverage;
        this.filmReleaseDate = filmReleaseDate;
    }

    public int getId() { return id; }
    public int getFilmId() { return filmId; }
    public String getFilmTitle() { return filmTitle; }
    public String getFilmOverView() { return filmOverView; }
    public String getFilmPosterUrl() { return filmPosterUrl; }
    public String getFilmReleaseDate() { return filmReleaseDate;}
    public String getFilmVoteAverage() { return filmVoteAverage; }

    public void setId(int id) { this.id = id; }
    public void setFilmId(int filmId) { this.filmId = filmId; }
    public void setFilmTitle(String filmTitle) {this.filmTitle = filmTitle; }
    public void setFilmOverView(String filmOverView) { this.filmOverView = filmOverView; }
    public void setFilmPosterUrl(String filmPosterUrl) { this.filmPosterUrl = filmPosterUrl; }
    public void setFilmReleaseDate(String filmReleaseDate) { this.filmReleaseDate = filmReleaseDate; }
    public void setFilmVoteAverage(String filmVoteAverage) { this.filmVoteAverage = filmVoteAverage; }
}
