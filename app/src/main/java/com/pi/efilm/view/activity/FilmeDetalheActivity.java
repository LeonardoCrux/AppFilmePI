package com.pi.efilm.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.pi.efilm.R;
import com.pi.efilm.model.filme.BuscaEBreve.ResultFilme;
import com.pi.efilm.model.filme.detalhes.Genre;
import com.pi.efilm.util.AppUtil;
import com.pi.efilm.util.Constantes;
import com.pi.efilm.view.adapter.ElencoAdapter;
import com.pi.efilm.model.filme.creditos.Cast;
import com.pi.efilm.model.filme.detalhes.Detalhes;
import com.pi.efilm.view.adapter.FilmeAdapterTop;
import com.pi.efilm.viewmodel.PessoaViewModel;
import com.pi.efilm.viewmodel.FilmeViewModel;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.PT_BR;

public class FilmeDetalheActivity extends AppCompatActivity {
    private ImageView imagemFilme, imagemFavorito, imagemShare;
    private TextView tituloFilme, duracao, originalTitle, sinopse, orcamento, bilheteria, data, genero;
    private FilmeViewModel viewModelFilme;
    private ImageButton botaoHome;
    private Detalhes filme;
    private PessoaViewModel viewModelElenco;
    private long idFilme;
    private ElencoAdapter adapter;
    private FilmeAdapterTop adapterSimilar, adapteRecomendado;
    private List<ResultFilme> resultFilmeList = new ArrayList<>();
    private List<Cast> castList = new ArrayList<>();
    private RecyclerView recyclerView, recyclerFilmeSimilar, recyclerFilmeRecomendado;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhe);
        initView();
        recuperaIdFilme();

        if (AppUtil.verificaConexaoComInternet(this)) {
            requisicaoApi();
            viewModelFilme.favoritoAdd.observe(this, result -> {
                if (result != null) {
                    Snackbar snackbar = Snackbar.make(recyclerView, result.getTitle() + getString(R.string.add_favorito), Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(Color.parseColor("#4CAF50"));
                    snackbar.show();
                }
            });

            viewModelFilme.resultLiveDataError.observe(this, error -> {
                Snackbar snackbar = Snackbar.make(imagemFavorito, error.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(Color.RED);
                snackbar.show();
            });

            adicionarFavorito();

        } else {
            Snackbar snackbar = Snackbar.make(recyclerView, R.string.carregar_informação_off, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            adicionarFavorito();
            viewModelFilme.getFilmeDetalheDB(this, idFilme);
            viewModelFilme.liveDataDetalhes.observe(this, detalhes -> {
                setDetalhes(detalhes);
                viewModelFilme.corBotaoFavoritos(detalhes, this, imagemFavorito);
                progressBar.setVisibility(View.INVISIBLE);
                filme = detalhes;
            });
        }
        botaoHome.setOnClickListener(v -> AppUtil.botaoHome(this));
        compartilhar();
    }

    private void requisicaoApi() {
        viewModelFilme.getFilmeDetalhe(idFilme, API_KEY, PT_BR);
        viewModelFilme.liveDataDetalhes.observe(this, detalhes -> {
            setDetalhes(detalhes);
            viewModelFilme.corBotaoFavoritos(detalhes, this, imagemFavorito);
            filme = detalhes;
        });
        viewModelFilme.liveDataLoading.observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        viewModelElenco.getCast(idFilme, API_KEY);
        viewModelElenco.liveDataCast.observe(this, casts -> adapter.atualizaLista(casts));
        viewModelFilme.getFilmeSimilar(idFilme, API_KEY, PT_BR, 1);
        viewModelFilme.liveDataSimilar.observe(this, resultFilmes -> adapterSimilar.atualizaListaTop(resultFilmes));
        viewModelFilme.getFilmeRecomendado(idFilme, API_KEY, PT_BR, 1);
        viewModelFilme.liveDataRecomendado.observe(this, resultFilmes -> adapteRecomendado.atualizaListaTop(resultFilmes));
    }

    private void adicionarFavorito() {
        imagemFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.verificaConexaoComInternet(getApplicationContext())) {
                    if (AppUtil.verificarLogado()) {
                        viewModelFilme.salvarFirebase(filme, getApplicationContext(), imagemFavorito);
                    } else
                        Snackbar.make(imagemFavorito, R.string.adicionar_logado, Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(imagemFavorito, R.string.adicionar_conexao, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        imagemFavorito = findViewById(R.id.imageAddFavorito);
        imagemFilme = findViewById(R.id.imagemFilmeDetalhe);
        tituloFilme = findViewById(R.id.tituloFilmeDetalhe);
        duracao = findViewById(R.id.duracaoFilmeDetalhe);
        originalTitle = findViewById(R.id.originalTitlefilmeDetalhe);
        sinopse = findViewById(R.id.sinopseFilmeDetalhe);
        progressBar = findViewById(R.id.progressBarSeriesDetalhe);
        orcamento = findViewById(R.id.orcamentoDetalhe);
        genero = findViewById(R.id.generoDetalhe);
        bilheteria = findViewById(R.id.bilheteriaDetalhe);
        data = findViewById(R.id.estreiaFilmeDetalhe);
        viewModelFilme = ViewModelProviders.of(this).get(FilmeViewModel.class);
        viewModelElenco = ViewModelProviders.of(this).get(PessoaViewModel.class);
        recyclerView = findViewById(R.id.recyclerElenco);
        recyclerFilmeSimilar = findViewById(R.id.recyclerFilmeSimilar);
        recyclerFilmeRecomendado = findViewById(R.id.recyclerFilmeRecomendado);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerFilmeSimilar.setLayoutManager(layoutManager2);
        recyclerFilmeRecomendado.setLayoutManager(layoutManager3);
        adapter = new ElencoAdapter(castList);
        adapterSimilar =  new FilmeAdapterTop(resultFilmeList);
        adapteRecomendado = new FilmeAdapterTop(resultFilmeList);
        recyclerFilmeRecomendado.setAdapter(adapteRecomendado);
        recyclerFilmeSimilar.setAdapter(adapterSimilar);
        recyclerView.setAdapter(adapter);
        botaoHome =  findViewById(R.id.botaoHomeFilme);
        imagemShare = findViewById(R.id.shareFilme);
    }

    private void recuperaIdFilme() {
        Bundle bundle = getIntent().getExtras();
        idFilme = bundle.getLong(ID);
    }

    private void setDetalhes(Detalhes detalhes) {
        try {
            DecimalFormat doisDigitos = new DecimalFormat("###,##0.00");
            Picasso.get().load(Constantes.URL_IMAGEM + detalhes.getPosterPath()).into(imagemFilme);
            tituloFilme.setText(detalhes.getTitle());
            duracao.setText(detalhes.getRuntime().toString() + getString(R.string.min));
            originalTitle.setText(detalhes.getOriginalTitle());
            sinopse.setText(detalhes.getOverview());
            List<Genre> genres = detalhes.getGenres();
            String generosString = "";
            for (Genre g : genres) {
                generosString += g.getName() + ", ";
            }
            genero.setText(generosString.substring(0, generosString.length() - 2));
            if (detalhes.getBudget() == 0) {
                orcamento.setVisibility(View.INVISIBLE);
            } else {
                String orcamentoFormatado = doisDigitos.format(detalhes.getBudget());
                orcamento.setText(getString(R.string.orcamento) + orcamentoFormatado);
            }
            if (detalhes.getRevenue() == 0) {
                bilheteria.setVisibility(View.INVISIBLE);
            } else {
                String bilheteriaFormatado = doisDigitos.format(detalhes.getRevenue());
                bilheteria.setText(getString(R.string.bilheteria) + bilheteriaFormatado);
            }
            data.setText(detalhes.getReleaseDate());
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.localizar_info_filme, Toast.LENGTH_LONG);
        } catch (StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    private void compartilhar(){
        imagemShare.setOnClickListener(v -> {
            String stringCompartilhar = "Recomendo o filme "+ filme.getTitle() + "\nhttps://www.themoviedb.org/movie/" + idFilme;
            AppUtil.compartilhar(stringCompartilhar, progressBar, this);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }
}

