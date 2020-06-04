package com.pi.efilm.view.interfaces;

import com.pi.efilm.model.filme.detalhes.Detalhes;
import com.pi.efilm.model.series.ResultSeriesDetalhe;

public interface PessoaListener {

    void clickFilme(Detalhes detalhes);
    void clickSerie(ResultSeriesDetalhe seriesDetalhe);
}
