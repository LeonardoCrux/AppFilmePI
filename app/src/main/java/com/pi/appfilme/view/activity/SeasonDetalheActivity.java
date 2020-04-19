package com.pi.appfilme.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.series.ResultSeriesDetalhe;
import com.pi.appfilme.model.series.SeasonDetalhes.Episode;
import com.pi.appfilme.model.series.SeasonDetalhes.SeasonDetalhes;
import com.pi.appfilme.util.Constantes;
import com.pi.appfilme.view.adapter.EpisodiosAdapter;
import com.pi.appfilme.view.adapter.SeasonAdapter;
import com.pi.appfilme.view.fragment.EpisodiosFragment;
import com.pi.appfilme.view.interfaces.Comunicador;
import com.pi.appfilme.viewmodel.SerieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;

public class SeasonDetalheActivity extends AppCompatActivity{
    private SerieViewModel viewModel;
    private RecyclerView recyclerViewEpisodio;
    private TextView nomeSeason, dataSeason, sinopseSeason;
    private ImageView imagemSeason;
    private EpisodiosAdapter adapter;
    private List<Episode> episodeList;
    private long idSerie;
    private long number;
    private long longDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detalhe);
        initView();
        recuperaIdSeason();
        viewModel.getSeasonDetalhe(idSerie, number, API_KEY, Constantes.Language.PT_BR);
        viewModel.liveDataSeason.observe(this, (SeasonDetalhes seasonDetalhes) -> {setDetalhes(seasonDetalhes);});
    }

    public void recuperaIdSeason(){
        Bundle bundle = getIntent().getExtras();
        idSerie = bundle.getLong("ID");
        number = bundle.getLong("NUMBER");
        Log.i("DADOS", "ID" + idSerie + " number" + number);
    }

    public void initView(){
        nomeSeason = findViewById(R.id.nameSeasonDetalhe);
        dataSeason = findViewById(R.id.estreiaSerieDetalhe);
        sinopseSeason = findViewById(R.id.sinopseSeason);
        imagemSeason = findViewById(R.id.imagemSeasonDetalhe);
        viewModel = ViewModelProviders.of(this).get(SerieViewModel.class);
    }

    public void setDetalhes(SeasonDetalhes detalhes){
        nomeSeason.setText(detalhes.getName());
        sinopseSeason.setText(detalhes.getOverview());
        dataSeason.setText(detalhes.getAirDate());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ detalhes.getPosterPath()).into(imagemSeason);
        initRecycler(detalhes);
    }


    private void initRecycler(SeasonDetalhes seasonDetalhes){
        recyclerViewEpisodio = findViewById(R.id.recyclerEpisodios);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewEpisodio.setLayoutManager(layoutManager);
        episodeList = seasonDetalhes.getEpisodes();
        adapter = new EpisodiosAdapter(seasonDetalhes.getEpisodes());
        recyclerViewEpisodio.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerViewEpisodio.setAdapter(adapter);
    }
}
