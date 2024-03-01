package com.fdlv.gmd.api.dto.fdlv;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.domain.enumeration.ModulePartenaire;
import com.fdlv.gmd.api.domain.enumeration.Priorite;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.fdlv.Partenaire} entity.
 */

public class PartenaireDTO implements Serializable{

    private Long id;

    @NotNull
    private Long num_ordre;
    @NotNull
    private String image;

    private Priorite priorite;
    
    private ModulePartenaire module;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNum_ordre() {
        return num_ordre;
    }

    public void setNum_ordre(Long num_ordre) {
        this.num_ordre = num_ordre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public ModulePartenaire getModule() {
        return module;
    }

    public void setModule(ModulePartenaire module) {
        this.module = module;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PartenaireDTO)) {
            return false;
        }

        PartenaireDTO partenaireDTO = (PartenaireDTO) obj;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partenaireDTO.id);
    }

    @Override
    public String toString() {
        return "PartenaireDTO{" +
        "id=" + getId() +
        ", num_ordre='" + getNum_ordre() + "'" +
        ", image='" + getImage() + "'" +
        ", priorite='" + getPriorite() + "'" +
        ", module=" + getModule() +
        "}";
    }

}
