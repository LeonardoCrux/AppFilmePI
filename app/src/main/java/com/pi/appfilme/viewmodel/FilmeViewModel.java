package com.pi.appfilme.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FilmeViewModel extends AndroidViewModel {
    private FilmeRepository repository = new FilmeRepository();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<ResultFilme>> mutableLiveDataPlaying = new MutableLiveData<>();
    public LiveData<List<ResultFilme>> liveData = mutableLiveDataPlaying;
    private MutableLiveData<List<ResultFilme>> mutableLiveDataTop = new MutableLiveData<>();
    public LiveData<List<ResultFilme>> liveDataTop = mutableLiveDataTop;
    private MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableLiveDataErro;
    private MutableLiveData<Boolean> mutableLiveDataLoading = new MutableLiveData<>();
    public LiveData<Boolean> liveDataLoading = mutableLiveDataLoading;
    private MutableLiveData<Detalhes> mutableLiveDataDetalhes = new MutableLiveData<>();
    public LiveData<Detalhes> liveDataDetalhes = mutableLiveDataDetalhes;

    private MutableLiveData<List<Detalhes>> mutableFavoritos = new MutableLiveData<>();
    public LiveData<List<Detalhes>> liveDataFavoritos = mutableFavoritos;

    private MutableLiveData<List<ResultFilme>> mutableBusca = new MutableLiveData<>();
    public LiveData<List<ResultFilme>> liveDataBusca = mutableBusca;


    public FilmeViewModel(@NonNull Application application) {
        super(application);
    }

    public void buscaFilmes(String apiKey, String language, String query, int pagina, String region) {
        disposable.add(
                repository.buscaFilmes(apiKey, language, query, pagina, region)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> mutableLiveDataLoading.setValue(true))
                        .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                        .subscribe(resultFilmes -> {
                            mutableBusca.setValue(resultFilmes.getResultFilmes());
                        }, throwable -> {
                            mutableLiveDataErro.setValue(throwable.getMessage());
                        }));
    }

    public void getPlaying(String apiKey, String language, String region, int pagina) {
        disposable.add(
                repository.getPlaying(apiKey, language, region, pagina)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> mutableLiveDataLoading.setValue(true))
                        .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                        .subscribe(movie -> {
                            mutableLiveDataPlaying.setValue(movie.getResults());
                        }, throwable -> {
                            mutableLiveDataErro.setValue(throwable.getMessage());
                        }));
    }


    public void getTop(String apiKey, String language, String region, int pagina) {
        disposable.add(
                repository.getTop(apiKey, language, region, pagina)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> mutableLiveDataLoading.setValue(true))
                        .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                        .subscribe(movie -> {
                            mutableLiveDataTop.setValue(movie.getResults());
                        }, throwable -> {
                            mutableLiveDataErro.setValue(throwable.getMessage());
                        }));
    }

    public void getFilmeDetalhe(long id, String apiKey, String language) {
        disposable.add(
                repository.getFilmeDetalhes(id, apiKey, language)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> mutableLiveDataLoading.setValue(true))
                        .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                        .subscribe(detalhes -> {
                            mutableLiveDataDetalhes.setValue(detalhes);
                        }, throwable -> {
                            mutableLiveDataErro.setValue(throwable.getMessage());
                        }));
    }


    public void getFavoritosDB(Context context) {
        disposable.add(
                repository.getFavoritosDB(getApplication())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(detalhes -> {
                            mutableFavoritos.setValue(detalhes);
                        }, throwable -> {
                            mutableLiveDataErro.setValue(throwable.getMessage() + "Erro DB");
                        }));
    }

    public void insereFavorito(Detalhes detalhes, Context context) {
        new Thread((() -> {
            if (detalhes != null) {
                repository.insereDadosDB(detalhes, context);
            }
        }
        )).start();
    }

    public void removeFavorito(Detalhes detalhes, Context context) {
new Thread(() -> {
    repository.removeFavorito(detalhes, context);
}).start();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }


}
