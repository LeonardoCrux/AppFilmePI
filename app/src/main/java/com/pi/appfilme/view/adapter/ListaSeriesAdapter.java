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
import com.pi.appfilme.model.series.ResultSeriesTop;
import com.pi.appfilme.view.activity.SerieDetalheActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListaSeriesAdapter extends RecyclerView.Adapter<ListaSeriesAdapter.ViewHolder> {

    private List<ResultSeriesTop> listSeriesTop;

    public ListaSeriesAdapter(java.util.List<ResultSeriesTop> listSeriesTop) {
        this.listSeriesTop = listSeriesTop;
    }

    @NonNull
    @Override
    public ListaSeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todos_filmes_lista, parent, false);
        return new ListaSeriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaSeriesAdapter.ViewHolder holder, int position) {
        ResultSeriesTop seriesTop = listSeriesTop.get(position);
        holder.onBind(seriesTop);

    }

    public void atualizaLista(List<ResultSeriesTop> novaLista){
        if(listSeriesTop.isEmpty()){
            listSeriesTop = novaLista;
        } else {
            listSeriesTop.addAll(novaLista);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listSeriesTop.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imagemTodos;
        private TextView tituloTodos;
        private TextView texteDe, textData;
        private TextView textVote;

        public ViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);
            imagemTodos = itemView.findViewById(R.id.imagemFilmeTodos);
            tituloTodos = itemView.findViewById(R.id.textTituloTodos);
            textVote = itemView.findViewById(R.id.textVote);
            textData = itemView.findViewById(R.id.textDataFilmeLista);
        }

        public void onBind(ResultSeriesTop resultSeriesTop){
            tituloTodos.setText(resultSeriesTop.getName());
            textVote.setText(resultSeriesTop.getVoteAverage().toString() + "/10");
            textData.setText(resultSeriesTop.getFirstAirDate());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ resultSeriesTop.getPosterPath()).into(imagemTodos);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), SerieDetalheActivity.class);
            intent.putExtra("ID", listSeriesTop.get(getAdapterPosition()).getId());
            v.getContext().startActivity(intent);
        }
    }
}
