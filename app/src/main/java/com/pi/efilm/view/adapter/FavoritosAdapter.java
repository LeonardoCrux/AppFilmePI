package com.pi.efilm.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.efilm.R;
import com.pi.efilm.model.filme.detalhes.Detalhes;
import com.pi.efilm.util.Constantes;
import com.pi.efilm.view.interfaces.FavoritosListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder> {
    private List<Detalhes> detalhesList;
    private FavoritosListener listener;

    public FavoritosAdapter(List<Detalhes> detalhesList, FavoritosListener listener) {
        this.detalhesList = detalhesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritos_lista, parent, false);
        return new ViewHolder(view);
    }

    public void removeItem(Detalhes result) {
        detalhesList.remove(result);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detalhes detalhes = detalhesList.get(position);
        holder.onBind(detalhes);
        holder.imageRemove.setOnClickListener(v -> listener.deleteFavorito(detalhes));
        holder.imageView.setOnClickListener(v -> listener.clickFavorito(detalhes));
    }

    public void atualizaLista(List<Detalhes> novaLista) {
        this.detalhesList = novaLista;
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

        public void onBind(Detalhes detalhes) {
            Picasso.get().load(Constantes.URL_IMAGEM + detalhes.getPosterPath()).into(imageView);

        }
    }
}
