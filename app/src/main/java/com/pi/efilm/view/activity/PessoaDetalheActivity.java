package com.pi.efilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.pi.efilm.R;
import com.pi.efilm.model.pessoa.FilmesPessoa;
import com.pi.efilm.model.pessoa.PessoaDetalhe;
import com.pi.efilm.model.pessoa.Profile;
import com.pi.efilm.util.AppUtil;
import com.pi.efilm.view.adapter.FilmografiaAdapter;
import com.pi.efilm.view.adapter.FotosPessoaAdapter;
import com.pi.efilm.viewmodel.PessoaViewModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.API_KEY;
import static com.pi.efilm.util.Constantes.EN_US;
import static com.pi.efilm.util.Constantes.PT_BR;
import static com.pi.efilm.util.Constantes.URL_IMAGEM;

public class PessoaDetalheActivity extends AppCompatActivity {
    private ImageView imagemPessoa, imagemErro;
    private TextView nomePessoa, nascPessoa, localPessoa, biografia, departamento;
    private PessoaViewModel viewModel;
    private ImageButton botaoHome;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewFotos;
    private FilmografiaAdapter adapter;
    private FotosPessoaAdapter adapterFotos;
    private PessoaDetalhe pessoaDetalhe;
    private long idPessoa;
    private List<FilmesPessoa> filmesPessoaList = new ArrayList<>();
    private List<Profile> profileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_detalhe);
        initView();
        recuperaDados();
        botaoHome.setOnClickListener(v -> AppUtil.botaoHome(this));
    }

    private void recuperaDados() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            idPessoa = bundle.getLong(ID);
            viewModel.getPessoa(idPessoa, API_KEY, PT_BR);
            viewModel.liveDataPessoa.observe(this, pessoaDetalhe1 -> setDetalhes(pessoaDetalhe1));

            viewModel.getFilmografia(idPessoa, API_KEY, PT_BR);
            viewModel.liveDataFilmografia.observe(this, filmesPessoas -> adapter.atualizaLista(filmesPessoas));

            viewModel.getFoto(idPessoa, API_KEY);
            viewModel.liveDataFoto.observe(this, profiles -> adapterFotos.atualizaLista(profiles));
        } else {
            imagemErro.setImageResource(R.drawable.marvin);
        }
    }

    private void initView() {
        biografia = findViewById(R.id.biografiaPessoaDetalhe);
        imagemPessoa = findViewById(R.id.imagemPessoa);
        nomePessoa = findViewById(R.id.nomePessoaDetalhe);
        nascPessoa = findViewById(R.id.dataNascimentoDetalhe);
        localPessoa = findViewById(R.id.localPessoaDetalhe);
        imagemErro = findViewById(R.id.imagemErroPessoa);
        departamento = findViewById(R.id.departamentoPessoaDetalhe);
        botaoHome = findViewById(R.id.botaoHomePessoa);
        viewModel = ViewModelProviders.of(this).get(PessoaViewModel.class);
        adapter = new FilmografiaAdapter(filmesPessoaList);
        recyclerView = findViewById(R.id.recyclerFilmografia);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewFotos = findViewById(R.id.recyclerFotosPessoa);
        adapterFotos = new FotosPessoaAdapter(profileList);
        recyclerViewFotos.setAdapter(adapterFotos);
        recyclerViewFotos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerViewFotos.setLayoutManager(layoutManager2);
    }

    private void setDetalhes(PessoaDetalhe pessoaDetalhe) {
        nomePessoa.setText(pessoaDetalhe.getName());
        nascPessoa.setText(getString(R.string.data_nascimento) + pessoaDetalhe.getBirthday());
        localPessoa.setText(getString(R.string.local_nascimento) + pessoaDetalhe.getPlaceOfBirth());
        if (pessoaDetalhe.getBiography().equals("")) {
            viewModel.getPessoa(idPessoa, API_KEY, EN_US);
        } else {
            biografia.setText(pessoaDetalhe.getBiography());
        }
        departamento.setText(pessoaDetalhe.getKnownForDepartment());
        Picasso.get().load(URL_IMAGEM + pessoaDetalhe.getProfilePath()).into(imagemPessoa);
    }
}
