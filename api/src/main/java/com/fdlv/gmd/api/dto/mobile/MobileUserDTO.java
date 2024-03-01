package com.fdlv.gmd.api.dto.mobile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fdlv.gmd.api.domain.enumeration.Sexe;
import com.fdlv.gmd.api.domain.enumeration.TrancheAge;

public class MobileUserDTO implements Serializable {

    private static final long serialVersionUID = 7042187797899719461L;

    private Long id;
    private String pseudo;
    private Sexe sexe;
    private TrancheAge trancheAge;
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

    public Boolean getParcoursPartage() {
        return parcoursPartage;
    }

    public void setParcoursPartage(Boolean parcoursPartage) {
        this.parcoursPartage = parcoursPartage;
    }

    private Boolean parcoursPartage;

    private Long mus_id_user;
    private String mus_pseudo;

    private Integer mus_sexe;
    private Integer mus_tranche_age;
    private String mus_date_cpte_ferme;
    private Boolean msp_parcours_partage;

    public Long getMus_id_user() {
        return mus_id_user;
    }

    public void setMus_id_user(Long mus_id_user) {
        this.mus_id_user = mus_id_user;
    }

    public String getMus_pseudo() {
        return mus_pseudo;
    }

    public void setMus_pseudo(String mus_pseudo) {
        this.mus_pseudo = mus_pseudo;
    }

    public Integer getMus_sexe() {
        return mus_sexe;
    }

    public void setMus_sexe(Integer mus_sexe) {
        this.mus_sexe = mus_sexe;
    }

    public Integer getMus_tranche_age() {
        return mus_tranche_age;
    }

    public void setMus_tranche_age(Integer mus_tranch_age) {
        this.mus_tranche_age = mus_tranch_age;
    }

    public String getMus_date_cpte_ferme() {
        return mus_date_cpte_ferme;
    }

    public void setMus_date_cpte_ferme(String mus_date_cpte_ferme) {
        this.mus_date_cpte_ferme = mus_date_cpte_ferme;
    }

    public Boolean getMsp_parcours_partage() {
        return msp_parcours_partage;
    }

    public void setMsp_parcours_partage(Boolean msp_parcours_partage) {
        this.msp_parcours_partage = msp_parcours_partage;
    }

    @Override
    public String toString() {
        return "MobileUserDTO{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", sexe=" + sexe +
                ", trancheAge=" + trancheAge +
                ", dateFermeture=" + dateFermeture +
                ", parcoursPartage=" + parcoursPartage +
                ", mus_id_user=" + mus_id_user +
                ", mus_pseudo='" + mus_pseudo + '\'' +
                ", mus_sexe=" + mus_sexe +
                ", mus_tranch_age=" + mus_tranche_age +
                ", mus_date_cpte_ferme='" + mus_date_cpte_ferme + '\'' +
                ", msp_parcours_partage=" + msp_parcours_partage +
                '}';
    }
}
