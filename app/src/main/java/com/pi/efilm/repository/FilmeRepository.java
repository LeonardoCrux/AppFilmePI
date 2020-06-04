package com.pi.efilm.repository;

import android.content.Context;

import com.pi.efilm.data.FilmeDataBase;
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
import com.pi.efilm.network.FilmeService;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class FilmeRepository {

    public Observable<Movie> buscaFilmes(String apiKey, String language, String query, int pagina, String region) {
        return FilmeService.getApiService().buscaFilmes(apiKey, language, query, pagina, region);
    }

    public Observable<SeriesTop> buscaSeries(String apiKey, String language, String query, int pagina, String region) {
        return FilmeService.getApiService().buscaSeries(apiKey, language, query, pagina, region);
    }

    public Observable<Pessoas> buscaPessoas(String apiKey, String language, String query, int pagina, String region) {
        return FilmeService.getApiService().buscaPessoas(apiKey, language, query, pagina, region);
    }

    public Observable<Movie> buscaBilheteria(String apikey, String language, String sort, int vote, int pagina){
        return FilmeService.getApiService().buscaBilheteria(apikey, language, sort,vote,  pagina);
    }

    public Observable<Movie> getFilmePopular(String apiKey, String language, String region, int pagina) {
        return FilmeService.getApiService().getFilmePopular(apiKey, language, region, pagina);
    }
    public Observable<Movie> getFilmeSimilar(long id, String apiKey, String language, int pagina) {
        return FilmeService.getApiService().getFilmeSimilar(id, apiKey, language, pagina);
    }

    public Observable<SeriesTop> getSerieSimilar(long id, String apiKey, String language, int pagina) {
        return FilmeService.getApiService().getSerieSimilar(id, apiKey, language, pagina);
    }

    public Observable<Movie> getFilmeRecomendado(long id, String apiKey, String language, int pagina) {
        return FilmeService.getApiService().getFilmeRecomendado(id, apiKey, language, pagina);
    }

    public Observable<Movie> getTop(String apiKey, String language, String region, int pagina) {
        return FilmeService.getApiService().getTop(apiKey, language, region, pagina);
    }

    public Observable<Creditos> getCreditos(long id, String apiKey) {
        return FilmeService.getApiService().getCreditos(id, apiKey);
    }

    public Observable<Creditos> getCreditosSerie(long id, String apiKey) {
        return FilmeService.getApiService().getCreditosSerie(id, apiKey);
    }

    public Single<Detalhes> getFilmeDetalhes(long id, String apiKey, String language) {
        return FilmeService.getApiService().getFilmeDetalhe(id, apiKey, language);
    }

    public Single<PessoaDetalhe> getPessoaDetalhe(long id, String apiKey, String language) {
        return FilmeService.getApiService().getPessoaDetalhe(id, apiKey, language);
    }

    public Observable<Filmografia> getFilmografia(long id, String apiKey, String language) {
        return FilmeService.getApiService().getFilmografia(id, apiKey, language);
    }

    public Observable<Filmografia> getSeriesTV(long id, String apiKey, String language) {
        return FilmeService.getApiService().getSeriesTV(id, apiKey, language);
    }

    public Observable<FotosPessoa> getFotos(long id, String apiKey) {
        return FilmeService.getApiService().getFotos(id, apiKey);
    }

    public Single<ResultSeriesDetalhe> getSerieDetalhe(long id, String apiKey, String language) {
        return FilmeService.getApiService().getSerieDetalhe(id, apiKey, language);
    }

    public Single<SeasonDetalhes> getSeason(long id, long number, String apiKey, String language) {
        return FilmeService.getApiService().getSeasonDetalhes(id, number, apiKey, language);
    }

    public Observable<SeriesTop> getSeriesTop(String apiKey, String language, int pagina) {
        return FilmeService.getApiService().getSeriesTop(apiKey, language, pagina);
    }

    public Observable<SeriesTop> getSeriesPopular(String apiKey, String language, int page) {
        return FilmeService.getApiService().getSeriePopular(apiKey, language, page);
    }

    public Observable<Pessoas> getPessoasPopular(String apiKey, String language, int pagina) {
        return FilmeService.getApiService().getPessoasPopular(apiKey, language, pagina);
    }

    public void insereDadosDB(Detalhes filmeDetalhes, Context context) {
        FilmeDataBase.getDatabase(context).filmeDAO().insereFavoritoDB(filmeDetalhes);
    }

    public void removeFavorito(Detalhes detalhes, Context context) {
        FilmeDataBase.getDatabase(context).filmeDAO().removeFavorito(detalhes);
    }

    public Flowable<List<Detalhes>> getFavoritosDB(Context context) {
        return FilmeDataBase.getDatabase(context).filmeDAO().recuperaFavoritosDB();
    }
    public Single<Detalhes> getDetalheDB(Context context, long id){
        return FilmeDataBase.getDatabase(context).filmeDAO().recuperaFilmeDetalhe(id);
    }


}
