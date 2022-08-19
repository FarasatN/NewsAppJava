package com.farasatnovruzov.newsappjava.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.farasatnovruzov.newsappjava.model.Articles;

import java.util.List;

@Dao
public interface NewsDAO {

    @Query("SELECT * FROM news")
    List<Articles> getAll();

    @Delete
    void delete(Articles news);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(Articles... news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long upsert(Articles news);
}
