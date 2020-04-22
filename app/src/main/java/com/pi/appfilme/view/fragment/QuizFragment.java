package com.pi.appfilme.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pi.appfilme.R;
import com.pi.appfilme.model.pessoa.pessoa.ResultPessoaPop;
import com.pi.appfilme.model.series.ResultSeriePopular;
import com.pi.appfilme.view.adapter.PessoaPopularAdapter;
import com.pi.appfilme.viewmodel.PessoaViewModel;
import com.pi.appfilme.viewmodel.SerieViewModel;
import java.util.ArrayList;
import java.util.List;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Language.PT_BR;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {
    private RecyclerView recyclerView;
    private PessoaViewModel viewModel;
    private PessoaPopularAdapter adapter;
    private List<ResultPessoaPop> pessoaPopList = new ArrayList<>();

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        initView(view);
        viewModel.getPessoaPop(API_KEY, PT_BR, 1);
        viewModel.liveDataPessoaTop.observe(getViewLifecycleOwner(), resultPessoaPops -> adapter.atualizaLista(resultPessoaPops));
        return view;
    }

    public void initView(View v){
        adapter = new PessoaPopularAdapter(pessoaPopList);
        recyclerView = v.findViewById(R.id.recyclerPessoaTop);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(PessoaViewModel.class);
    }
}