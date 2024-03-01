package com.fdlv.gmd.api.domain.forum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "forum_stand_banniere")
public class StandBanniere implements Serializable {
    @Id
    @Column(name = "fsb_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fsb_fse_id", foreignKey = @ForeignKey(name = "fk_fsb_fse_id", value = ConstraintMode.CONSTRAINT), nullable = false, referencedColumnName = "fse_id", columnDefinition = "bigint NOT NULL COMMENT 'Id Structure responsable de la bannière'")
    private Structure structure;

    @Column(name = "fsb_banniere_libelle", length = 50, columnDefinition = "varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Libellé de la Bannièe'")
    private String banniereLibelle;

    @Column(name = "fsb_banniere_url", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'url de la Bannière'")
    private String banniereUrl;

    // Commented inverse direction till we need it, then we'll uncomment
//    @OneToMany(mappedBy = "standBanniere")
//	@JsonIgnoreProperties()
//	private Set<InterventionStand> interventionsStand = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getBanniereLibelle() {
        return banniereLibelle;
    }

    public void setBanniereLibelle(String banniereLibelle) {
        this.banniereLibelle = banniereLibelle;
    }

    public String getBanniereUrl() {
        return banniereUrl;
    }

    public void setBanniereUrl(String banniereUrl) {
        this.banniereUrl = banniereUrl;
    }
    
//    public Set<InterventionStand> getInterventionsStand() {
//		return interventionsStand;
//	}
//
//	public void setInterventionsStand(Set<InterventionStand> interventionsStand) {
//		this.interventionsStand = interventionsStand;
//	}

	@Override
    public String toString() {
        return "StandBanniere{" +
                "id=" + id +
                ", structure=" + structure +
                ", banniereLibelle='" + banniereLibelle + '\'' +
                ", banniereUrl='" + banniereUrl + '\'' +
                '}';
    }
}