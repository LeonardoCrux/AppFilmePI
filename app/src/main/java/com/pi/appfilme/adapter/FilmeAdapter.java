package com.pi.appfilme.adapter;
import android.util.Log;
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

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.ViewHolder> {
    private List<ResultFilme> listPlaying;

    public FilmeAdapter(List<ResultFilme> listPlaying) {
        this.listPlaying = listPlaying;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filme_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultFilme resultFilme = listPlaying.get(position);
        holder.onBind(resultFilme);
    }

    public void atualizaListaPlaying(List<ResultFilme> novaLista) {
        if (this.listPlaying.isEmpty()) {
            this.listPlaying = novaLista;
        } else {
            this.listPlaying.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listPlaying.size() - 17;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textTitulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagemCartazRecycler);
            textTitulo = itemView.findViewById(R.id.textRecyclerCartaz);
        }

        public void onBind (ResultFilme resultFilme){
            textTitulo.setText(resultFilme.getTitle());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ resultFilme.getPosterPath()).into(imageView);
        }
    }
}
