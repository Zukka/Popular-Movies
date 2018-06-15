package com.example.android.popularmovies.utils;

import android.content.Context;

import com.example.android.popularmovies.Film;
import com.example.android.popularmovies.Review;
import com.example.android.popularmovies.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zukka on 19/03/18.
 */

public class TheMovieDbJsonUtils {

    public static List<Film> getSimpleFilmsStringsFromJson(String jsonMoviesResponse) throws JSONException {

        final String OWM_MESSAGE_CODE = "cod";
        final String FILM_LIST = "results";

        JSONObject filmJson = new JSONObject(jsonMoviesResponse);
        List<Film> parsedFilmData;
        if (filmJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = filmJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray filmArray = filmJson.getJSONArray(FILM_LIST);
        parsedFilmData = new ArrayList<>();
        for (int i = 0; i < filmArray.length(); i++) {
            JSONObject filmObject = filmArray.getJSONObject(i);

            int FilmId = (int)filmObject.get(FilmJSonConstants.ID);
            String FilmTitle = filmObject.get(FilmJSonConstants.TITLE).toString();
            String FilmPoster = PopularMoviesConstants.IMAGE_BASE_URL + PopularMoviesConstants.IMAGE_SIZE_URL + filmObject.get(FilmJSonConstants.POSTER).toString();
            String FilmOverView = filmObject.get(FilmJSonConstants.OVERVIEW).toString();
            String FilmVoteAverage = filmObject.get(FilmJSonConstants.VOTE_AVERAGE).toString();
            String FilmReleaseDate = filmObject.get(FilmJSonConstants.RELEASE_DATE).toString();
            Film film = new Film(FilmId, FilmTitle, FilmPoster, FilmOverView, FilmVoteAverage, FilmReleaseDate);
            parsedFilmData.add(film);
        }
        return parsedFilmData;
    }

    public static List<Review> getSimpleReviewStringsFromJson(String jsonReviewsResponse) throws JSONException  {

        final String OWM_MESSAGE_CODE = "cod";
        final String REVIEW_LIST = "results";

        JSONObject reviewJson = new JSONObject(jsonReviewsResponse);
        List<Review> parsedReviewData;
        if (reviewJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = reviewJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray reviewArray = reviewJson.getJSONArray(REVIEW_LIST);
        parsedReviewData = new ArrayList<>();
        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject filmObject = reviewArray.getJSONObject(i);
            String Author = filmObject.get(FilmJSonConstants.AUTHOR).toString();
            String Content = filmObject.get(FilmJSonConstants.CONTENT).toString();
            Review review = new Review(Author,Content);
            parsedReviewData.add(review);
        }
        return  parsedReviewData;
    }

    public static List<Trailers> getSimpleTrailerStringsFromJson(String jsonTrailerResponse) throws  JSONException {

        final String OWM_MESSAGE_CODE = "cod";
        final String TRAILER_LIST = "results";

        JSONObject trialerJson = new JSONObject(jsonTrailerResponse);
        List<Trailers> parsedTrailerData;
        if (trialerJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = trialerJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray trailerArray = trialerJson.getJSONArray(TRAILER_LIST);
        parsedTrailerData = new ArrayList<>();
        for (int i = 0; i < trailerArray.length(); i++) {
            JSONObject filmObject = trailerArray.getJSONObject(i);
            String key = filmObject.get(FilmJSonConstants.KEY).toString();
            Trailers trailer = new Trailers(key);
            parsedTrailerData.add(trailer);
        }
        return  parsedTrailerData;
    }
}
