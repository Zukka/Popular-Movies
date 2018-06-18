package com.example.android.popularmovies;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    List<Favorite> getAll();

    @Query("SELECT * FROM favorite WHERE id LIKE :first LIMIT 1")
    Favorite findByFilmId(String first);

    @Insert
    void insertFilm(Favorite... favorites);

    @Delete
    void deleteFilm(Favorite favorite);
}
