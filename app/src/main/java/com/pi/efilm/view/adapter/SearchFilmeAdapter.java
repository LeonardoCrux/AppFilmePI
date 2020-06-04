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
import com.pi.efilm.model.filme.BuscaEBreve.ResultFilme;
import com.pi.efilm.view.activity.FilmeDetalheActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.URL_IMAGEM;

public class SearchFilmeAdapter extends RecyclerView.Adapter<SearchFilmeAdapter.ViewHolder> {
    private List<ResultFilme> listaFilmes;

    public SearchFilmeAdapter(List<ResultFilme> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.busca_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultFilme resultFilme = listaFilmes.get(position);
        holder.onBind(resultFilme);
    }

    public void atualizaLista(List<ResultFilme> listaNova) {
        if (listaFilmes.isEmpty()) {
            this.listaFilmes = listaNova;
        } else {
            this.listaFilmes.addAll(listaNova);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (listaFilmes.size() >= 4) {
            return 4;
        } else return listaFilmes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.searchImagem);
            textView = itemView.findViewById(R.id.searchTitulo);
        }

        private void onBind(ResultFilme resultFilme) {
            textView.setText(resultFilme.getTitle());
            Picasso.get().load(URL_IMAGEM + resultFilme.getPosterPath()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FilmeDetalheActivity.class);
            intent.putExtra(ID, listaFilmes.get(getAdapterPosition()).getId());
            v.getContext().startActivity(intent);
        }
    }
}
