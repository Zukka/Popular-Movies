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

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    Film film;
    private RecyclerView trailerRecyclerView;
  //  private TrailerRecycleViewAdapter trailerRecyclerViewAdapter;
    private LinearLayoutManager trailerLinearLayoutManager;
    private ProgressBar trailerProgressBar;
    String filmTitle, filmPosterURL, filmOverView, filmVoteAverage, filmReleaseDate;
    int filmId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent detailsIntent = getIntent();
        film = detailsIntent.getParcelableExtra("film");
        filmId = film.getId();
        filmTitle = film.getTitle();
        filmPosterURL = film.getPosterURL();
        filmOverView = film.getOverView();
        filmVoteAverage = film.getVoteAverage();
        filmReleaseDate = film.getReleaseDate();

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

        trailerProgressBar = findViewById(R.id.trailerProgressBar);
        trailerRecyclerView = findViewById(R.id.trailers_recycled_view);
        trailerRecyclerView.setHasFixedSize(true);
        trailerLinearLayoutManager = new LinearLayoutManager(this);
        trailerRecyclerView.setLayoutManager(trailerLinearLayoutManager);
        trailerRecyclerView.setItemAnimator(new DefaultItemAnimator());

        new RequestTrailersMovies().execute();
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

    public class RequestTrailersMovies extends AsyncTask<String, Void, List<Trailers>> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            isProgressBarVisible(true);
        }

        @Override
        protected List<Trailers> doInBackground(String... strings) {
            return null;
        }
    }
}
