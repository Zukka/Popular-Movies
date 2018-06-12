package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.nio.channels.InterruptibleChannel;

public class DetailsActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent detailsIntent = getIntent();
        String filmTitle = detailsIntent.getStringExtra("FilmName");
        String filmPosterURL = detailsIntent.getStringExtra("FilmPosterURL");
        String filmOverView = detailsIntent.getStringExtra("FilmOverView");
        String filmVoteAverage = detailsIntent.getStringExtra("FilmVoteAverage");
        String filmReleaseDate = detailsIntent.getStringExtra("FilmReleaseDate");

    }
}
