package com.pi.appfilme.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pi.appfilme.R;
import com.pi.appfilme.adapter.TodosFilmesAdapter;
import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.pi.appfilme.viewmodel.FilmeViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.appfilme.util.Constantes.Hash.API_KEY;
import static com.pi.appfilme.util.Constantes.Language.PT_BR;
import static com.pi.appfilme.util.Constantes.Region.BR;

public class TodosActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private FilmeViewModel viewModel;
    private TodosFilmesAdapter adapter;
    private ProgressBar progressBar;
    private List<ResultFilme> listaFilmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        initViews();
        Bundle bundle = getIntent().getExtras();
        String click = bundle.getString("Click");
        if(click.equals("Cartaz")){
            viewModel.getPlaying(API_KEY, PT_BR, BR, 1);
            viewModel.liveData.observe(this, resultFilmes -> adapter.atualizaLista(resultFilmes));
        } else if(click.equals("Top")){
            viewModel.getTop(API_KEY, PT_BR, BR, 1);
            viewModel.liveDataTop.observe(this, resultFilmes -> adapter.atualizaLista(resultFilmes)); }
    }

    public void initViews(){
        recyclerView = findViewById(R.id.recyclerTodos);
        adapter = new TodosFilmesAdapter(listaFilmes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
    }
}
