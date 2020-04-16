package com.pi.appfilme.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.filme.BuscaEBreve.ResultFilme;
import com.pi.appfilme.view.activity.FilmeDetalheActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        private ImageView estrela1;
        private ImageView estrela2;
        private ImageView estrela3;
        private ImageView estrela4;
        private ImageView estrela5;
        private TextView texteDe, textData;
        private TextView textVote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemTodos = itemView.findViewById(R.id.imagemFilmeTodos);
            tituloTodos = itemView.findViewById(R.id.textTituloTodos);
            textVote = itemView.findViewById(R.id.textVote);
            estrela1 = itemView.findViewById(R.id.estrela1);
            estrela2 = itemView.findViewById(R.id.estrela2);
            estrela3 = itemView.findViewById(R.id.estrela3);
            estrela4 = itemView.findViewById(R.id.estrela4);
            estrela5 = itemView.findViewById(R.id.estrela5);
            textData = itemView.findViewById(R.id.textDataFilmeLista);
            itemView.setOnClickListener(this);
        }

        private void onBind(ResultFilme resultFilme){
            tituloTodos.setText(resultFilme.getTitle());
            textVote.setText(resultFilme.getVoteAverage().toString() + "/10");
            textData.setText(resultFilme.getReleaseDate());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ resultFilme.getPosterPath()).into(imagemTodos);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), FilmeDetalheActivity.class);
            long idFilme = listResult.get(getAdapterPosition()).getId();
            intent.putExtra("ID", idFilme);
            v.getContext().startActivity(intent);
        }
    }

}
