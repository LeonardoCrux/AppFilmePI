package com.pi.appfilme.data;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pi.appfilme.model.filme.detalhes.Detalhes;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FilmeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insereFavoritoDB(Detalhes filme);

    @Query("SELECT * from filmes")
    Flowable<List<Detalhes>> recuperaFavoritosDB();

    @Delete
    void removeFavorito(Detalhes detalhes);
}
