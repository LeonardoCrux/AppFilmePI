package com.pi.efilm.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.pi.efilm.R;
import com.pi.efilm.model.series.ResultSeriesTop;
import com.pi.efilm.view.adapter.ListaSeriesAdapter;
import com.pi.efilm.view.adapter.TodosFilmesAdapter;
import com.pi.efilm.model.filme.BuscaEBreve.ResultFilme;
import com.pi.efilm.viewmodel.FilmeViewModel;
import com.pi.efilm.viewmodel.SerieViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.pi.efilm.util.Constantes.CARTAZ;
import static com.pi.efilm.util.Constantes.CLICK;
import static com.pi.efilm.util.Constantes.Hash.API_KEY;
import static com.pi.efilm.util.Constantes.Language.PT_BR;
import static com.pi.efilm.util.Constantes.Region.BR;
import static com.pi.efilm.util.Constantes.SERIES_POPULARES;
import static com.pi.efilm.util.Constantes.SERIES_TOP;
import static com.pi.efilm.util.Constantes.TOP;

public class ListaExpandidaActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private FilmeViewModel viewModel;
    private TodosFilmesAdapter adapter;
    private ListaSeriesAdapter adapterSeries;
    private ProgressBar progressBar;
    private String selecionado;
    private SerieViewModel serieViewModel;
    private List<ResultFilme> listaFilmes = new ArrayList<>();
    private List<ResultSeriesTop> listaSeries = new ArrayList<>();
    private int pagina = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_expandida);
        initViews();
        Bundle bundle = getIntent().getExtras();
        selecionado = bundle.getString(CLICK);

        if(selecionado.equals(CARTAZ)){
            setScrollView();
            recyclerView.setAdapter(adapter);
            viewModel.getPlaying(API_KEY, PT_BR, BR, pagina);
            viewModel.liveData.observe(this, resultFilmes -> adapter.atualizaLista(resultFilmes));

        } else if(selecionado.equals(TOP)){
            setScrollView();
            recyclerView.setAdapter(adapter);
            viewModel.getTop(API_KEY, PT_BR, "US", pagina);
            viewModel.liveDataTop.observe(this, resultFilmes -> adapter.atualizaLista(resultFilmes));
        } else if(selecionado.equals(SERIES_TOP)){
            setScrollView();
            adapterSeries = new ListaSeriesAdapter(listaSeries);
            recyclerView.setAdapter(adapterSeries);
            serieViewModel.getTopSeries(API_KEY, PT_BR, pagina);
            serieViewModel.liveDataSeriesTop.observe(this, resultSeriesTops -> adapterSeries.atualizaLista(resultSeriesTops));
        } else if(selecionado.equals(SERIES_POPULARES)){
            setScrollView();
            adapterSeries = new ListaSeriesAdapter(listaSeries);
            recyclerView.setAdapter(adapterSeries);
            serieViewModel.getSeriesPopular(API_KEY, PT_BR, pagina);
            serieViewModel.liveDataPopular.observe(this, resultSeriesTops -> adapterSeries.atualizaLista(resultSeriesTops));
        }
    }


    public void initViews(){
        recyclerView = findViewById(R.id.recyclerTodos);
        adapter = new TodosFilmesAdapter(listaFilmes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(FilmeViewModel.class);
        serieViewModel = ViewModelProviders.of(this).get(SerieViewModel.class);
        recyclerView.setHasFixedSize(true);
        recyclerView.scheduleLayoutAnimation();
    }

    private void setScrollView(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = layoutManager.getItemCount();
                int ultimoVisivel = layoutManager.findLastCompletelyVisibleItemPosition();
                boolean ultimoItem = ultimoVisivel + 1 >= itemCount;

                if (itemCount > 0 && ultimoItem){
                    pagina++;
                    if(selecionado.equals(CARTAZ)){
                        viewModel.getPlaying(API_KEY, PT_BR, BR, pagina);
                    } else if(selecionado.equals(TOP)){
                        viewModel.getTop(API_KEY, PT_BR, "US", pagina);
                    } else if(selecionado.equals(SERIES_TOP)){
                        serieViewModel.getTopSeries(API_KEY, PT_BR, pagina);
                    } else if(selecionado.equals(SERIES_POPULARES)){
                        serieViewModel.getSeriesPopular(API_KEY, PT_BR, pagina);
                    }
                }
            }
        });
    }


}
