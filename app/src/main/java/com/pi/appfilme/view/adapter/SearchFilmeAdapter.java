package com.pi.appfilme.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pi.appfilme.util.Constantes.URL_IMAGEM;

public class SearchFilmeAdapter extends RecyclerView.Adapter<SearchFilmeAdapter.ViewHolder> {
    private List<ResultFilme> listaFilmes;

    public SearchFilmeAdapter(List<ResultFilme> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filme_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultFilme resultFilme = listaFilmes.get(position);
        holder.onBind(resultFilme);

    }

    public void atualizaLista(List<ResultFilme> listaNova){
        this.listaFilmes = listaNova;
    }


    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.searchImagem);
            textView = itemView.findViewById(R.id.searchTitulo);
        }

        private void onBind(ResultFilme resultFilme){
            textView.setText(resultFilme.getTitle());
            Picasso.get().load(URL_IMAGEM+resultFilme.getPosterPath());
        }
    }
}
