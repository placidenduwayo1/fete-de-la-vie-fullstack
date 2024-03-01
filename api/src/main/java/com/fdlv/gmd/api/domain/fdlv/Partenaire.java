package com.fdlv.gmd.api.domain.fdlv;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fdlv.gmd.api.domain.enumeration.ModulePartenaire;
import com.fdlv.gmd.api.domain.enumeration.Priorite;

/**
 * A Partenaire.
 */
@Entity
@Table(name ="fdlv_partenaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Partenaire implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fpe_id")
    private Long id;

    @Column(name = "fpe_num_ordre")
    private Long num_ordre;

    @Column(name = "fpe_image")
    private String image;

    @Column(name = "fpe_priorite")
    @Convert(converter = PrioriteConverter.class)
    private Priorite priorite;

    @Column(name = "fpe_module")
    @Convert(converter = ModulePartenaireConverter.class)
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

  
    
    
    
}
