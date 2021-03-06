package com.pi.efilm.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.efilm.R;
import com.pi.efilm.model.filme.BuscaEBreve.ResultFilme;
import com.pi.efilm.view.activity.ListaExpandidaActivity;
import com.pi.efilm.view.adapter.FilmeAdapter;
import com.pi.efilm.view.adapter.FilmeAdapterTop;
import com.pi.efilm.viewmodel.FilmeViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.BR;
import static com.pi.efilm.util.Constantes.CLICK;
import static com.pi.efilm.util.Constantes.FILME_POPULAR;
import static com.pi.efilm.util.Constantes.PT_BR;
import static com.pi.efilm.util.Constantes.TOP;

public class FilmesFragment extends Fragment {
    private TextView textCartaz;
    private TextView textTop;
    private RecyclerView recyclerCartaz;
    private RecyclerView recyclerTop;
    private FilmeAdapter adapter;
    private FilmeAdapterTop adapterTop;
    private List<ResultFilme> listPlaying = new ArrayList<>();
    private List<ResultFilme> listTop = new ArrayList<>();
    private ProgressBar progressBar3, progressBar;
    private FilmeViewModel viewModel;
    private Animation animFadein;
    private int pagina = 1;

    public FilmesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filmes, container, false);
        initViews(view);
        viewModel.getFilmePopular(API_KEY, PT_BR, BR,1 );
        viewModel.liveData.observe(getViewLifecycleOwner(), (List<ResultFilme> resultFilmes) -> { adapter.atualizaListaPlaying(resultFilmes);
        });
        viewModel.liveDataLoading.observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean) {
                progressBar3.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                progressBar3.setVisibility(View.INVISIBLE);
            }
        });
        viewModel.getTop(API_KEY, PT_BR, "US", pagina);
        viewModel.liveDataTop.observe(getViewLifecycleOwner(), resultFilmes -> {adapterTop.atualizaListaTop(resultFilmes);});

        textCartaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCartaz.startAnimation(animFadein);
                Intent intent = new Intent(getContext(), ListaExpandidaActivity.class);
                intent.putExtra(CLICK , FILME_POPULAR);
                startActivity(intent);
            }
        });

        textTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textTop.startAnimation(animFadein);
                Intent intent = new Intent(getContext(), ListaExpandidaActivity.class);
                intent.putExtra(CLICK, TOP);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initViews(View view){
        recyclerCartaz = view.findViewById(R.id.recyclerCartaz);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerCartaz.setLayoutManager(layoutManager);
        adapter = new FilmeAdapter(listPlaying);
        recyclerCartaz.setAdapter(adapter);
        recyclerCartaz.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerTop = view.findViewById(R.id.recyclerTop);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerTop.setLayoutManager(layoutManager2);
        adapterTop = new FilmeAdapterTop(listTop);
        recyclerTop.setAdapter(adapterTop);
        recyclerTop.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        progressBar3 = view.findViewById(R.id.progressBar3);
        progressBar = view.findViewById(R.id.progressBar);
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
        textTop = (TextView) view.findViewById(R.id.textTop);
        textCartaz = view.findViewById(R.id.textFilmePopular);

        animFadein = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.fragment_fade_enter);
    }

}
