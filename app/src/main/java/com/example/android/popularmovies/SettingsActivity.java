package com.example.android.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.android.popularmovies.utils.PopularMoviesConstants;

/**
 * Created by zukka on 17/03/18.
 */

public class SettingsActivity extends AppCompatActivity {

    private String endpoint = "ENDPOINT";

    RadioButton popular, rating, favorite;
    SharedPreferences popularMoviesPrefs;
    SharedPreferences.Editor prefEditor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        popular = findViewById(R.id.radio_popular);
        rating = findViewById(R.id.radio_rating);
        favorite = findViewById(R.id.radio_favorites);

        popularMoviesPrefs = getSharedPreferences("PopularMovies_Prefs", Context.MODE_PRIVATE);
        String selectedEndPoint = popularMoviesPrefs.getString("ENDPOINT", PopularMoviesConstants.THE_MOVIE_DB_POPULAR_ENDPOINT);
        SelectedRadioButton(selectedEndPoint);
    }

    private void SelectedRadioButton(String selectedEndPoint) {
        if (selectedEndPoint.equals(PopularMoviesConstants.THE_MOVIE_DB_POPULAR_ENDPOINT)) {
            popular.setChecked(true);
        } else if (selectedEndPoint.equals(PopularMoviesConstants.THE_MOVIE_DB_TOP_RATE_ENDPOINT)){
            rating.setChecked(true);
        } else {
            favorite.setChecked(true);
        }

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        prefEditor = popularMoviesPrefs.edit();

        switch(view.getId()) {
            case R.id.radio_popular:
                if (checked) {
                    prefEditor.putString(endpoint, PopularMoviesConstants.THE_MOVIE_DB_POPULAR_ENDPOINT);
                    prefEditor.commit();
                }
                break;

            case R.id.radio_rating:
                if (checked) {
                    prefEditor.putString(endpoint, PopularMoviesConstants.THE_MOVIE_DB_TOP_RATE_ENDPOINT);
                    prefEditor.commit();
                }
                break;

            case R.id.radio_favorites:
                if (checked) {
                    prefEditor.putString(endpoint, PopularMoviesConstants.THE_MOVIE_DB_LOCAL_DATABASE_ENDPOINT);
                    prefEditor.commit();
                }
        }
    }
}
