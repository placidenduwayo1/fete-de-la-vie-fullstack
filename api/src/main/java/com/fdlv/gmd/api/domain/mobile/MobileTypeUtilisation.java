package com.fdlv.gmd.api.domain.mobile;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Entity for the table mobile_type_utilisation
 */
@Entity
@Table(name = "mobile_type_utilisation")
public class MobileTypeUtilisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mob_id")
    private Long mob_id;

    @Column(name = "mob_type_utilisation")
    private Integer mob_type_utilisation;
    @Column(name = "mob_description")
    private String mob_description;

    public Long getMob_id() {
        return mob_id;
    }

    public void setMob_id(Long mob_id) {
        this.mob_id = mob_id;
    }

    public Integer getMob_type_utilisation() {
        return mob_type_utilisation;
    }

    public void setMob_type_utilisation(Integer mob_type_utilisation) {
        this.mob_type_utilisation = mob_type_utilisation;
    }

    public String getMob_description() {
        return mob_description;
    }

    public void setMob_description(String mob_description) {
        this.mob_description = mob_description;
    }
}
