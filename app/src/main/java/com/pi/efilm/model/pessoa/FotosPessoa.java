
package com.pi.efilm.model.pessoa;

import java.util.List;
import com.google.gson.annotations.Expose;

public class FotosPessoa {

    @Expose
    private Long id;
    @Expose
    private List<Profile> profiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

}
