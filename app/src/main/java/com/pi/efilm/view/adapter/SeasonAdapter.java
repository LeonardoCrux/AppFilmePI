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
import com.pi.efilm.model.series.Season;
import com.pi.efilm.view.activity.SeasonDetalheActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
import static com.pi.efilm.util.Constantes.ID;
import static com.pi.efilm.util.Constantes.NUMBER;
import static com.pi.efilm.util.Constantes.URL_IMAGEM;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
    private List<Season> seasonList;
    private  long idSerie;

    public SeasonAdapter(List<Season> seasonList, long idSerie) {
        this.seasonList = seasonList;
        this.idSerie=idSerie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temporadas_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Season season = seasonList.get(position);
        holder.onBind(season);
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textTemporada, textEpisodios;
        private ImageView imageView;

        public ViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);
            textTemporada = v.findViewById(R.id.textTemporadaN);
            textEpisodios = v.findViewById(R.id.textEpisodiosLista);
            imageView = v.findViewById(R.id.imagemSeasonLista);
        }

        public void onBind(Season season) {
            textTemporada.setText(season.getName());
            textEpisodios.setText(season.getEpisodeCount().toString() + " episodios");
            Picasso.get().load(URL_IMAGEM + season.getPosterPath()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), SeasonDetalheActivity.class);
            intent.putExtra(ID, idSerie);
            intent.putExtra(NUMBER ,seasonList.get(getAdapterPosition()).getSeasonNumber());
            v.getContext().startActivity(intent);
        }
    }
}
