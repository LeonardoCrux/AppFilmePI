package com.pi.appfilme.view.interfaces;

import android.view.View;

import com.pi.appfilme.model.series.SeasonDetalhes.Episode;

public interface Comunicador {

    void envioDadosSistemaOperacional(Episode ep, View v);
}
