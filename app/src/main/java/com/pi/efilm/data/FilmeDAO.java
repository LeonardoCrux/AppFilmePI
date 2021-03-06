package com.pi.efilm.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pi.efilm.model.filme.detalhes.Detalhes;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface FilmeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insereFavoritoDB(Detalhes filme);

    @Query("SELECT * from filmes")
    Flowable<List<Detalhes>> recuperaFavoritosDB();

    @Query("SELECT * FROM filmes WHERE id=:id")
    Single<Detalhes> recuperaFilmeDetalhe(long id);

    @Delete
    void removeFavorito(Detalhes detalhes);
}
