package com.pi.appfilme.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.series.CreatedBy;
import com.pi.appfilme.model.series.ResultSeriesDetalhe;
import com.pi.appfilme.model.series.Season;
import com.pi.appfilme.view.adapter.FilmeAdapter;
import com.pi.appfilme.view.adapter.SeasonAdapter;
import com.pi.appfilme.viewmodel.SerieViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Language.PT_BR;

public class SerieDetalheActivity extends AppCompatActivity {
    private SerieViewModel viewModel;
    private ResultSeriesDetalhe resultSeriesDetalhe;
    private List<Season> seasonList = new ArrayList<>();
    private long idFilme;
    private TextView data, sinopse, titulo, tituloOriginal, status, diretor, nota,  numeroSeasons, numeroEps;
    private ImageView imageView;
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
        });
        viewModel.liveDataLoading.observe(this, aBoolean -> {
            if(aBoolean){
                progressBar.setVisibility(View.VISIBLE);
            } else progressBar.setVisibility(View.INVISIBLE);
        });
    }

    public void initView(){
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
    }

    public void recuperaIdFilme(){
        Bundle bundle = getIntent().getExtras();
        idFilme = bundle.getLong("ID");
    }

    public void setDetalhes(ResultSeriesDetalhe result){
        List<CreatedBy> created = result.getCreatedBy();
        String criadores = "  ";
        for (CreatedBy c: created) {
                criadores += c.getName() + ", ";
        }
        diretor.setText("De" + criadores.substring(0, criadores.length() -2));
        sinopse.setText(result.getOverview());
        tituloOriginal.setText(result.getOriginalName());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ result.getPosterPath()).into(imageView);
        titulo.setText(result.getName());
        status.setText(result.getStatus());
        initRecycler(result);
        nota.setText(result.getVoteAverage().toString());
        numeroEps.setText("Total de Episódios: " + result.getNumberOfEpisodes().toString());
        numeroSeasons.setText("Temporadas: " + result.getNumberOfSeasons().toString());
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

}
