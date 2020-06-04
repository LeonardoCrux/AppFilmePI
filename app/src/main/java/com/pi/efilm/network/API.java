package com.pi.efilm.network;

import com.pi.efilm.model.filme.BuscaEBreve.Movie;
import com.pi.efilm.model.filme.creditos.Creditos;
import com.pi.efilm.model.filme.detalhes.Detalhes;
import com.pi.efilm.model.pessoa.Filmografia;
import com.pi.efilm.model.pessoa.FotosPessoa;
import com.pi.efilm.model.pessoa.PessoaDetalhe;
import com.pi.efilm.model.pessoa.pessoa.Pessoas;
import com.pi.efilm.model.series.ResultSeriesDetalhe;
import com.pi.efilm.model.series.SeasonDetalhes.SeasonDetalhes;
import com.pi.efilm.model.series.SeriesTop;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("search/movie")
    Observable<Movie> buscaFilmes(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int pagina,
            @Query("region") String region);

    @GET("search/tv")
    Observable<SeriesTop> buscaSeries(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int pagina,
            @Query("region") String region);

    @GET("search/person")
    Observable<Pessoas> buscaPessoas(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int pagina,
            @Query("region") String region);

    @GET("movie/popular")
    Observable<Movie> getFilmePopular(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("region") String region,
            @Query("page") int pagina);

    @GET("movie/{movie_id}/similar")
    Observable<Movie> getFilmeSimilar(
            @Path("movie_id") long id,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int pagina);

    @GET("tv/{tv_id}/similar")
    Observable<SeriesTop> getSerieSimilar(
            @Path("tv_id") long id,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int pagina);

    @GET("movie/{movie_id}/recommendations")
    Observable<Movie> getFilmeRecomendado(
            @Path("movie_id") long id,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int pagina);

    @GET("discover/movie")
    Observable<Movie> buscaBilheteria(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("sort_by") String sort,
            @Query("vote_count.gte") int vote,
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

    @GET("tv/{tv_id}/credits")
    Observable<Creditos> getCreditosSerie(
            @Path("tv_id") long id,
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

    @GET("person/{person_id}/tv_credits")
    Observable<Filmografia> getSeriesTV(
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
    Observable<SeriesTop> getSeriePopular(
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
