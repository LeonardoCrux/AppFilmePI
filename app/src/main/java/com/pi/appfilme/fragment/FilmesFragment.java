package com.pi.appfilme.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pi.appfilme.R;
import com.pi.appfilme.adapter.FilmeAdapter;
import com.pi.appfilme.adapter.FilmeAdapterTop;
import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.pi.appfilme.view.MainActivity;
import com.pi.appfilme.viewmodel.FilmeViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Language.PT_BR;
import static com.pi.appfilme.util.Constantes.Region.BR;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmesFragment extends Fragment {
    private RecyclerView recyclerCartaz;
    private RecyclerView recyclerTop;
    private FilmeAdapter adapter;
    private FilmeAdapterTop adapterTop;
    private List<ResultFilme> listPlaying = new ArrayList<>();
    private List<ResultFilme> listTop = new ArrayList<>();
    private ProgressBar progressBar;
    private FilmeViewModel viewModel;

    public FilmesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filmes, container, false);
        initViews(view);
        viewModel.getPlaying(API_KEY, PT_BR, BR,1 );
        viewModel.liveData.observe(getViewLifecycleOwner(), (List<ResultFilme> resultFilmes) -> {adapter.atualizaListaPlaying(resultFilmes);
        });
        viewModel.liveDataLoading.observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else progressBar.setVisibility(View.INVISIBLE);
        });
        viewModel.getTop(API_KEY, PT_BR, BR, 1);
        viewModel.liveDataTop.observe(getViewLifecycleOwner(), resultFilmes -> {adapterTop.atualizaListaTop(resultFilmes);});
        return view;
    }

    public void initViews(View view){
        recyclerCartaz = view.findViewById(R.id.recyclerCartaz);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerCartaz.setLayoutManager(layoutManager);
        adapter = new FilmeAdapter(listPlaying);
        recyclerCartaz.setAdapter(adapter);

        recyclerTop = view.findViewById(R.id.recyclerTop);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerTop.setLayoutManager(layoutManager2);
        adapterTop = new FilmeAdapterTop(listTop);
        recyclerTop.setAdapter(adapterTop);
        progressBar = view.findViewById(R.id.progressBar3);
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
    }

}
