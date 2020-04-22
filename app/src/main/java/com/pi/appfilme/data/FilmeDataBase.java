package com.pi.appfilme.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.pi.appfilme.model.filme.detalhes.Detalhes;

@Database(entities = {Detalhes.class}, version = 1, exportSchema = false)
@TypeConverters(FilmeConverter.class)
public abstract class FilmeDataBase extends RoomDatabase {

    private static volatile FilmeDataBase INSTANCE;

    public abstract FilmeDAO filmeDAO();

    public static FilmeDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (FilmeDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, FilmeDataBase.class, "filmes_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

