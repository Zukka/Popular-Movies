package com.example.android.popularmovies.utils;

import android.content.Context;

import com.example.android.popularmovies.Film;

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

    public static List<Film> getSimpleFilmsStringsFromJson(Context mainActivity, String jsonMoviesResponse) throws JSONException {

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

            String FilmId = filmObject.get(FilmJSonConstants.ID).toString();
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
}
