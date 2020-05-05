package com.pi.efilm.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pi.efilm.model.filme.BuscaEBreve.Movie;
import com.pi.efilm.model.filme.detalhes.Detalhes;
import com.pi.efilm.model.filme.detalhes.Genre;
import com.pi.efilm.model.filme.detalhes.ProductionCompany;
import com.pi.efilm.model.filme.detalhes.ProductionCountry;
import com.pi.efilm.model.filme.detalhes.SpokenLanguage;

import java.lang.reflect.Type;
import java.util.Date;
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
    public List<Detalhes> fromListDetalhe(String value) {
        Type listType = (Type) new TypeToken<List<Detalhes>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromListDetalheObject(List<Detalhes> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public Date toDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return new Date(timestamp);
        }
    }

    @TypeConverter
    public Long toTimestamp(Date date) {
        return date.getTime();
    }


    @TypeConverter
    public Movie fromFilme(String value) {
        Type listType = (Type) new TypeToken<Movie>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromFilme(Movie movie) {
        Gson gson = new Gson();
        return gson.toJson(movie);
    }


    @TypeConverter
    public Genre fromGenre(String value) {
        Type listType = (Type) new TypeToken<Genre>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public String fromGenre(Genre list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public String fromGenre(List list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public List<Genre> fromListGenre(String value) {
        Type listType = (Type) new TypeToken<List<Genre>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromListGenre(List<Genre> listGenre) {
        Gson gson = new Gson();
        return gson.toJson(listGenre);
    }

    @TypeConverter
    public List<ProductionCompany> fromListProductionCompany(String valueProdCompany) {
        Type listType = (Type) new TypeToken<List<ProductionCompany>>() {
        }.getType();
        return new Gson().fromJson(valueProdCompany, listType);
    }

    @TypeConverter
    public String fromListProductionCompany(List<ProductionCompany> listProductionCompany) {
        Gson gson = new Gson();
        return gson.toJson(listProductionCompany);
    }

    @TypeConverter
    public List<ProductionCountry> fromListProductionCountry(String valueProdCountry) {
        Type listType = (Type) new TypeToken<List<ProductionCountry>>() {
        }.getType();
        return new Gson().fromJson(valueProdCountry, listType);
    }

    @TypeConverter
    public String fromListProductionCountry(List<ProductionCountry> listProductionCountry) {
        Gson gson = new Gson();
        return gson.toJson(listProductionCountry);
    }

    @TypeConverter
    public  List<SpokenLanguage> fromListSpokenLanguage(String valueSpokenLanguage){
        Type listType = (Type) new TypeToken<List<SpokenLanguage>>(){
        }.getType();
        return new Gson().fromJson(valueSpokenLanguage, listType);
    }

    @TypeConverter
    public String fromListSpokenLanguage(List<SpokenLanguage> listSpokenLanguage){
        Gson gson = new Gson();
        return gson.toJson(listSpokenLanguage);
    }


}
