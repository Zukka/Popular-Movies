package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.utils.FilmJSonConstants;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.TheMovieDbJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    Film film;
    private TextView noTrailerFound;
    private RecyclerView trailerRecyclerView;
    private TrailerRecycleViewAdapter trailerRecyclerViewAdapter;
    private LinearLayoutManager trailerLinearLayoutManager;
    private ProgressBar trailerProgressBar;
    private AppDatabase mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState == null) {
            Intent detailsIntent = getIntent();
            film = detailsIntent.getParcelableExtra("film");
        } else {
            film = savedInstanceState.getParcelable(FilmJSonConstants.FILM);
        }

        TextView _filmTitle = findViewById(R.id.detailFilmTitle);
        _filmTitle.setText(film.getTitle());

        TextView _filmOverView = findViewById(R.id.detailFilmOverView);
        _filmOverView.setText(film.getOverView());

        TextView _filmVoteAverage = findViewById(R.id.detailFilmAverage);
        _filmVoteAverage.setText(String.format("%s %s", getString(R.string.average), film.getVoteAverage()));

        TextView _filmReleaseDate = findViewById(R.id.detailFilmReleaseDate);
        _filmReleaseDate.setText(String.format("%s %s", getString(R.string.releaseDate), film.getReleaseDate()));

        ImageView _filmPoster = findViewById(R.id.detailFilmImage);
        Picasso.with(this).load(film.getPosterURL()).into(_filmPoster);

        noTrailerFound = findViewById(R.id.no_trailer_textview);
        trailerProgressBar = findViewById(R.id.trailerProgressBar);
        trailerRecyclerView = findViewById(R.id.trailers_recycled_view);
        trailerRecyclerView.setHasFixedSize(true);
        trailerLinearLayoutManager = new LinearLayoutManager(this);
        trailerRecyclerView.setLayoutManager(trailerLinearLayoutManager);
        trailerRecyclerView.setItemAnimator(new DefaultItemAnimator());

        new RequestTrailersMovies().execute(film.getId());

        trailerRecyclerViewAdapter = new TrailerRecycleViewAdapter(this);
        trailerRecyclerView.setAdapter(trailerRecyclerViewAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(FilmJSonConstants.FILM, film);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_review) {
            Intent intent = new Intent(this, ReviewsActivity.class);
            intent.putExtra("film", film);
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void isProgressBarVisible(boolean isVisible) {
        if (isVisible) {
            trailerProgressBar.setVisibility(View.VISIBLE);
        } else {
            trailerProgressBar.setVisibility((View.GONE));
        }
    }

    public class RequestTrailersMovies extends AsyncTask<Integer, Void, List<Trailers>> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            isProgressBarVisible(true);
        }

        @Override
        protected List<Trailers> doInBackground(Integer... params) {

            if (params.length == 0) {
                return null;
            }


            int filmID = params[0];
            URL trailerRequestUrl = NetworkUtils.buildRequestTrailerUrl(filmID);


            try {
                String jsonTrailerResponse = NetworkUtils
                        .getResponseFromHttpUrl(trailerRequestUrl);

                List<Trailers> simpleJsonFilsData = TheMovieDbJsonUtils
                        .getSimpleTrailerStringsFromJson(jsonTrailerResponse);

                return simpleJsonFilsData;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Trailers> trailerData) {
            isProgressBarVisible(false);
            if (trailerData != null) {
                if (trailerData.size() == 0)
                    noTrailerFound.setVisibility(View.VISIBLE);
                else {
                    trailerRecyclerView.setVisibility(View.VISIBLE);
                    trailerRecyclerViewAdapter.setFilmData(trailerData);
                    trailerRecyclerView.setAdapter(trailerRecyclerViewAdapter);
                }
            } else {
                Toast.makeText(DetailsActivity.this, getString(R.string.loadTrailerFailed), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class AddFavorite extends AsyncTask<Integer, Void, Boolean> {
        private Favorite favorite;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             favorite = new Favorite(
                    film.getId(),
                    film.getTitle(),
                    film.getPosterURL(),
                    film.getOverView(),
                    film.getVoteAverage(),
                    film.getReleaseDate());
        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            if (params.length == 0) {
                return null;
            }
            int filmID = params[0];
            Favorite result = mDb.favoriteDao().findByFilmId(filmID);
            if (result != null)
                return false;
            else
                mDb.favoriteDao().insertFilm(favorite);
                return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean == null) {
                Toast.makeText(DetailsActivity.this, getString(R.string.no_favoritefilms), Toast.LENGTH_LONG).show();
            } else if (aBoolean)
                    Toast.makeText(DetailsActivity.this, getString(R.string.add_favorite_success), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(DetailsActivity.this, getString(R.string.add_favotite_fail), Toast.LENGTH_LONG).show();
        }
    }
}
