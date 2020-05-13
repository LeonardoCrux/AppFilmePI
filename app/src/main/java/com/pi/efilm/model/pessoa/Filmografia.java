
package com.pi.efilm.model.pessoa;

import java.util.List;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Filmografia {

    @Expose
    private List<FilmesPessoa> cast;
    @Expose
    private List<Crew> crew;
    @Expose
    private Long id;

    public List<FilmesPessoa> getCast() {
        return cast;
    }

    public void setCast(List<FilmesPessoa> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
