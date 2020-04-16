package com.pi.appfilme.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pi.appfilme.R;
import com.pi.appfilme.view.adapter.ElencoAdapter;
import com.pi.appfilme.model.filme.creditos.Cast;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.viewmodel.PessoaViewModel;
import com.pi.appfilme.viewmodel.FilmeViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Language.PT_BR;

public class FilmeDetalheActivity extends AppCompatActivity {
    private ImageView imagemFilme;
    private TextView tituloFilme, duracao, originalTitle, sinopse, orcamento, bilheteria, data, diretor;
    private FilmeViewModel viewModelFilme;
    private PessoaViewModel viewModelElenco;
    private long idFilme;
    private ElencoAdapter adapter;
    private List<Cast> castList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhe);
        initView();
        recuperaIdFilme();
        viewModelFilme.getFilmeDetalhe(idFilme, API_KEY, PT_BR);
        viewModelFilme.liveDataDetalhes.observe(this, detalhes -> {
            setDetalhes(detalhes);
        });
        viewModelFilme.liveDataLoading.observe(this, aBoolean -> {
            if(aBoolean){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        viewModelElenco.getCast(idFilme, "9e388e7de5c0c42386ebad1002886539");
        viewModelElenco.liveDataCast.observe(this, casts -> adapter.atualizaLista(casts));

    }

    public void initView(){
        imagemFilme = findViewById(R.id.imagemFilmeDetalhe);
        tituloFilme = findViewById(R.id.tituloFilmeDetalhe);
        duracao = findViewById(R.id.duracaoFilmeDetalhe);
        originalTitle = findViewById(R.id.originalTitlefilmeDetalhe);
        sinopse = findViewById(R.id.sinopseFilmeDetalhe);
        progressBar = findViewById(R.id.progressBarDetalhe);
        orcamento = findViewById(R.id.orcamentoDetalhe);
        diretor = findViewById(R.id.dataFilmeDetalhe);
        bilheteria = findViewById(R.id.bilheteriaDetalhe);
        data = findViewById(R.id.estreiaFilmeDetalhe);
        viewModelFilme = ViewModelProviders.of(this).get(FilmeViewModel.class);
        viewModelElenco = ViewModelProviders.of(this).get(PessoaViewModel.class);

        recyclerView = findViewById(R.id.recyclerElenco);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ElencoAdapter(castList);
        recyclerView.setAdapter(adapter);
    }

    public void recuperaIdFilme(){
        Bundle bundle = getIntent().getExtras();
        idFilme = bundle.getLong("ID");
    }

    public void setDetalhes(Detalhes detalhes){
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ detalhes.getPosterPath()).into(imagemFilme);
        tituloFilme.setText(detalhes.getTitle());
        duracao.setText(detalhes.getRuntime().toString() + " min");
        originalTitle.setText(detalhes.getOriginalTitle());
        sinopse.setText(detalhes.getOverview());
        if(detalhes.getBudget() == 0){
            orcamento.setVisibility(View.INVISIBLE);
        } else{orcamento.setText("Orçamento: " + detalhes.getBudget());}
        if(detalhes.getRevenue() == 0){
            bilheteria.setVisibility(View.INVISIBLE);
        } else{bilheteria.setText("Bilheteria: " + detalhes.getRevenue());}
        data.setText(detalhes.getReleaseDate());
    }
    }

