package com.pi.efilm.view.interfaces;

import com.pi.efilm.model.filme.detalhes.Detalhes;
import com.pi.efilm.model.series.ResultSeriesDetalhe;

public interface FavoritosListener {

    void deleteFavorito(Detalhes detalhes);
    void clickFavorito(Detalhes detalhes);
    void deleteFavoritoSerie(ResultSeriesDetalhe detalhes);
    void clickFavoritoSerie(ResultSeriesDetalhe detalhes);
}
