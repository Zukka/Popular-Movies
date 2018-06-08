package com.example.android.popularmovies.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by zukka on 19/03/18.
 */

public class TheMovieDbJsonUtils {

    public static List<Film> getSimpleFilmsStringsFromJson(Context mainActivity, String jsonMoviesResponse) throws JSONException {

        final String OWM_MESSAGE_CODE = "cod";
        final String FILM_LIST = "results";

        JSONObject filmJson = new JSONObject(jsonMoviesResponse);
        List<Film> parsedFilmData = new List<Film>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Film> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(Film film) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Film> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends Film> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Film get(int i) {
                return null;
            }

            @Override
            public Film set(int i, Film film) {
                return null;
            }

            @Override
            public void add(int i, Film film) {

            }

            @Override
            public Film remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Film> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Film> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<Film> subList(int i, int i1) {
                return null;
            }
        };

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

        for (int i = 0; i < filmArray.length(); i++) {
            JSONObject filmObject = filmArray.getJSONObject(i);

           String FilmTitle = filmObject.getJSONObject(FilmJSonConstants.Title).toString();
           String FilmPoster = filmObject.getJSONObject(FilmJSonConstants.Poster).toString();
           String FilmOverView = filmObject.getJSONObject(FilmJSonConstants.OverView).toString();
           String FilmVoteAverage = filmObject.getJSONObject(FilmJSonConstants.VoteAverage).toString();
           String FilmReleaseDate = filmObject.getJSONObject(FilmJSonConstants.ReleaseDate).toString();
           Film film = new Film(FilmTitle, FilmPoster, FilmOverView, FilmVoteAverage, FilmReleaseDate);
           parsedFilmData.add(film);
        }
        return parsedFilmData;
    }
}
