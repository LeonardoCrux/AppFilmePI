package com.pi.efilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.efilm.R;
import com.pi.efilm.model.pessoa.FilmesPessoa;
import com.pi.efilm.model.pessoa.PessoaDetalhe;
import com.pi.efilm.model.pessoa.Profile;
import com.pi.efilm.view.adapter.FilmografiaAdapter;
import com.pi.efilm.view.adapter.FotosPessoaAdapter;
import com.pi.efilm.viewmodel.PessoaViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;
import static com.pi.efilm.util.Constantes.Hash.API_KEY;
import static com.pi.efilm.util.Constantes.Language.PT_BR;

public class PessoaDetalheActivity extends AppCompatActivity {
    private ImageView imagemPessoa, imagemErro;
    private TextView nomePessoa, nascPessoa, localPessoa, biografia, departamento;
    private PessoaViewModel viewModel;
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
    }

    public void recuperaDados(){
        if(getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            idPessoa = bundle.getLong(ID);
            viewModel.getPessoa( idPessoa , API_KEY, PT_BR);
            viewModel.liveDataPessoa.observe(this, pessoaDetalhe1 -> setDetalhes(pessoaDetalhe1));

            viewModel.getFilmografia(idPessoa , API_KEY, PT_BR);
            viewModel.liveDataFilmografia.observe(this, filmesPessoas -> adapter.atualizaLista(filmesPessoas));

            viewModel.getFoto(idPessoa, API_KEY);
            viewModel.liveDataFoto.observe(this, profiles -> adapterFotos.atualizaLista(profiles));
        } else {
            imagemErro.setImageResource(R.drawable.marvin);
            localPessoa.setText("Error");
        }
    }

    public void initView(){
        biografia = findViewById(R.id.biografiaPessoaDetalhe);
        imagemPessoa = findViewById(R.id.imagemPessoa);
        nomePessoa = findViewById(R.id.nomePessoaDetalhe);
        nascPessoa = findViewById(R.id.dataNascimentoDetalhe);
        localPessoa = findViewById(R.id.localPessoaDetalhe);
        imagemErro = findViewById(R.id.imagemErroPessoa);
        departamento = findViewById(R.id.departamentoPessoaDetalhe);
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

    public void setDetalhes(PessoaDetalhe pessoaDetalhe){
        nomePessoa.setText(pessoaDetalhe.getName());
        nascPessoa.setText("Data de Nascimento: " + pessoaDetalhe.getBirthday());
        localPessoa.setText("Local de nascimento: " + pessoaDetalhe.getPlaceOfBirth());
        if(pessoaDetalhe.getBiography().equals("")){
            biografia.setText("Biografia indisponível");
        } else {
            biografia.setText(pessoaDetalhe.getBiography());
        }
        departamento.setText(pessoaDetalhe.getKnownForDepartment());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+pessoaDetalhe.getProfilePath()).into(imagemPessoa);
    }
}
