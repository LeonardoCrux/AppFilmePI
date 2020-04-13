package com.pi.appfilme.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pi.appfilme.R;
import com.pi.appfilme.adapter.SeriesAdapter;
import com.pi.appfilme.model.series.ResultSeries;
import com.pi.appfilme.viewmodel.SerieViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Region.BR;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private SeriesAdapter adapter;
    private SerieViewModel viewModel;
    private List<ResultSeries> listSerie = new ArrayList<>();
    public SeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_series, container, false);
        initViews(view);
        viewModel.getPopularSeries(API_KEY, BR, 1);
        viewModel.liveData.observe(getViewLifecycleOwner(), seriesPopulares -> {
            adapter.setResult(seriesPopulares);
        });
        return view;
    }

    public void initViews(View view){
        recyclerView = view.findViewById(R.id.recyclerSeriePopular);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SeriesAdapter(listSerie);
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(SerieViewModel.class);

    }
}
