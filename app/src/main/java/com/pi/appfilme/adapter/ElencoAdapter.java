package com.pi.appfilme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.creditos.Cast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ElencoAdapter extends RecyclerView.Adapter<ElencoAdapter.ViewHolder>{
    private List<Cast> listaCast;

    public ElencoAdapter(List<Cast> listaCast) {
        this.listaCast = listaCast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elenco_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cast cast = listaCast.get(position);
        holder.onBind(cast);
    }

    public void atualizaLista(List<Cast> novaLista){
        if(listaCast.isEmpty()){
            this.listaCast = novaLista;
        } else {
            this.listaCast.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaCast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagemElenco;
        private TextView textNome;
        private TextView textPersonagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemElenco = itemView.findViewById(R.id.imagemElenco);
            textNome = itemView.findViewById(R.id.nomeElenco);
            textPersonagem = itemView.findViewById(R.id.personagemElenco);
        }

        public void onBind(Cast cast){
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ cast.getProfilePath()).into(imagemElenco);
            textNome.setText(cast.getName());
            textPersonagem.setText(cast.getCharacter());
        }
    }
}
