package com.pi.appfilme.network;

import com.pi.appfilme.model.filme.BuscaEBreve.Movie;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.model.series.SeriesPopulares;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("movie/now_playing")
    Observable<Movie> getPlaying(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("region") String region,
            @Query("page") int pagina);

    @GET("movie/top_rated")
    Observable<Movie> getTop(
    @Query("api_key") String api_key,
    @Query("language") String language,
    @Query("region") String region,
    @Query("page") int pagina);

    @GET("movie/{movie_id}")
    Single<Detalhes> getFilmeDetalhe(
            @Path("movie_id") long id,
            @Query("api_key") String apiKey,
            @Query("language") String language);
    @GET("tv/popular")
    Observable<SeriesPopulares> getSeriesPopulares(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pagina);
}
