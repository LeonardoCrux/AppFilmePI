package com.pi.efilm.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.efilm.R;
import com.pi.efilm.model.pessoa.FilmesPessoa;
import com.pi.efilm.util.Constantes;
import com.pi.efilm.view.activity.FilmeDetalheActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.os.Build.ID;

public class FilmografiaAdapter extends RecyclerView.Adapter<FilmografiaAdapter.ViewHolder> {
    private List<FilmesPessoa> filmesPessoaList;

    public FilmografiaAdapter(List<FilmesPessoa> filmesPessoaList) {
        this.filmesPessoaList = filmesPessoaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filmografia_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmesPessoa filmesPessoa = filmesPessoaList.get(position);
        holder.onBind(filmesPessoa);

    }

    @Override
    public int getItemCount() {
        return filmesPessoaList.size();
    }

    public void atualizaLista(List<FilmesPessoa> novaLista){
        if(filmesPessoaList.isEmpty()){
            this.filmesPessoaList = novaLista;
        }else {
            this.filmesPessoaList.addAll(novaLista);}
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView estreia, personagem, titulo;
        private ImageView imagem;

        public ViewHolder(@NonNull View v) {
            super(v);
            estreia = v.findViewById(R.id.estreiaFilmografia);
            personagem = v.findViewById(R.id.personagemFilmografia);
            titulo = v.findViewById(R.id.tituloFilmografia);
            imagem = v.findViewById(R.id.imagemFilmografia);
            v.setOnClickListener(this);
        }

        public void onBind(FilmesPessoa filmesPessoa){
            estreia.setText(filmesPessoa.getReleaseDate());
            personagem.setText(filmesPessoa.getCharacter());
            titulo.setText(filmesPessoa.getTitle());
            Picasso.get().load(Constantes.URL_IMAGEM+ filmesPessoa.getPosterPath()).into(imagem);
        }


        @Override
        public void onClick(View view) {
            Intent intent =  new Intent(view.getContext(), FilmeDetalheActivity.class);
            intent.putExtra(ID , filmesPessoaList.get(getAdapterPosition()).getId());
            view.getContext().startActivity(intent);
        }
    }
}
