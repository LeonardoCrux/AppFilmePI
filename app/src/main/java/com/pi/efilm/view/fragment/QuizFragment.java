package com.pi.efilm.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.efilm.R;
import com.pi.efilm.model.pessoa.pessoa.ResultPessoaPop;
import com.pi.efilm.view.adapter.PessoaPopularAdapter;
import com.pi.efilm.viewmodel.PessoaViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.PT_BR;

public class QuizFragment extends Fragment {
    private RecyclerView recyclerView;
    private PessoaViewModel viewModel;
    private PessoaPopularAdapter adapter;
    private List<ResultPessoaPop> pessoaPopList = new ArrayList<>();
    private int pagina = 1;

    public QuizFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        initView(view);
        viewModel.getPessoaPop(API_KEY, PT_BR, pagina);
        viewModel.liveDataPessoaTop.observe(getViewLifecycleOwner(), resultPessoaPops -> adapter.atualizaLista(resultPessoaPops));
        return view;
    }

    private void initView(View v) {
        adapter = new PessoaPopularAdapter(pessoaPopList);
        recyclerView = v.findViewById(R.id.recyclerPessoaTop);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(PessoaViewModel.class);
    }
}
