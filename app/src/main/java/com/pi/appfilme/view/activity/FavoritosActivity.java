package com.pi.appfilme.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.view.adapter.FavoritosAdapter;
import com.pi.appfilme.view.interfaces.FavoritosListener;
import com.pi.appfilme.viewmodel.FilmeViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class FavoritosActivity extends AppCompatActivity implements FavoritosListener {
    private RecyclerView recyclerView;
    private FavoritosAdapter adapter;
    private FilmeViewModel viewModel;
    private List<Detalhes> detalhesList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        initViews();
        viewModel.getFavoritosDB(this);
        viewModel.liveDataFavoritos.observe(this, detalhes -> adapter.atualizaLista(detalhes));

    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerFavoritoss);
        adapter =  new FavoritosAdapter(detalhesList, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
    }

    @Override
    public void deleteFavorito(Detalhes detalhes) {
        viewModel.removeFavorito(detalhes, this);
        Toast.makeText(this, R.string.removido_favoritos, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickFavorito(Detalhes detalhes) {
        Intent intent = new Intent(this, FilmeDetalheActivity.class);
        intent.putExtra(ID, detalhes.getId());
        startActivity(intent);
    }
}
