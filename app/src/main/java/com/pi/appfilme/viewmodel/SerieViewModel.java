package com.pi.appfilme.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pi.appfilme.model.series.ResultSeriesDetalhe;
import com.pi.appfilme.model.series.ResultSeriesTop;
import com.pi.appfilme.model.series.SeasonDetalhes.SeasonDetalhes;
import com.pi.appfilme.repository.FilmeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SerieViewModel extends AndroidViewModel {
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository = new FilmeRepository();
    private MutableLiveData<List<ResultSeriesTop>> mutableSeriesTop = new MutableLiveData<>();
    public LiveData<List<ResultSeriesTop>> liveDataSeriesTop = mutableSeriesTop;
    private MutableLiveData<ResultSeriesDetalhe> mutableSerieDetalhe = new MutableLiveData<>();
    public LiveData<ResultSeriesDetalhe> liveDataSerieDetalhe = mutableSerieDetalhe;

    private MutableLiveData<Boolean> mutableLoading = new MutableLiveData<>();
    public LiveData<Boolean> liveDataLoading = mutableLoading;
    private MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableLiveDataErro;

    private MutableLiveData<SeasonDetalhes> mutableSeason = new MutableLiveData<>();
    public LiveData<SeasonDetalhes> liveDataSeason = mutableSeason;


    public SerieViewModel(@NonNull Application application) {
        super(application);
    }

    public void getTopSeries(String apiKey, String language, int pagina) {
        disposable.add(
                repository.getSeriesTop(apiKey, language, pagina)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> mutableLoading.setValue(true))
                        .doOnTerminate(() -> mutableLoading.setValue(false))
                        .subscribe(seriesTop -> {
                            mutableSeriesTop.setValue(seriesTop.getResults());
                        }, throwable -> {
                            mutableLiveDataErro.setValue(throwable.getMessage());
                        }));
    }

    public void getSerieDetalhe(long id, String apiKey, String language){
        disposable.add(
                repository.getSerieDetalhe(id, apiKey, language)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> mutableLoading.setValue(true))
                        .doOnTerminate(() -> mutableLoading.setValue(false))
                        .subscribe(resultSeriesDetalhe -> {
                            mutableSerieDetalhe.setValue(resultSeriesDetalhe);
                        }, throwable -> {
                            mutableLiveDataErro.setValue(throwable.getMessage());
                        }));
    }

    public void getSeasonDetalhe(long id, long number, String apiKey, String language){
        disposable.add(
                repository.getSeason(id, number, apiKey, language)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> mutableLoading.setValue(true))
                        .doOnTerminate(() -> mutableLoading.setValue(false))
                .subscribe(seasonDetalhes -> {mutableSeason.setValue(seasonDetalhes);
                }, throwable -> mutableLiveDataErro.setValue(throwable.getMessage()))
        );
    }


}
