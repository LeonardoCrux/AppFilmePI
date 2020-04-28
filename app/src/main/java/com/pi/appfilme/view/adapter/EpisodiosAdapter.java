package com.pi.appfilme.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.pi.appfilme.R;
import com.pi.appfilme.model.series.SeasonDetalhes.Episode;
import com.pi.appfilme.view.fragment.EpisodiosFragment;

import java.util.List;

public class EpisodiosAdapter extends RecyclerView.Adapter<EpisodiosAdapter.ViewHolder> {
    private List<Episode> episodeList;

    public EpisodiosAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodios_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.onBind(episode);

    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textNome;

        public ViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);
            textNome = v.findViewById(R.id.episodiosListaNome);
        }

        public void onBind(Episode episode){
            textNome.setText(episode.getEpisodeNumber() + " - " + episode.getName());
        }

        public void envioDadosEpisodio(Episode ep, View v) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("EP", ep);
            Fragment fragment = new EpisodiosFragment();
            fragment.setArguments(bundle);
            replaceFragments(fragment, v.getContext());

        }

        private void replaceFragments(Fragment fragment, Context context) {
            FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.containerEpisodio, fragment);
            transaction.commit();
        }


        @Override
        public void onClick(View v) {
            envioDadosEpisodio(episodeList.get(getAdapterPosition()), v);
        }
    }
}
