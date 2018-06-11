package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.PopularMoviesConstants;
import com.example.android.popularmovies.utils.TheMovieDbJsonUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView filmRecyclerView;
    private FilmRecycleViewAdapter filmRecycleViewAdapter;
    private static RecyclerView.Adapter adapter;
    private GridLayoutManager gridLayoutManager;

    private List<Film> filmsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filmRecyclerView = findViewById(R.id.films_recycled_view);
        filmRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4);
        filmRecyclerView.setLayoutManager(gridLayoutManager);
        filmRecyclerView.setItemAnimator(new DefaultItemAnimator());
        loadMoviesData();

        filmRecycleViewAdapter = new FilmRecycleViewAdapter();
        filmRecyclerView.setAdapter(filmRecycleViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMoviesData() {
        SharedPreferences popularMoviesPrefs = getSharedPreferences("PopularMovies_Prefs", Context.MODE_PRIVATE);
        String endPointOrder = popularMoviesPrefs.getString("ENDPOINT", PopularMoviesConstants.theMovieDbPopularEndPoint);
        new RequestFavoriteMovies().execute(endPointOrder);
    }

    public class RequestFavoriteMovies extends AsyncTask<String, Void, List<Film>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Film> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String endPointURL = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(endPointURL);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                List<Film> simpleJsonFilsData = TheMovieDbJsonUtils
                        .getSimpleFilmsStringsFromJson(MainActivity.this, jsonMoviesResponse);

                return simpleJsonFilsData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Film> movieData) {
            if (movieData != null) {
                filmRecycleViewAdapter.setFilmData(movieData);
                filmRecyclerView.setAdapter(filmRecycleViewAdapter);
            }
        }
    }
}
