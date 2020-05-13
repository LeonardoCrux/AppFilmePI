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
import com.pi.efilm.model.series.ResultSeriesTop;
import com.pi.efilm.view.activity.SerieDetalheActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.URL_IMAGEM;

public class SearchSerieAdapter extends RecyclerView.Adapter<SearchSerieAdapter.ViewHolder> {
    private List<ResultSeriesTop> listaFilmes;

    public SearchSerieAdapter(List<ResultSeriesTop> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    @NonNull
    @Override
    public SearchSerieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.busca_lista, parent, false);
        return new SearchSerieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSerieAdapter.ViewHolder holder, int position) {
        ResultSeriesTop resultSerie = listaFilmes.get(position);
        holder.onBind(resultSerie);
    }

    public void atualizaLista(List<ResultSeriesTop> listaNova) {
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

        private void onBind(ResultSeriesTop resultSerie) {
            textView.setText(resultSerie.getName());
            Picasso.get().load(URL_IMAGEM + resultSerie.getPosterPath()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), SerieDetalheActivity.class);
            intent.putExtra(ID, listaFilmes.get(getAdapterPosition()).getId());
            v.getContext().startActivity(intent);
        }
    }
}

