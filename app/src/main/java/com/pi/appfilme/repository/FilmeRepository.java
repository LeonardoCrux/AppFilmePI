package com.pi.appfilme.repository;

import com.pi.appfilme.model.filme.BuscaEBreve.Movie;
import com.pi.appfilme.model.series.SeriesPopulares;
import com.pi.appfilme.network.FilmeService;

import io.reactivex.Observable;

public class FilmeRepository {

    public Observable<Movie> getPlaying(String apiKey, String language, String region, int pagina){
        return FilmeService.getApiService().getPlaying(apiKey, language,region, pagina);
    }

    public Observable<SeriesPopulares> getSeriesPopulares(String apiKey, String language, int pagina){
        return FilmeService.getApiService().getSeriesPopulares(apiKey, language, pagina);
    }

    public Observable<Movie> getTop(String apiKey, String language, String region, int pagina){
        return FilmeService.getApiService().getTop(apiKey, language,region, pagina);
    }
}
