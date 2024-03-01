package com.fdlv.gmd.api.dto.fdlv;

import com.fdlv.gmd.api.domain.enumeration.ModulePartenaire;
import com.fdlv.gmd.api.domain.enumeration.Priorite;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * This class is use to convert a partenaires into a flutter and website understable partenaire
 */
public class PartenaireModuleDTO {

    private Long fpe_id;

    @NotNull
    private Long fpe_num_ordre;
    @NotNull
    private String fpe_image;

    private Integer fpe_priorite;

    private Integer fpe_module;

    public PartenaireModuleDTO(Long fpe_id, Long fpe_num_ordre, String fpe_image, Integer fpe_priorite, Integer fpe_module) {
        this.fpe_id = fpe_id;
        this.fpe_num_ordre = fpe_num_ordre;
        this.fpe_image = fpe_image;
        this.fpe_priorite = fpe_priorite;
        this.fpe_module = fpe_module;
    }

    public Long getFpe_id() {
        return fpe_id;
    }

    public void setFpe_id(Long fpe_id) {
        this.fpe_id = fpe_id;
    }

    public Long getFpe_num_ordre() {
        return fpe_num_ordre;
    }

    public void setFpe_num_ordre(Long fpe_num_ordre) {
        this.fpe_num_ordre = fpe_num_ordre;
    }

    public String getFpe_image() {
        return fpe_image;
    }

    public void setFpe_image(String fpe_image) {
        this.fpe_image = fpe_image;
    }

    public Integer getFpe_priorite() {
        return fpe_priorite;
    }

    public void setFpe_priorite(Integer fpe_priorite) {
        this.fpe_priorite = fpe_priorite;
    }

    public Integer getFpe_module() {
        return fpe_module;
    }

    public void setFpe_module(Integer fpe_module) {
        this.fpe_module = fpe_module;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PartenaireModuleDTO)) {
            return false;
        }

        PartenaireModuleDTO partenaireDTO = (PartenaireModuleDTO) obj;
        if (this.fpe_id == null) {
            return false;
        }
        return Objects.equals(this.fpe_id, partenaireDTO.fpe_id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(fpe_id);
    }

    @Override
    public String toString() {
        return "PartenaireModuleDTO{" +
                "fpe_id=" + fpe_id +
                ", fpe_num_ordre=" + fpe_num_ordre +
                ", fpe_image='" + fpe_image + '\'' +
                ", fpe_priorite=" + fpe_priorite +
                ", fpe_module=" + fpe_module +
                '}';
    }
}
