package com.pi.appfilme.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.pi.appfilme.view.adapter.SearchFilmeAdapter;
import com.pi.appfilme.viewmodel.BuscaViewModel;
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
    private SearchFilmeAdapter searchFilmeAdapter;
    private BuscaViewModel buscaViewModel;
    private String busca;
    private int pagina = 1;
    private List<ResultFilme> listaFilme =  new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busca);
        initViews();
        recuperaDados();
        buscaViewModel.getResultFilme(API_KEY, PT_BR, busca, 1, BR);
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerSearchFilme);
        searchFilmeAdapter = new SearchFilmeAdapter(listaFilme);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchFilmeAdapter);
        buscaViewModel = ViewModelProviders.of(this).get(BuscaViewModel.class);
    }


    public void recuperaDados(){
        Bundle bundle = getIntent().getExtras();
        busca = bundle.getString("NOME");
    }

    public void funciona(){
        buscaViewModel.liveDataFilme.observe(this, resultFilmes -> searchFilmeAdapter.atualizaLista(resultFilmes));
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
                    buscaViewModel.getResultFilme(API_KEY, PT_BR, busca, pagina, BR);
                }
            }
        });
    }
}
