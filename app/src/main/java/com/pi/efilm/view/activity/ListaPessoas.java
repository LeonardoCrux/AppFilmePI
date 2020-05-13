package com.pi.efilm.view.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.pi.efilm.R;
import com.pi.efilm.model.pessoa.pessoa.ResultPessoaPop;
import com.pi.efilm.view.adapter.PessoaPopularAdapter;
import com.pi.efilm.viewmodel.BuscaViewModel;
import java.util.ArrayList;
import java.util.List;
import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.BR;
import static com.pi.efilm.util.Constantes.CLICK;
import static com.pi.efilm.util.Constantes.PT_BR;

public class ListaPessoas extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PessoaPopularAdapter adapter;
    private BuscaViewModel viewModel;
    private List<ResultPessoaPop> pessoaPopList = new ArrayList<>();
    private String query;
    private int pagina = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);
        initView();
        recuperaDados();
        setScrollView();
        viewModel.getResultPessoa(API_KEY, PT_BR, query, pagina, BR);
        viewModel.liveDataPessoa.observe(this, resultPessoaPops -> adapter.atualizaLista(resultPessoaPops));
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerPessoasLista);
        adapter = new PessoaPopularAdapter(pessoaPopList);
        viewModel = ViewModelProviders.of(this).get(BuscaViewModel.class);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void recuperaDados(){
        Bundle bundle = getIntent().getExtras();
        query = bundle.getString(CLICK);
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
                    viewModel.getResultPessoa(API_KEY, PT_BR, query, pagina, BR);
                }
            }
        });
    }
}
