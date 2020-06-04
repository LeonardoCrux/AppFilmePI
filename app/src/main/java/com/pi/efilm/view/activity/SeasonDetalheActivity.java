package com.pi.efilm.view.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.efilm.R;
import com.pi.efilm.model.series.SeasonDetalhes.Episode;
import com.pi.efilm.model.series.SeasonDetalhes.SeasonDetalhes;
import com.pi.efilm.util.AppUtil;
import com.pi.efilm.view.adapter.EpisodiosAdapter;
import com.pi.efilm.viewmodel.SerieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.NUMBER;
import static com.pi.efilm.util.Constantes.PT_BR;
import static com.pi.efilm.util.Constantes.URL_IMAGEM;

public class SeasonDetalheActivity extends AppCompatActivity {
    private SerieViewModel viewModel;
    private RecyclerView recyclerViewEpisodio;
    private TextView nomeSeason, dataSeason, sinopseSeason;
    private ImageButton botaoHome;
    private ImageView imagemSeason;
    private EpisodiosAdapter adapter;
    private List<Episode> episodeList;
    private long idSerie;
    private long number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detalhe);
        initView();
        recuperaIdSeason();
        viewModel.getSeasonDetalhe(idSerie, number, API_KEY, PT_BR);
        viewModel.liveDataSeason.observe(this, (SeasonDetalhes seasonDetalhes) -> {
            setDetalhes(seasonDetalhes);
        });
        botaoHome.setOnClickListener(v -> AppUtil.botaoHome(this));
    }

    private void recuperaIdSeason() {
        Bundle bundle = getIntent().getExtras();
        idSerie = bundle.getLong(ID);
        number = bundle.getLong(NUMBER);
    }

    private void initView() {
        nomeSeason = findViewById(R.id.nameSeasonDetalhe);
        dataSeason = findViewById(R.id.estreiaSerieDetalhe);
        sinopseSeason = findViewById(R.id.sinopseSeason);
        imagemSeason = findViewById(R.id.imagemSeasonDetalhe);
        viewModel = ViewModelProviders.of(this).get(SerieViewModel.class);
        botaoHome = findViewById(R.id.botaoHomeSeason);
    }

    private void setDetalhes(SeasonDetalhes detalhes) {
        nomeSeason.setText(detalhes.getName());

        if (detalhes.getOverview().equals("")) {
            sinopseSeason.setText(R.string.sinopse_temporada_nao);
        } else {
            sinopseSeason.setText(detalhes.getOverview());
        }
        dataSeason.setText(detalhes.getAirDate());
        Picasso.get().load(URL_IMAGEM + detalhes.getPosterPath()).into(imagemSeason);
        initRecycler(detalhes);
    }


    private void initRecycler(SeasonDetalhes seasonDetalhes) {
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
