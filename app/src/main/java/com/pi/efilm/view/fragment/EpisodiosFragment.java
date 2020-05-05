package com.pi.efilm.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.efilm.R;
import com.pi.efilm.model.series.SeasonDetalhes.Episode;
import com.squareup.picasso.Picasso;

import static com.pi.efilm.util.Constantes.EP;
import static com.pi.efilm.util.Constantes.URL_IMAGEM;

/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodiosFragment extends Fragment {
    private TextView nome, sinopse, data;
    private ImageView imageView;
    private Episode episode;

    public EpisodiosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_episodios, container, false);
        recuperaDados();
        initView(view);
        setDetalhes(episode);


        return view;
    }

    public void initView(View v){
        nome = v.findViewById(R.id.nomeEpisodio);
        imageView = v.findViewById(R.id.imagemEpisodioDetalhe);
        sinopse = v.findViewById(R.id.sinopseEpisodio);
        data = v.findViewById(R.id.dataEpisodio);
    }

    public void recuperaDados(){
        Bundle bundle = getArguments();
        episode = bundle.getParcelable(EP );
    }

    public void setDetalhes(Episode episode){
        nome.setText(episode.getName());
        Picasso.get().load(URL_IMAGEM+ episode.getStillPath()).into(imageView);
        sinopse.setText(episode.getOverview());
        data.setText(episode.getAirDate());
    }


}
