package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        TextView _filmTitle = findViewById(R.id.detailFilmTitle);
        _filmTitle.setText(filmTitle);

        TextView _filmOverView = findViewById(R.id.detailFilmOverView);
        _filmOverView.setText(filmOverView);

        TextView _filmVoteAverage = findViewById(R.id.detailFilmAverage);
        _filmVoteAverage.setText(String.format("%s %s", getString(R.string.average), filmVoteAverage));

        TextView _filmReleaseDate = findViewById(R.id.detailFilmReleaseDate);
        _filmReleaseDate.setText(String.format("%s %s", getString(R.string.releaseDate), filmReleaseDate));

        ImageView _filmPoster = findViewById(R.id.detailFilmImage);
        Picasso.with(this).load(filmPosterURL).into(_filmPoster);
    }
}
