package com.pi.efilm.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pi.efilm.R;
import com.pi.efilm.model.filme.BuscaEBreve.ResultFilme;
import com.pi.efilm.model.pessoa.pessoa.ResultPessoaPop;
import com.pi.efilm.model.series.ResultSeriesTop;
import com.pi.efilm.view.adapter.SearchFilmeAdapter;
import com.pi.efilm.view.adapter.SearchPessoaAdapter;
import com.pi.efilm.view.adapter.SearchSerieAdapter;
import com.pi.efilm.viewmodel.BuscaViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.efilm.util.Constantes.Hash.API_KEY;
import static com.pi.efilm.util.Constantes.Language.PT_BR;
import static com.pi.efilm.util.Constantes.NOME;
import static com.pi.efilm.util.Constantes.Region.BR;

public class ResultadoBuscaActivity extends AppCompatActivity {
    private RecyclerView recyclerView, recyclerViewSerie, recyclerViewPessoa;
    private SearchFilmeAdapter searchFilmeAdapter;
    private SearchSerieAdapter searchSerieAdapter;
    private SearchPessoaAdapter searchPessoaAdapter;
    private BuscaViewModel buscaViewModel;
    private String query;
    private int pagina = 1;
    private List<ResultFilme> listaFilme =  new ArrayList<>();
    private List<ResultSeriesTop> listaSeries =  new ArrayList<>();
    private List<ResultPessoaPop> listaPessoas =  new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busca);
        initViews();
        setScrollView();
        recuperaDados();
        buscaViewModel.getResultFilme(API_KEY, PT_BR, query, pagina, BR);
        buscaViewModel.liveDataFilme.observe(this, (List<ResultFilme> resultFilmes) -> searchFilmeAdapter.atualizaLista(resultFilmes));
        buscaViewModel.getResultSeries(API_KEY, PT_BR, query, pagina, BR);
        buscaViewModel.liveDataSerie.observe(this, resultSeriesTops -> searchSerieAdapter.atualizaLista(resultSeriesTops));
        buscaViewModel.getResultPessoa(API_KEY, PT_BR, query, pagina, BR);
        buscaViewModel.liveDataPessoa.observe(this, resultPessoaPops -> searchPessoaAdapter.atualizaLista(resultPessoaPops));
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerSearchFilme);
        recyclerViewSerie = findViewById(R.id.recyclerBuscaSeries);
        recyclerViewPessoa = findViewById(R.id.recyclerPessoaBusca);
        searchSerieAdapter = new SearchSerieAdapter(listaSeries);
        searchFilmeAdapter = new SearchFilmeAdapter(listaFilme);
        searchPessoaAdapter = new SearchPessoaAdapter(listaPessoas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSerie.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPessoa.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchFilmeAdapter);
        recyclerViewSerie.setAdapter(searchSerieAdapter);
        recyclerViewPessoa.setAdapter(searchPessoaAdapter);
        buscaViewModel = ViewModelProviders.of(this).get(BuscaViewModel.class);

    }


    public void recuperaDados(){
        Bundle bundle = getIntent().getExtras();
        query = bundle.getString(NOME);
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
                    buscaViewModel.getResultFilme(API_KEY, PT_BR, query, pagina, BR);
                }
            }
        });
    }
}
