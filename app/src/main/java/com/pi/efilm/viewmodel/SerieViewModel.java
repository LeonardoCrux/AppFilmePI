package com.pi.efilm.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pi.efilm.R;
import com.pi.efilm.model.series.ResultSeriesDetalhe;
import com.pi.efilm.model.series.ResultSeriesTop;
import com.pi.efilm.model.series.SeasonDetalhes.SeasonDetalhes;
import com.pi.efilm.repository.FilmeRepository;
import com.pi.efilm.util.AppUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.pi.efilm.util.Constantes.FAVORITOS_SERIE;

public class SerieViewModel extends AndroidViewModel {
    private CompositeDisposable disposable = new CompositeDisposable();
    private FilmeRepository repository = new FilmeRepository();
    private MutableLiveData<List<ResultSeriesTop>> mutableSeriesTop = new MutableLiveData<>();
    public LiveData<List<ResultSeriesTop>> liveDataSeriesTop = mutableSeriesTop;
    public MutableLiveData<Throwable> resultLiveDataError = new MutableLiveData<>();
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
    public MutableLiveData<ResultSeriesDetalhe> favoritoAdd = new MutableLiveData<>();

    private MutableLiveData<List<ResultSeriesTop>> mutableSimilar =  new MutableLiveData<>();
    public LiveData<List<ResultSeriesTop>> liveDataSimilar = mutableSimilar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenceSerie = database.getReference(AppUtil.getIdUsuario(getApplication()) + FAVORITOS_SERIE);


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

    public void getSerieSimilar(long id, String apikey, String language, int pagina){
        disposable.add(
                repository.getSerieSimilar(id, apikey, language, pagina)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(seriesTop -> {
                            mutableSimilar.setValue(seriesTop.getResults());
                        }, throwable -> { mutableLiveDataErro.setValue(throwable.getMessage());})
        );
    }

    public void getSerieDetalhe(long id, String apiKey, String language) {
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

    public void getSeasonDetalhe(long id, long number, String apiKey, String language) {
        disposable.add(
                repository.getSeason(id, number, apiKey, language)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> mutableLoading.setValue(true))
                        .doOnTerminate(() -> mutableLoading.setValue(false))
                        .subscribe(seasonDetalhes -> {
                            mutableSeason.setValue(seasonDetalhes);
                        }, throwable -> mutableLiveDataErro.setValue(throwable.getMessage()))
        );
    }

    public void getSeriesPopular(String apiKey, String language, int page) {
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

    public void salvarFirebase(ResultSeriesDetalhe detalhes, Context context, ImageView imageView) {
        referenceSerie.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existe = false;
                for (DataSnapshot snapshotResult : dataSnapshot.getChildren()) {
                    ResultSeriesDetalhe detalhes1 = snapshotResult.getValue(ResultSeriesDetalhe.class);

                    if (detalhes1 != null && detalhes1.getId() != null && detalhes1.getId().equals(detalhes.getId())) {
                        existe = true;
                    }
                }
                if (existe) {
                    resultLiveDataError.setValue(new Throwable(detalhes.getName() + ": Já existe nos favoritos"));
                } else {
                    salvarFirebaseVerificado(referenceSerie, detalhes, context);
                    imageView.setImageResource(R.drawable.ic_favorite_24dp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void salvarFirebaseVerificado(DatabaseReference databaseReference, ResultSeriesDetalhe detalhes, Context context) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(detalhes);
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ResultSeriesDetalhe detalhes = dataSnapshot.getValue(ResultSeriesDetalhe.class);
                favoritoAdd.setValue(detalhes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void corBotaoFavoritos(ResultSeriesDetalhe detalhes, Context context, ImageView imageView) {
        referenceSerie.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existe = false;
                for (DataSnapshot snapshotResult : dataSnapshot.getChildren()) {
                    ResultSeriesDetalhe detalhes1 = snapshotResult.getValue(ResultSeriesDetalhe.class);

                    if (detalhes1 != null && detalhes1.getId() != null && detalhes1.getId().equals(detalhes.getId())) {
                        existe = true;
                    }
                }
                if (existe) {
                    imageView.setImageResource(R.drawable.ic_favorite_24dp);
                } else {
                    imageView.setImageResource(R.drawable.ic_favorite_branco);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
