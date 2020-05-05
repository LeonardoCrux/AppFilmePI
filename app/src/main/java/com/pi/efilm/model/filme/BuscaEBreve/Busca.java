
package com.pi.efilm.model.filme.BuscaEBreve;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Busca {

    @Expose
    private Long page;
    @Expose
    private List<ResultFilme> resultFilmes;
    @SerializedName("total_pages")
    private Long totalPages;
    @SerializedName("total_results")
    private Long totalResults;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<ResultFilme> getResultFilmes() {
        return resultFilmes;
    }

    public void setResultFilmes(List<ResultFilme> resultFilmes) {
        this.resultFilmes = resultFilmes;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}