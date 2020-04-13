package com.pi.appfilme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
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


    public FilmeViewModel(@NonNull Application application) {
        super(application);
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


}
