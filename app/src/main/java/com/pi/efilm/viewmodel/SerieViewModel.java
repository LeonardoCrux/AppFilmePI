package com.pi.efilm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pi.efilm.model.series.ResultSeriePopular;
import com.pi.efilm.model.series.ResultSeriesDetalhe;
import com.pi.efilm.model.series.ResultSeriesTop;
import com.pi.efilm.model.series.SeasonDetalhes.SeasonDetalhes;

import com.pi.efilm.repository.FilmeRepository;

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

    private MutableLiveData<List<ResultSeriesTop>> mutablePopular = new MutableLiveData<>();
    public LiveData<List<ResultSeriesTop>> liveDataPopular = mutablePopular;

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

    public void getSeriesPopular(String apiKey, String language, int page){
        disposable.add(
                repository.getSeriesPopular(apiKey, language, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(seriesTop -> {
                    mutablePopular.setValue(seriesTop.getResults());
                    }, throwable -> {
                    mutableLiveDataErro.setValue(throwable.getMessage());
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
