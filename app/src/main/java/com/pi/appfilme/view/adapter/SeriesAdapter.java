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
import com.pi.appfilme.model.series.ResultSeries;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MyViewHolderSeries>{
    private List<ResultSeries> popularList;

    public SeriesAdapter(List<ResultSeries> listaSeriesPopulares) {
        this.popularList = listaSeriesPopulares;
    }

    @NonNull
    @Override
    public MyViewHolderSeries onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filme_lista, parent, false);
        return new MyViewHolderSeries(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSeries holder, int position) {
        ResultSeries resultSeriesPopulares = popularList.get(position);
    }

    public void atualizaLista(List<ResultSeries> novaLista) {
        if (popularList.isEmpty()) {
            this.popularList = novaLista;
        } else {
            this.popularList.addAll(novaLista);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }


    public class MyViewHolderSeries extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textSerie;

        public MyViewHolderSeries(@NonNull View itemView) {
            super(itemView);
            textSerie = itemView.findViewById(R.id.textRecyclerCartaz);
            imageView = itemView.findViewById(R.id.imagemCartazRecycler);
        }

        public void bnd(ResultSeries resultSeries){
            textSerie.setText(resultSeries.getOriginalName());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + resultSeries.getPosterPath()).into(imageView);
        }
    }

}
