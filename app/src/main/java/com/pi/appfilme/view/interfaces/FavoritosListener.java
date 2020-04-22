package com.pi.appfilme.view.interfaces;

import android.view.View;

import com.pi.appfilme.model.filme.detalhes.Detalhes;
import com.pi.appfilme.model.series.SeasonDetalhes.Episode;

public interface FavoritosListener {

    void clickFavorito(Detalhes detalhes);
}
