package com.example.android.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by zukka on 18/03/18.
 */

public class NetworkUtils {

    public static URL buildRequestMoviesUrl(String endpointURL) {

        String baseURL = PopularMoviesConstants.THE_MOVIE_DB_BASE_URL + endpointURL;
        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter(PopularMoviesConstants.THE_MOVIE_DB_API_KEY_PARAMETER, PopularMoviesConstants.THE_MOVIE_DB_API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildRequestReviewsUrl(int movieID) {

        String baseURL =
                        PopularMoviesConstants.THE_MOVIE_DB_BASE_URL +
                        PopularMoviesConstants.THE_MOVIE_DB_BASE_FETCH_REQUEST +
                        movieID +
                        PopularMoviesConstants.THE_MOVIE_DB_REVIEWS_FETCH_REQUEST;


        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter(PopularMoviesConstants.THE_MOVIE_DB_API_KEY_PARAMETER, PopularMoviesConstants.THE_MOVIE_DB_API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
