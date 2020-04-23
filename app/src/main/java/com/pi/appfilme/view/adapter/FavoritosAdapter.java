package com.pi.appfilme.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.view.activity.FavoritosActivity;
import com.pi.appfilme.view.interfaces.FavoritosListener;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder> {
    private List<Detalhes> detalhesList;

    public FavoritosAdapter(List<Detalhes> detalhesList) {
        this.detalhesList = detalhesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filme_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detalhes detalhes = detalhesList.get(position);
        holder.onBind(detalhes);
    }

    public void atualizaLista(List<Detalhes> novaLista){
        if (this.detalhesList.isEmpty()){
            this.detalhesList = novaLista;
        }else {
            this.detalhesList.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return detalhesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtData, txtNome;
        private ImageView imageView;

        public ViewHolder(@NonNull View v) {
            super(v);
            txtNome = v.findViewById(R.id.textRecyclerCartaz);
            txtData = v.findViewById(R.id.cartazData);

        }

        public void onBind(Detalhes detalhes){
            txtData.setText(detalhes.getReleaseDate());
            txtNome.setText(detalhes.getTitle());
        }
    }
}
