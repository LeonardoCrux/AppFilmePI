package com.pi.appfilme.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.series.ResultSeriePopular;
import com.pi.appfilme.model.series.ResultSeriesTop;
import com.pi.appfilme.view.activity.ListaExpandidaActivity;
import com.pi.appfilme.view.adapter.SeriesTopAdapter;
import com.pi.appfilme.viewmodel.SerieViewModel;
import java.util.ArrayList;
import java.util.List;
import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Language.PT_BR;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewPopular;
    private SerieViewModel viewModel;
    private TextView textPopulares;
    private SeriesTopAdapter adapter;
    private List<ResultSeriesTop> listaSeriesTops = new ArrayList<>();
    private List<ResultSeriePopular> listaSeriePopular =  new ArrayList<>();


    public SeriesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_series, container, false);
        initViews(view);
        viewModel.getTopSeries(API_KEY, PT_BR, 1);
        viewModel.liveDataSeriesTop.observe(getViewLifecycleOwner(), resultSeriesTops -> adapter.atualizaLista(resultSeriesTops));
        textPopulares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListaExpandidaActivity.class);
                intent.putExtra("Click", "Populares");
                startActivity(intent);
            }
        });
        return view;
    }

    public void initViews(View view){
        recyclerView = view.findViewById(R.id.recyclerSeriesTop);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SeriesTopAdapter(listaSeriesTops);
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(SerieViewModel.class);
        textPopulares = view.findViewById(R.id.textViewSeriePopular);
    }
}
