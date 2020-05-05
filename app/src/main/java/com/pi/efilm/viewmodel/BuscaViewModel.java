package com.pi.efilm.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pi.efilm.model.filme.BuscaEBreve.ResultFilme;
import com.pi.efilm.model.pessoa.pessoa.ResultPessoaPop;
import com.pi.efilm.model.series.ResultSeriesTop;
import com.pi.efilm.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BuscaViewModel extends AndroidViewModel {
    private CompositeDisposable disposable =  new CompositeDisposable();
    private FilmeRepository repository =  new FilmeRepository();
    public MutableLiveData<List<ResultFilme>> mutableBuscaFilme = new MutableLiveData<>();
    public LiveData<List<ResultFilme>> liveDataFilme = mutableBuscaFilme;
    public MutableLiveData<String> mutableErro =  new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableErro;

    public MutableLiveData<List<ResultSeriesTop>> mutableBuscaSerie = new MutableLiveData<>();
    public LiveData<List<ResultSeriesTop>> liveDataSerie = mutableBuscaSerie;

    public MutableLiveData<List<ResultPessoaPop>> mutablePessoaBusca = new MutableLiveData<>();
    public LiveData<List<ResultPessoaPop>> liveDataPessoa = mutablePessoaBusca;

    public BuscaViewModel(@NonNull Application application) {
        super(application);
    }

    public void getResultFilme(String api, String language, String query, int pagina, String region){
        disposable.add(
                repository.buscaFilmes(api, language, query, pagina, region)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((movie) -> {
                    mutableBuscaFilme.setValue(movie.getResults());
                }, throwable -> Log.i("Erro", throwable.getMessage()) )
        );
    }

    public void getResultSeries(String api, String language, String query, int pagina, String region){
        disposable.add(
                repository.buscaSeries(api, language, query, pagina, region )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(seriesTop -> {
                    mutableBuscaSerie.setValue(seriesTop.getResults());
                }, throwable ->
                        mutableErro.setValue(throwable.getMessage()))
        );
    }

    public void getResultPessoa(String api, String language, String query, int pagina, String region){
        disposable.add(
                repository.buscaPessoas(api, language, query, pagina, region )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(pessoas -> {
                            mutablePessoaBusca.setValue(pessoas.getResults());
                        }, throwable ->
                                mutableErro.setValue(throwable.getMessage()))
        );
    }




    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
