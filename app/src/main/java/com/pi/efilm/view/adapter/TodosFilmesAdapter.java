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

import static android.os.Build.ID;

public class TodosFilmesAdapter extends RecyclerView.Adapter<TodosFilmesAdapter.ViewHolder> {
    private List<ResultFilme> listResult;

    public TodosFilmesAdapter(List<ResultFilme> listResult) {
        this.listResult = listResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todos_filmes_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultFilme resultFilme = listResult.get(position);
        holder.onBind(resultFilme);
    }

    public void atualizaLista(List<ResultFilme> novaLista){
        if(this.listResult.isEmpty()){
            listResult = novaLista;
        } else {
            this.listResult.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imagemTodos;
        private TextView tituloTodos;
        private TextView texteDe, textData;
        private TextView textVote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemTodos = itemView.findViewById(R.id.imagemFilmeTodos);
            tituloTodos = itemView.findViewById(R.id.textTituloTodos);
            textVote = itemView.findViewById(R.id.textVote);
            textData = itemView.findViewById(R.id.textDataFilmeLista);
            itemView.setOnClickListener(this);
        }

        private void onBind(ResultFilme resultFilme){
            tituloTodos.setText(resultFilme.getTitle());
            textVote.setText(resultFilme.getVoteAverage().toString() + "/10");
            textData.setText(resultFilme.getReleaseDate());
            Picasso.get().load(Constantes.URL_IMAGEM+ resultFilme.getPosterPath()).into(imagemTodos);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FilmeDetalheActivity.class);
            long idFilme = listResult.get(getAdapterPosition()).getId();
            intent.putExtra(ID, idFilme);
            v.getContext().startActivity(intent);
        }
    }

}
