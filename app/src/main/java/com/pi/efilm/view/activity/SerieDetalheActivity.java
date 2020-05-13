package com.pi.efilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pi.efilm.R;
import com.pi.efilm.model.series.CreatedBy;
import com.pi.efilm.model.series.ResultSeriesDetalhe;
import com.pi.efilm.model.series.Season;
import com.pi.efilm.util.AppUtil;
import com.pi.efilm.view.adapter.SeasonAdapter;
import com.pi.efilm.viewmodel.SerieViewModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.PT_BR;
import static com.pi.efilm.util.Constantes.URL_IMAGEM;

public class SerieDetalheActivity extends AppCompatActivity {
    private SerieViewModel viewModel;
    private ResultSeriesDetalhe resultSeriesDetalhe;
    private List<Season> seasonList = new ArrayList<>();
    private ImageButton botaoHome;
    private long idFilme;
    private TextView data, sinopse, titulo, tituloOriginal, status, diretor, nota,  numeroSeasons, numeroEps;
    private ImageView imageView, imageFavorito, imagemShare;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewSeason;
    private SeasonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detalhe);
        initView();
        recuperaIdFilme();
        viewModel.getSerieDetalhe(idFilme ,API_KEY, PT_BR);

        viewModel.liveDataSerieDetalhe.observe(this, resultSeriesDetalhe1 -> {
            setDetalhes(resultSeriesDetalhe1);
            viewModel.corBotaoFavoritos(resultSeriesDetalhe1, this, imageFavorito);
            resultSeriesDetalhe = resultSeriesDetalhe1;
        });
        viewModel.liveDataLoading.observe(this, aBoolean -> {
            if(aBoolean){
                progressBar.setVisibility(View.VISIBLE);
            } else progressBar.setVisibility(View.INVISIBLE);
        });

        viewModel.resultLiveDataError.observe(this, error -> {
            Snackbar snackbar = Snackbar.make(imageFavorito, error.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
        });
        adicionarFavorito();
        viewModel.favoritoAdd.observe(this, result -> {
            if (result != null) {
                Snackbar snackbar = Snackbar.make(imageFavorito, result.getName() + getString(R.string.add_favorito), Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(Color.parseColor("#4CAF50"));
                snackbar.show();
            }
        });

        botaoHome.setOnClickListener(v -> AppUtil.botaoHome(this));
        compartilhar();
    }

    private void initView(){
        data = findViewById(R.id.dataSerieDetalhe);
        imageView = findViewById(R.id.imagemSerieDetalhe);
        sinopse = findViewById(R.id.sinopseSerieDetalhe);
        titulo = findViewById(R.id.tituloSerieDetalhe);
        status = findViewById(R.id.statusSerie);
        tituloOriginal = findViewById(R.id.originalSerieDetalhe);
        viewModel = ViewModelProviders.of(this).get(SerieViewModel.class);
        progressBar = findViewById(R.id.progressBarSeriesDetalhe);
        diretor = findViewById(R.id.diretorSerieDetalhe);
        numeroSeasons = findViewById(R.id.numeroSeasons);
        nota = findViewById(R.id.notaSerie);
        numeroEps = findViewById(R.id.numeroEpsSerie);
        imageFavorito =  findViewById(R.id.imageFavoritoSerie);
        botaoHome = findViewById(R.id.botaoHomeSerie);
        imagemShare = findViewById(R.id.shareSerie);
    }

    private void recuperaIdFilme(){
        Bundle bundle = getIntent().getExtras();
        idFilme = bundle.getLong(ID );
    }

    private void setDetalhes(ResultSeriesDetalhe result){
        List<CreatedBy> created = result.getCreatedBy();
        String criadores = "  ";
        for (CreatedBy c: created) {
                criadores += c.getName() + ", ";
        }
        diretor.setText(getString(R.string.de) + criadores.substring(0, criadores.length() -2));

        if(result.getOverview().equals("")){
            sinopse.setText(R.string.sinopse_indisponivel);
        } else {
            sinopse.setText(result.getOverview());
        }
        tituloOriginal.setText(result.getOriginalName());
        Picasso.get().load(URL_IMAGEM+ result.getPosterPath()).into(imageView);
        titulo.setText(result.getName());
        status.setText(result.getStatus());
        initRecycler(result);
        nota.setText(result.getVoteAverage().toString() + getString(R.string.avaliacao_10));
        numeroEps.setText(getString(R.string.total_episodios) + result.getNumberOfEpisodes().toString());
        numeroSeasons.setText(getString(R.string.temporadas) + result.getNumberOfSeasons().toString());
    }

    private void initRecycler(ResultSeriesDetalhe resultSeriesDetalhe){
        recyclerViewSeason = findViewById(R.id.recyclerSeason);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewSeason.setLayoutManager(layoutManager);
        seasonList = resultSeriesDetalhe.getSeasons();
        adapter = new SeasonAdapter(resultSeriesDetalhe.getSeasons(), resultSeriesDetalhe.getId());
        recyclerViewSeason.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerViewSeason.setAdapter(adapter);
    }

    private void adicionarFavorito() {
        imageFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.verificaConexaoComInternet(getApplicationContext())) {
                    if (AppUtil.verificarLogado()) {
                        viewModel.salvarFirebase(resultSeriesDetalhe, getApplicationContext(), imageFavorito);
                    } else
                        Snackbar.make(imageFavorito, R.string.adicionar_logado, Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(imageFavorito, R.string.adicionar_conexao, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void compartilhar(){
        imagemShare.setOnClickListener(v -> {
            String stringCompartilhar = "Recomendo a série "+ resultSeriesDetalhe.getName() + "\nhttps://www.themoviedb.org/tv/" + idFilme;
            AppUtil.compartilhar(stringCompartilhar, progressBar, this);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
