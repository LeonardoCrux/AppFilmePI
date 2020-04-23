package com.pi.appfilme.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.pi.appfilme.network.API;
import com.pi.appfilme.network.FilmeService;
import com.pi.appfilme.view.adapter.AdapterBuscaFilmes;
import com.pi.appfilme.viewmodel.FilmeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Language.PT_BR;
import static com.pi.appfilme.util.Constantes.Region.BR;

public class ResultadoBuscaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterBuscaFilmes adapter;
    private FilmeViewModel viewModel;
    private List<ResultFilme> resultFilmeList = new ArrayList<>();
    private String busca;
    private int pagina = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busca);
        initView();
        recuperaDados();
        viewModel.buscaFilmes(API_KEY, PT_BR, busca, pagina, BR);
        viewModel.liveDataBusca.observe(this, (List<ResultFilme> resultFilmes) -> adapter.atualizaLista(resultFilmes));
    }

    public void initView(){
        recyclerView = findViewById(R.id.recyclerBuscaFilmes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterBuscaFilmes(resultFilmeList);
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
    }

    public void recuperaDados(){
        Bundle bundle = getIntent().getExtras();
        busca = bundle.getString("NOME");
    }

    private void setScrollView(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = layoutManager.getItemCount();
                int ultimoVisivel = layoutManager.findLastVisibleItemPosition();
                boolean ultimoItem = ultimoVisivel + 5 >= itemCount;
                if(itemCount > 0 && ultimoItem){
                    pagina++;
                    viewModel.buscaFilmes(API_KEY, PT_BR, busca, pagina, BR);
                }
            }
        });
    }
}
