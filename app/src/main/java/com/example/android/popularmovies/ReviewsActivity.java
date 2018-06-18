package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.TheMovieDbJsonUtils;

import java.net.URL;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private ReviewRecycleViewAdapter reviewRecycleViewAdapter;
    private LinearLayoutManager layoutManager;
    private ProgressBar loadingReviewsProgressBar;
    private RecyclerView reviewRecycledView;
    private TextView noReviewFound;
    private Film film;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Intent detailsIntent = getIntent();
        film = detailsIntent.getParcelableExtra("film");

        loadingReviewsProgressBar = findViewById(R.id.review_progressbar);
        noReviewFound = findViewById(R.id.no_review_textview);
        reviewRecycledView = findViewById(R.id.reviews_recycled_view);
        reviewRecycledView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        reviewRecycledView.setLayoutManager(layoutManager);
        reviewRecycledView.setItemAnimator(new DefaultItemAnimator());

        new RequestMoviesReviews().execute(film.getId());

        reviewRecycleViewAdapter = new ReviewRecycleViewAdapter(this);
        reviewRecycledView.setAdapter(reviewRecycleViewAdapter);
    }

    private void isProgressBarVisible(boolean isVisible) {
        if (isVisible) {
            loadingReviewsProgressBar.setVisibility(View.VISIBLE);
        } else {
            loadingReviewsProgressBar.setVisibility((View.GONE));
        }
    }

    public class RequestMoviesReviews extends AsyncTask<Integer, Void, List<Review>> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            isProgressBarVisible(true);
        }

        @Override
        protected List<Review> doInBackground(Integer... params) {

            if (params.length == 0) {
                return null;
            }

            int filmID = params[0];
            URL reviewRequestUrl = NetworkUtils.buildRequestReviewsUrl(filmID);

            try {
              String jsonReviewResponse = NetworkUtils
                        .getResponseFromHttpUrl(reviewRequestUrl);

                List<Review> simpleJsonFilsData = TheMovieDbJsonUtils
                        .getSimpleReviewStringsFromJson(jsonReviewResponse);

                return simpleJsonFilsData;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Review> reviewData) {
            isProgressBarVisible(false);
            if (reviewData != null) {
                if (reviewData.size() == 0)
                    noReviewFound.setVisibility(View.VISIBLE);
                else {
                    reviewRecycledView.setVisibility(View.VISIBLE);
                    reviewRecycleViewAdapter.setFilmData(reviewData);
                    reviewRecycledView.setAdapter(reviewRecycleViewAdapter);
                }
            } else {
                Toast.makeText(ReviewsActivity.this, getString(R.string.loadReviewFailed), Toast.LENGTH_LONG).show();
            }
        }
    }
}
