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
import com.pi.efilm.model.filme.BuscaEBreve.ResultFilme;
import com.pi.efilm.model.filme.detalhes.Detalhes;
import com.pi.efilm.repository.FilmeRepository;
import com.pi.efilm.util.AppUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.pi.efilm.util.Constantes.FAVORITOS;

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
    public MutableLiveData<Throwable> resultLiveDataError = new MutableLiveData<>();
    private MutableLiveData<List<Detalhes>> mutableFavoritos = new MutableLiveData<>();
    public LiveData<List<Detalhes>> liveDataFavoritos = mutableFavoritos;
    private MutableLiveData<List<Detalhes>> mutableFavFirebase = new MutableLiveData<>();
    public LiveData<List<Detalhes>> liveDataFavFirebase = mutableFavFirebase;
    private MutableLiveData<List<ResultFilme>> mutableBilheteria = new MutableLiveData<>();
    public LiveData<List<ResultFilme>> liveDataBilheteria = mutableBilheteria;
    public MutableLiveData<Detalhes> favoritoAdd = new MutableLiveData<>();
    private MutableLiveData<List<ResultFilme>> mutableFilmeSimilar =  new MutableLiveData<>();
    public LiveData<List<ResultFilme>> liveDataSimilar = mutableFilmeSimilar;
    private MutableLiveData<List<ResultFilme>> mutableRecomendado =  new MutableLiveData<>();
    public LiveData<List<ResultFilme>> liveDataRecomendado = mutableRecomendado;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference reference = database.getReference(AppUtil.getIdUsuario(getApplication()) + FAVORITOS);
    public FilmeViewModel(@NonNull Application application) {
        super(application);
    }


    public void getFilmePopular(String apiKey, String language, String region, int pagina) {
        disposable.add(
                repository.getFilmePopular(apiKey, language, region, pagina)
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

    public void getFilmeSimilar(long id, String apikey, String language, int pagina){
        disposable.add(
                repository.getFilmeSimilar(id, apikey, language, pagina)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    mutableFilmeSimilar.setValue(movie.getResults());
                }, throwable -> { mutableLiveDataErro.setValue(throwable.getMessage());})
        );
    }

    public void getFilmeRecomendado(long id, String apikey, String language, int pagina){
        disposable.add(
                repository.getFilmeRecomendado(id, apikey, language, pagina)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(movie -> {
                            mutableRecomendado.setValue(movie.getResults());
                        }, throwable -> { mutableLiveDataErro.setValue(throwable.getMessage());})
        );
    }



    public void getBilheteria(String apiKey, String language, String sort, int pagina) {
        disposable.add(
                repository.buscaBilheteria(apiKey, language, sort, pagina)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> mutableLiveDataLoading.setValue(true))
                        .doOnTerminate(() -> mutableLiveDataLoading.setValue(false))
                        .subscribe(movie -> {
                            mutableBilheteria.setValue(movie.getResults());
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

    public void getFilmeDetalheDB(Context context, long id){
        disposable.add(
                repository.getDetalheDB(context, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(detalhes -> {
                    mutableLiveDataDetalhes.setValue(detalhes);
                }, throwable -> {
                    mutableLiveDataErro.setValue(throwable.getMessage());
                })
        );
    }

    public void removeFavorito(Detalhes detalhes, Context context) {
        new Thread(() -> {
            repository.removeFavorito(detalhes, context);
        }).start();
    }

    public void salvarFirebase(Detalhes detalhes, Context context, ImageView imageView) {
        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existe = false;
                for (DataSnapshot snapshotResult : dataSnapshot.getChildren()) {
                    Detalhes detalhes1 = snapshotResult.getValue(Detalhes.class);

                    if (detalhes1 != null && detalhes1.getId() != null && detalhes1.getId().equals(detalhes.getId())) {
                        existe = true;
                    }
                }
                if (existe) {
                    resultLiveDataError.setValue(new Throwable(detalhes.getTitle() + ": Já existe nos favoritos"));
                } else {
                    salvarFirebaseVerificado(reference, detalhes, context);
                    imageView.setImageResource(R.drawable.ic_favorite_24dp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    private void salvarFirebaseVerificado(DatabaseReference databaseReference, Detalhes detalhes, Context context) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(detalhes);
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Detalhes detalhes = dataSnapshot.getValue(Detalhes.class);
                insereFavorito(detalhes, context);
                favoritoAdd.setValue(detalhes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void corBotaoFavoritos(Detalhes detalhes, Context context, ImageView imageView){
        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existe = false;
                for (DataSnapshot snapshotResult : dataSnapshot.getChildren()) {
                    Detalhes detalhes1 = snapshotResult.getValue(Detalhes.class);

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
