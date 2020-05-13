package com.pi.efilm.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pi.efilm.R;
import com.pi.efilm.model.filme.detalhes.Detalhes;
import com.pi.efilm.model.series.ResultSeriesDetalhe;
import com.pi.efilm.util.AppUtil;
import com.pi.efilm.view.adapter.FavoritosAdapter;
import com.pi.efilm.view.adapter.FavoritosSerieAdapter;
import com.pi.efilm.view.interfaces.FavoritosListener;
import com.pi.efilm.viewmodel.FilmeViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.FAVORITOS;
import static com.pi.efilm.util.Constantes.FAVORITOS_SERIE;

public class FavoritosActivity extends AppCompatActivity implements FavoritosListener {
    private RecyclerView recyclerView, recyclerSerie;
    private FavoritosAdapter adapter;
    private FavoritosSerieAdapter adapterSeries;
    private FilmeViewModel viewModel;
    private List<Detalhes> detalhesList = new ArrayList<>();
    private List<ResultSeriesDetalhe> seriesDetalhesList = new ArrayList<>();
    private FirebaseDatabase database, databaseSerie;
    private DatabaseReference reference, referenceSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        initViews();
        viewModel.getFavoritosDB(this);
        viewModel.liveDataFavoritos.observe(this, detalhes -> adapter.atualizaLista(detalhes));
        carregaFavoritosSerie();

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerFavoritoss);
        adapter = new FavoritosAdapter(detalhesList, this);
        adapterSeries = new FavoritosSerieAdapter(seriesDetalhesList, this);
        recyclerSerie = findViewById(R.id.recyclerSerieFavorito);
        recyclerView.setAdapter(adapter);
        recyclerSerie.setAdapter(adapterSeries);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerSerie.setLayoutManager(new LinearLayoutManager(this));
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
        database = FirebaseDatabase.getInstance();
        databaseSerie = FirebaseDatabase.getInstance();
        reference = database.getReference(AppUtil.getIdUsuario(getApplicationContext()) + FAVORITOS);
        referenceSerie = databaseSerie.getReference(AppUtil.getIdUsuario(getApplicationContext()) + FAVORITOS_SERIE);
    }

    private void carregaFavoritosSerie() {
        referenceSerie.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ResultSeriesDetalhe resultSeriesDetalhe = child.getValue(ResultSeriesDetalhe.class);
                    seriesDetalhesList.add(resultSeriesDetalhe);
                }
                adapterSeries.atualizaLista(seriesDetalhesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void deleteFavorito(Detalhes detalhes1) {
        if (AppUtil.verificaConexaoComInternet(getApplicationContext())) {
            if (AppUtil.verificarLogado()) {
                removeFavoritoFirebase(detalhes1);
                viewModel.removeFavorito(detalhes1, this);
                Toast.makeText(this, R.string.removido_favoritos, Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, R.string.remover_logado, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, R.string.remover_conexão, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickFavorito(Detalhes detalhes) {
        Intent intent = new Intent(this, FilmeDetalheActivity.class);
        intent.putExtra(ID, detalhes.getId());
        startActivity(intent);
    }

    @Override
    public void deleteFavoritoSerie(ResultSeriesDetalhe detalhes) {
        if (AppUtil.verificaConexaoComInternet(getApplicationContext())) {
            if (AppUtil.verificarLogado()) {
                removeFavoritoSerieFirebase(detalhes);
                Toast.makeText(this, R.string.removido_favoritos, Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, R.string.remover_logado, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, R.string.remover_conexão, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickFavoritoSerie(ResultSeriesDetalhe detalhes) {
        Intent intent = new Intent(this, SerieDetalheActivity.class);
        intent.putExtra(ID, detalhes.getId());
        startActivity(intent);
    }

    private void removeFavoritoFirebase(Detalhes detalhes1) {
        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    if (child.getValue(ResultSeriesDetalhe.class).getId().equals(detalhes1.getId())) {
                        child.getRef().removeValue();
                        adapter.removeItem(detalhes1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removeFavoritoSerieFirebase(ResultSeriesDetalhe detalhes) {
        referenceSerie.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    if (child.getValue(ResultSeriesDetalhe.class).getId().equals(detalhes.getId())) {
                        child.getRef().removeValue();
                        adapterSeries.removeItem(detalhes);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
