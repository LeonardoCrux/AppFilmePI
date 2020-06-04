package com.pi.efilm.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.BR;
import static com.pi.efilm.util.Constantes.CLICK;
import static com.pi.efilm.util.Constantes.NOME;
import static com.pi.efilm.util.Constantes.PT_BR;

public class ResultadoBuscaActivity extends AppCompatActivity {
    private RecyclerView recyclerView, recyclerViewSerie, recyclerViewPessoa;
    private SearchFilmeAdapter searchFilmeAdapter;
    private SearchSerieAdapter searchSerieAdapter;
    private SearchPessoaAdapter searchPessoaAdapter;
    private TextView textFilme, textSerie, textPessoas;
    private BuscaViewModel buscaViewModel;
    public static String query = "";
    private int pagina = 1;
    private List<ResultFilme> listaFilme = new ArrayList<>();
    private List<ResultSeriesTop> listaSeries = new ArrayList<>();
    private List<ResultPessoaPop> listaPessoas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busca);
        initViews();
        recuperaDados();
        buscaViewModel.getResultFilme(API_KEY, PT_BR, query, pagina, BR);
        buscaViewModel.liveDataFilme.observe(this, (List<ResultFilme> resultFilmes) -> searchFilmeAdapter.atualizaLista(resultFilmes));
        buscaViewModel.getResultSeries(API_KEY, PT_BR, query, pagina, BR);
        buscaViewModel.liveDataSerie.observe(this, resultSeriesTops -> searchSerieAdapter.atualizaLista(resultSeriesTops));
        buscaViewModel.getResultPessoa(API_KEY, PT_BR, query, pagina, BR);
        buscaViewModel.liveDataPessoa.observe(this, resultPessoaPops -> searchPessoaAdapter.atualizaLista(resultPessoaPops));
        textFilme.setOnClickListener(v -> {
            Intent intent = new Intent( this, ListaExpandidaActivity.class);
            intent.putExtra(CLICK, "1");
            startActivity(intent);
        });
        textSerie.setOnClickListener(v -> {
            Intent intent = new Intent( this, ListaExpandidaActivity.class);
            intent.putExtra(CLICK, "2");
            startActivity(intent);
        });
        textPessoas.setOnClickListener(v -> {
            Intent intent = new Intent( this, ListaPessoas.class);
            intent.putExtra(CLICK, query);
            startActivity(intent);
        });

    }

    private void initViews() {
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
        textFilme = findViewById(R.id.textFilmeBusca);
        textSerie = findViewById(R.id.textSerieBusca);
        textPessoas = findViewById(R.id.textPessoaBusca);
    }


    private void recuperaDados() {
        Bundle bundle = getIntent().getExtras();
        query = bundle.getString(NOME);
    }
}
