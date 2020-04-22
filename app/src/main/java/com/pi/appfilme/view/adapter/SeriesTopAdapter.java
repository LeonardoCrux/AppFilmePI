package com.pi.appfilme.view.adapter;

import android.content.Intent;
import android.util.Log;
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

public class SeriesTopAdapter extends RecyclerView.Adapter<SeriesTopAdapter.ViewHolder> {
    private List<ResultSeriesTop> listSeriesTop;

    public SeriesTopAdapter(List<ResultSeriesTop> listSeriesTop) {
        this.listSeriesTop = listSeriesTop;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filme_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        private ImageView imageView;
        private TextView nome;

        public ViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);
            imageView = v.findViewById(R.id.imagemCartazRecycler);
            nome = v.findViewById(R.id.textRecyclerCartaz);
        }

        public void onBind(ResultSeriesTop resultSeriesTop){
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ resultSeriesTop.getPosterPath()).into(imageView);
            nome.setText(resultSeriesTop.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), SerieDetalheActivity.class);
            intent.putExtra("ID", listSeriesTop.get(getAdapterPosition()).getId());
            v.getContext().startActivity(intent);
        }
    }
}
