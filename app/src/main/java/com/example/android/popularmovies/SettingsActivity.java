package com.example.android.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.android.popularmovies.utils.PopularMoviesConstants;

/**
 * Created by zukka on 17/03/18.
 */

public class SettingsActivity extends AppCompatActivity {

    RadioButton popular, rating;
    SharedPreferences popularMoviesPrefs;
    SharedPreferences.Editor prefEditor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        popular = findViewById(R.id.radio_popular);
        rating = findViewById(R.id.radio_rating);

        popularMoviesPrefs = getSharedPreferences("PopularMovies_Prefs", Context.MODE_PRIVATE);
        String selectedEndPoint = popularMoviesPrefs.getString("ENDPOINT", PopularMoviesConstants.theMovieDbPopularEndPoint);
        SelectedRadioButton(selectedEndPoint);
    }

    private void SelectedRadioButton(String selectedEndPoint) {
        if (selectedEndPoint.equals(PopularMoviesConstants.theMovieDbPopularEndPoint)) {

            popular.setChecked(true);
        } else {
            rating.setChecked(true);
        }

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        prefEditor = popularMoviesPrefs.edit();

        switch(view.getId()) {
            case R.id.radio_popular:
                if (checked) {
                    prefEditor.putString("ENDPOINT", PopularMoviesConstants.theMovieDbPopularEndPoint);
                    prefEditor.commit();
                }
                break;

            case R.id.radio_rating:
                if (checked) {
                    prefEditor.putString("ENDPOINT", PopularMoviesConstants.theMovieDbTopRateEndPoint);
                    prefEditor.commit();
                }
                break;
        }
    }
}
