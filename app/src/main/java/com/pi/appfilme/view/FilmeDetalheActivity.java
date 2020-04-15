package com.pi.appfilme.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.viewmodel.FilmeViewModel;
import com.squareup.picasso.Picasso;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Language.PT_BR;

public class FilmeDetalheActivity extends AppCompatActivity {
    private ImageView imagemFilme;
    private TextView tituloFilme, duracao, originalTitle, sinopse, orcamento, bilheteria, data, diretor;
    private FilmeViewModel viewModel;
    private long idFilme;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhe);
        initView();
        recuperaIdFilme();
        viewModel.getFilmeDetalhe(idFilme, API_KEY, PT_BR);
        viewModel.liveDataDetalhes.observe(this, detalhes -> {
            setDetalhes(detalhes);
        });
        viewModel.liveDataLoading.observe(this, aBoolean -> {
            if(aBoolean){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        Log.i("LOG", "filme" + idFilme);
    }

    public void initView(){
        imagemFilme = findViewById(R.id.imagemFilmeDetalhe);
        tituloFilme = findViewById(R.id.tituloFilmeDetalhe);
        duracao = findViewById(R.id.duracaoFilmeDetalhe);
        originalTitle = findViewById(R.id.originalTitlefilmeDetalhe);
        sinopse = findViewById(R.id.sinopseFilmeDetalhe);
        progressBar = findViewById(R.id.progressBarDetalhe);
        orcamento = findViewById(R.id.orcamentoDetalhe);
        diretor = findViewById(R.id.deDetalhe);
        bilheteria = findViewById(R.id.bilheteriaDetalhe);
        data = findViewById(R.id.estreiaFilmeDetalhe);
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
    }

    public void recuperaIdFilme(){
        Bundle bundle = getIntent().getExtras();
        idFilme = bundle.getLong("Id");
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

