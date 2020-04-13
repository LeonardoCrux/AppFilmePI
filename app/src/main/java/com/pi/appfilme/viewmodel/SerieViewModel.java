package com.pi.appfilme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.pi.appfilme.model.series.ResultSeries;
import com.pi.appfilme.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SerieViewModel extends AndroidViewModel {
    private FilmeRepository repository = new FilmeRepository();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<ResultSeries>> mutableLiveDataPopular = new MutableLiveData<>();
    public LiveData<List<ResultSeries>> liveData = mutableLiveDataPopular;
    private MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableLiveDataErro;
    private MutableLiveData<Boolean> mutableLiveDataLoading = new MutableLiveData<>();
    public LiveData<Boolean> liveDataLoading = mutableLiveDataLoading;
    public SerieViewModel(@NonNull Application application) {
        super(application);
    }

    public void getPopularSeries(String apiKey, String language, int pagina){
        disposable.add(
                repository.getSeriesPopulares(apiKey, language, pagina)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(seriesPopulares -> {
                    mutableLiveDataPopular.setValue(seriesPopulares.getResultSeries());
                }, throwable -> {
                    mutableLiveDataErro.setValue(throwable.getMessage());
                }));
    }
}
