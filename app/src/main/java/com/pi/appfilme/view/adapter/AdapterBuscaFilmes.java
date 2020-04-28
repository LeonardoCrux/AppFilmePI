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
import com.pi.appfilme.util.Constantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterBuscaFilmes extends RecyclerView.Adapter<AdapterBuscaFilmes.ViewHolder> {
    private List<ResultFilme> resultFilmeList;

    public AdapterBuscaFilmes(List<ResultFilme> listResult) {
        this.resultFilmeList = listResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.busca_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ResultFilme resultFilme = resultFilmeList.get(position);
        holder.onBind(resultFilme);
    }

    public void atualizaLista(List<ResultFilme> novaLista){
        if(resultFilmeList.isEmpty()) {
            this.resultFilmeList = novaLista;
        }else {
            resultFilmeList.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return resultFilmeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textNome, textData;
        private ImageView imageView;

        public ViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);
            textNome = v.findViewById(R.id.busca_filme_textView);
            textData = v.findViewById(R.id.estreia_busca_textView);
            imageView = v.findViewById(R.id.imagem_busca);
        }

        public void onBind(ResultFilme resultFilme){
            textNome.setText(resultFilme.getTitle());
            textData.setText(resultFilme.getReleaseDate());
            Picasso.get().load(Constantes.URL_IMAGEM+ resultFilme.getPosterPath()).into(imageView);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
