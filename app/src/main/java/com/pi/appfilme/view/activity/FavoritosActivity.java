package com.pi.appfilme.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.view.adapter.FavoritosAdapter;
import com.pi.appfilme.viewmodel.FilmeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritosActivity extends AppCompatActivity {
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

    public void initViews(){
        recyclerView = findViewById(R.id.recyclerFavoritos);
        adapter =  new FavoritosAdapter(detalhesList);
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
    }
}
