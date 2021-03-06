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
import com.pi.efilm.util.Constantes;
import com.pi.efilm.view.activity.FilmeDetalheActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.pi.efilm.util.Constantes.ID;

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
        return listPlaying.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textTitulo, dataFilme;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagemCartazRecycler);
            textTitulo = itemView.findViewById(R.id.textRecyclerCartaz);
            dataFilme = itemView.findViewById(R.id.cartazData);
            itemView.setOnClickListener(this);
        }

        public void onBind(ResultFilme resultFilme) {
            textTitulo.setText(resultFilme.getTitle());
            dataFilme.setText(resultFilme.getReleaseDate());
            Picasso.get().load(Constantes.URL_IMAGEM + resultFilme.getPosterPath()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FilmeDetalheActivity.class);
            long idFilme = listPlaying.get(getAdapterPosition()).getId();
            intent.putExtra(ID, idFilme);
            v.getContext().startActivity(intent);
        }
    }
}
