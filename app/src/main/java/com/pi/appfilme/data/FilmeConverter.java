package com.pi.appfilme.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pi.appfilme.model.filme.detalhes.Detalhes;

import java.lang.reflect.Type;
import java.util.List;

public class FilmeConverter {

    @TypeConverter
    public Object fromObject(String value) {
        Type listType = (Type) new TypeToken<Object>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromJsonObject(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @TypeConverter
    public List<Detalhes> fromListAlbum(String value) {
        Type listType = (Type) new TypeToken<List<Detalhes>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromListAlbumObject(List<Detalhes> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}
