package com.pi.appfilme.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.appfilme.model.filme.creditos.Cast;

import java.util.List;

public class ElencoAdapter extends RecyclerView.Adapter<ElencoAdapter.ViewHolder>{
    private List<Cast> listaCast;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagemElenco;
        private TextView textNome;
        private TextView textPersonagem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
