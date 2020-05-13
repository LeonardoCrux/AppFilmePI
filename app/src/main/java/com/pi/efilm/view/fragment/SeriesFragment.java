package com.pi.efilm.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.pi.efilm.R;
import com.pi.efilm.model.series.ResultSeriesTop;
import com.pi.efilm.view.activity.ListaExpandidaActivity;
import com.pi.efilm.view.adapter.SeriePopularAdapter;
import com.pi.efilm.view.adapter.SeriesTopAdapter;
import com.pi.efilm.viewmodel.SerieViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.CLICK;
import static com.pi.efilm.util.Constantes.PT_BR;
import static com.pi.efilm.util.Constantes.SERIES_POPULARES;
import static com.pi.efilm.util.Constantes.SERIES_TOP;

public class SeriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewPopular;
    private SerieViewModel viewModel;
    private TextView textPopulares, textSerieTop;
    private SeriesTopAdapter adapter;
    private SeriePopularAdapter seriePopularAdapter;
    private List<ResultSeriesTop> listaSeriesTops = new ArrayList<>();
    private List<ResultSeriesTop> listaSeriePopular = new ArrayList<>();
    private Animation animFadein;
    private int pagina = 1;


    public SeriesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series, container, false);
        initViews(view);
        viewModel.getTopSeries(API_KEY, PT_BR, pagina);
        viewModel.liveDataSeriesTop.observe(getViewLifecycleOwner(), resultSeriesTops -> adapter.atualizaLista(resultSeriesTops));

        viewModel.getSeriesPopular(API_KEY, PT_BR, pagina);
        viewModel.liveDataPopular.observe(getViewLifecycleOwner(), resultSeriesTops -> seriePopularAdapter.atualizaLista(resultSeriesTops));

        textSerieTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSerieTop.startAnimation(animFadein);
                Intent intent = new Intent(getContext(), ListaExpandidaActivity.class);
                intent.putExtra(CLICK, SERIES_TOP);
                startActivity(intent);
            }
        });

        textPopulares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPopulares.startAnimation(animFadein);
                Intent intent = new Intent(getContext(), ListaExpandidaActivity.class);
                intent.putExtra(CLICK, SERIES_POPULARES);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerSeriesTop);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SeriesTopAdapter(listaSeriesTops);
        recyclerView.setAdapter(adapter);
        textSerieTop = view.findViewById(R.id.textSerieTop);
        viewModel = ViewModelProviders.of(this).get(SerieViewModel.class);
        textPopulares = view.findViewById(R.id.textViewSeriePopular);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerViewPopular = view.findViewById(R.id.recyclerSeriePopular);
        seriePopularAdapter = new SeriePopularAdapter(listaSeriePopular);
        recyclerViewPopular.setLayoutManager(layoutManager2);
        recyclerViewPopular.setAdapter(seriePopularAdapter);
        animFadein = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.fragment_fade_enter);
    }
}
