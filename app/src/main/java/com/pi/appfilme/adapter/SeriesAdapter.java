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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_lista, parent, false);
        return new MyViewHolderSeries(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSeries holder, int position) {
        ResultSeries resultSeriesPopulares = popularList.get(position);
        holder.bind(resultSeriesPopulares);
    }

    public void setResult(List<ResultSeries> resultado) {
        if (resultado.size() != 0) {
            this.popularList = resultado;
        } else {
            this.popularList.addAll(resultado);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        Log.i("LISTA", "Lista"+ popularList.size());
        return popularList.size();
    }


    public class MyViewHolderSeries extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textSerie;

        public MyViewHolderSeries(@NonNull View itemView) {
            super(itemView);
            textSerie = itemView.findViewById(R.id.textPopularSerie);
            imageView = itemView.findViewById(R.id.imagemSeriesPopular);
        }

        public void bind(ResultSeries resultSeriesBind){
            textSerie.setText(resultSeriesBind.getOriginalName());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+resultSeriesBind.getPosterPath()).into(imageView);
        }
    }

}
