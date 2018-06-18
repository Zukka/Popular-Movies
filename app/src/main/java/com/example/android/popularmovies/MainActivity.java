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
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.PopularMoviesConstants;
import com.example.android.popularmovies.utils.TheMovieDbJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView filmRecyclerView;
    private FilmRecycleViewAdapter filmRecycleViewAdapter;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar loadingDataProgressBar;
    private AppDatabase mDb;
    private String currentEndPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());
        loadingDataProgressBar = findViewById(R.id.progressBar);
        filmRecyclerView = findViewById(R.id.films_recycled_view);
        filmRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4);
        filmRecyclerView.setLayoutManager(gridLayoutManager);
        filmRecyclerView.setItemAnimator(new DefaultItemAnimator());
        loadMoviesData();

        filmRecycleViewAdapter = new FilmRecycleViewAdapter(this);
        filmRecyclerView.setAdapter(filmRecycleViewAdapter);
    }

    private void isProgressBarVisible(boolean isVisible) {
        if (isVisible) {
            loadingDataProgressBar.setVisibility(View.VISIBLE);
        } else {
            loadingDataProgressBar.setVisibility((View.GONE));
        }
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
        currentEndPoint = popularMoviesPrefs.getString("ENDPOINT", PopularMoviesConstants.THE_MOVIE_DB_POPULAR_ENDPOINT);
        new RequestMovies().execute(currentEndPoint);
    }

    public class RequestMovies extends AsyncTask<String, Void, List<Film>> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            isProgressBarVisible(true);
        }

        @Override
        protected List<Film> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            String endPointURL = params[0];
            if (endPointURL != PopularMoviesConstants.THE_MOVIE_DB_LOCAL_DATABASE_ENDPOINT) {
                URL movieRequestUrl = NetworkUtils.buildRequestMoviesUrl(endPointURL);

                try {
                    String jsonMoviesResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieRequestUrl);

                    List<Film> simpleJsonFilsData = TheMovieDbJsonUtils
                            .getSimpleFilmsStringsFromJson(jsonMoviesResponse);

                    return simpleJsonFilsData;

                } catch (Exception e) {
                    return null;
                }
            } else {
                List<Favorite> favoriteData = mDb.favoriteDao().getAllFavorites();
                if (favoriteData != null) {
                    List<Film> filmData = new ArrayList<>();;
                    for (Favorite favoriteFilm : favoriteData) {
                        Film film = new Film(
                                favoriteFilm.getFilmId(),
                                favoriteFilm.getFilmTitle(),
                                favoriteFilm.getFilmPosterUrl(),
                                favoriteFilm.getFilmOverView(),
                                favoriteFilm.getFilmVoteAverage(),
                                favoriteFilm.getFilmReleaseDate()
                        );
                        filmData.add(film);
                    }
                    return filmData;
                } else {
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(List<Film> movieData) {
            isProgressBarVisible(false);
            if (movieData != null) {
                filmRecyclerView.setVisibility(View.VISIBLE);
                filmRecycleViewAdapter.setFilmData(movieData);
                filmRecyclerView.setAdapter(filmRecycleViewAdapter);
            } else {
                if (currentEndPoint.equals(PopularMoviesConstants.THE_MOVIE_DB_LOCAL_DATABASE_ENDPOINT))
                    Toast.makeText(MainActivity.this, getString(R.string.no_favoritefilms), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, getString(R.string.loadFilmsFailed), Toast.LENGTH_LONG).show();
            }
        }
    }
}
