package com.pi.appfilme.viewmodel;

import android.app.Application;
import android.util.Log;

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

public class BuscaViewModel extends AndroidViewModel {
    private CompositeDisposable disposable =  new CompositeDisposable();
    private FilmeRepository repository =  new FilmeRepository();
    public MutableLiveData<List<ResultFilme>> mutableBuscaFilme = new MutableLiveData<>();
    public LiveData<List<ResultFilme>> liveDataFilme = mutableBuscaFilme;
    public MutableLiveData<String> mutableErro =  new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableErro;

    public BuscaViewModel(@NonNull Application application) {
        super(application);
    }

    public void getResultFilme(String api, String language, String query, int pagina, String region){
        disposable.add(
                repository.buscaFilmes(api, language, query, pagina, region)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultFilmes -> {
                    mutableBuscaFilme.setValue(resultFilmes.getResultFilmes());
                }, throwable -> Log.i("Erro", throwable.getMessage()) )
        );
    }

}
