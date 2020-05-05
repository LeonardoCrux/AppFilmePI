package com.pi.efilm.view.interfaces;

import com.pi.efilm.model.filme.detalhes.Detalhes;

public interface FavoritosListener {

    void deleteFavorito(Detalhes detalhes);
    void clickFavorito(Detalhes detalhes);
}
