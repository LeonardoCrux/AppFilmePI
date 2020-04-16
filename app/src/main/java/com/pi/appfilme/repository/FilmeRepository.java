package com.pi.appfilme.repository;

import com.pi.appfilme.model.filme.BuscaEBreve.Movie;
import com.pi.appfilme.model.filme.creditos.Creditos;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.model.pessoa.FilmesPessoa;
import com.pi.appfilme.model.pessoa.Filmografia;
import com.pi.appfilme.model.pessoa.FotosPessoa;
import com.pi.appfilme.model.pessoa.PessoaDetalhe;
import com.pi.appfilme.model.series.SeriesPopulares;
import com.pi.appfilme.network.FilmeService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class FilmeRepository {

    public Observable<Movie> getPlaying(String apiKey, String language, String region, int pagina) {
        return FilmeService.getApiService().getPlaying(apiKey, language, region, pagina);
    }

    public Observable<SeriesPopulares> getSeriesPopulares(String apiKey, String language, int pagina) {
        return FilmeService.getApiService().getSeriesPopulares(apiKey, language, pagina);
    }

    public Observable<Movie> getTop(String apiKey, String language, String region, int pagina) {
        return FilmeService.getApiService().getTop(apiKey, language, region, pagina);
    }

    public Observable<Creditos> getCreditos(long id, String apiKey) {
        return FilmeService.getApiService().getCreditos(id, apiKey);
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

    public Observable<FotosPessoa> getFotos(long id, String apiKey) {
        return FilmeService.getApiService().getFotos(id, apiKey);
    }
}
