package com.pi.appfilme.network;

import com.pi.appfilme.model.filme.BuscaEBreve.Busca;
import com.pi.appfilme.model.filme.BuscaEBreve.Movie;
import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.pi.appfilme.model.filme.creditos.Creditos;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.model.pessoa.Filmografia;
import com.pi.appfilme.model.pessoa.FotosPessoa;
import com.pi.appfilme.model.pessoa.PessoaDetalhe;
import com.pi.appfilme.model.pessoa.pessoa.Pessoas;
import com.pi.appfilme.model.series.ResultSeriesDetalhe;
import com.pi.appfilme.model.series.Season;
import com.pi.appfilme.model.series.SeasonDetalhes.SeasonDetalhes;
import com.pi.appfilme.model.series.SeriesPopular;
import com.pi.appfilme.model.series.SeriesTop;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("search/movie")
    Observable<Busca> buscaFilmes(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int pagina,
            @Query("region") String region);


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

    @GET("movie/{movie_id}/credits")
    Observable<Creditos> getCreditos(
    @Path("movie_id") long id,
    @Query("api_key") String apiKey);

    @GET("person/{person_id}")
    Single<PessoaDetalhe> getPessoaDetalhe(
            @Path("person_id") long id,
            @Query("api_key") String api_key,
            @Query("language") String language);

    @GET("person/{person_id}/movie_credits")
    Observable<Filmografia> getFilmografia(
            @Path("person_id") long id,
            @Query("api_key") String api_key,
            @Query("language") String language);

    @GET("person/{person_id}/images")
    Observable<FotosPessoa> getFotos(
            @Path("person_id") long id,
            @Query("api_key") String api_key);


    @GET("tv/{tv_id}")
    Single<ResultSeriesDetalhe> getSerieDetalhe(
            @Path("tv_id") long id,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("tv/top_rated")
    Observable<SeriesTop> getSeriesTop(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page);

    @GET("tv/popular")
    Observable<SeriesPopular> getSeriePopular(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page);

    @GET("tv/{tv_id}/season/{season_number}")
    Single<SeasonDetalhes> getSeasonDetalhes(
            @Path("tv_id") long id,
            @Path("season_number") long season_number,
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("person/popular")
    Observable<Pessoas> getPessoasPopular(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pagina);
}
