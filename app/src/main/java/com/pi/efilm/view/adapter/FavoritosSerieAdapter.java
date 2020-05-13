package com.pi.efilm.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pi.efilm.R;
import com.pi.efilm.model.series.ResultSeriesDetalhe;
import com.pi.efilm.util.Constantes;
import com.pi.efilm.view.interfaces.FavoritosListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritosSerieAdapter extends RecyclerView.Adapter<FavoritosSerieAdapter.ViewHolder> {
    private List<ResultSeriesDetalhe> detalhesList;
    private FavoritosListener listener;

    public FavoritosSerieAdapter(List<ResultSeriesDetalhe> detalhesList, FavoritosListener listener) {
        this.detalhesList = detalhesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritos_lista, parent, false);
        return new FavoritosSerieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritosSerieAdapter.ViewHolder holder, int position) {
        ResultSeriesDetalhe detalhes = detalhesList.get(position);
        holder.onBind(detalhes);
        holder.imageRemove.setOnClickListener(v -> listener.deleteFavoritoSerie(detalhes));
        holder.imageView.setOnClickListener(v -> listener.clickFavoritoSerie(detalhes));
    }

    public void atualizaLista(List<ResultSeriesDetalhe> novaLista) {
        this.detalhesList = novaLista;
        notifyDataSetChanged();
    }

    public void removeItem(ResultSeriesDetalhe result) {
        detalhesList.remove(result);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return detalhesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtData, txtNome;
        private ImageView imageView, imageRemove;

        public ViewHolder(@NonNull View v) {
            super(v);
            imageView = v.findViewById(R.id.imagemFav);
            imageRemove = v.findViewById(R.id.removeFav);
        }

        public void onBind(ResultSeriesDetalhe detalhes) {
            Picasso.get().load(Constantes.URL_IMAGEM + detalhes.getPosterPath()).into(imageView);

        }
    }
}

