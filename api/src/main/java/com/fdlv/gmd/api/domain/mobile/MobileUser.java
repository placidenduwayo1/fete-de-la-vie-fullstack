package com.fdlv.gmd.api.domain.mobile;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fdlv.gmd.api.domain.enumeration.Sexe;
import com.fdlv.gmd.api.domain.enumeration.TrancheAge;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mobile_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MobileUser implements Serializable {

    private static final long serialVersionUID = 2683151418664758475L;

    @Id
    @Column(name = "mus_id_user",insertable = true,unique = true)
    private Long id;

    @Column(name = "mus_pseudo", length = 20)
    private String pseudo;

    @Column(name = "mus_sexe")
    @Enumerated
    private Sexe sexe;

    @Column(name = "mus_tranche_age")
    @Enumerated
    private TrancheAge trancheAge;

    @Column(name = "mus_date_cpte_ferme")
    private LocalDateTime dateFermeture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public TrancheAge getTrancheAge() {
        return trancheAge;
    }

    public void setTrancheAge(TrancheAge trancheAge) {
        this.trancheAge = trancheAge;
    }

    public LocalDateTime getDateFermeture() {
        return dateFermeture;
    }

    public void setDateFermeture(LocalDateTime dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

}
