package com.pi.appfilme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pi.appfilme.model.filme.creditos.Cast;
import com.pi.appfilme.model.filme.creditos.Creditos;
import com.pi.appfilme.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CreditosViewModel extends AndroidViewModel {
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository =  new FilmeRepository();
    private MutableLiveData<List<Cast>> mutableLiveDataCast = new MutableLiveData<>();
    public LiveData<List<Cast>> liveDataCast = mutableLiveDataCast;
    private MutableLiveData<Boolean> mutableLiveDataLoading = new MutableLiveData<>();
    public LiveData<Boolean> LiveDataBoolean = mutableLiveDataLoading;
    private MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableLiveDataErro;

    public CreditosViewModel(@NonNull Application application) {
        super(application);
    }

    public void getCast(long id, String apiKey){
        disposable.add(
                repository.getCreditos(id, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> mutableLiveDataLoading.setValue(true))
                .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                .subscribe(creditos -> { mutableLiveDataCast.setValue(creditos.getCast());
                }, throwable -> {
                    mutableLiveDataErro.setValue(throwable.getMessage());
                }));
    }
}
