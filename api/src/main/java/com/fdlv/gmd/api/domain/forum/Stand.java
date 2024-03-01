package com.fdlv.gmd.api.domain.forum;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "forum_stand")
public class Stand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fsd_id", nullable = false, columnDefinition = "bigint NOT NULL COMMENT 'Id du Stand du Forum'")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fsd_fss_id", referencedColumnName = "fss_id", nullable=false, foreignKey = @ForeignKey(name = "fk_fsd_fts_id"),
            columnDefinition = "bigint NOT NULL COMMENT 'Id du Thème du Stand'")
    @JsonManagedReference
    private StandSecteur forumStandSecteur;

    @Column(name = "fsd_stand_physique", length = 3, nullable = false, columnDefinition = "varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Oui = Stand Physique / Non = Présence uniqueent sans besin de Stand physique'")
    private String standPhysique;

    @Column(name = "fsd_libelle", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Libellé du Stand'")
    private String libelle;

    @Column(name = "fsd_reference", length = 15, columnDefinition = "varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Référence du stand composée de la concaténation de fsd_id-fsd_fss_id_fsd_stand_physique-(5première lettre de fsd_libelle)).\r\nCette réréference est générée automatiquement et non modifiable par l'utilsiateur '")
    private String reference;

    @Column(name = "fsd_matériel", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String materiel;

    @Column(name = "fsd_observation", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String observation;

    @Column(name = "fsd_besoin_electricite", length = 1, columnDefinition = "varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Oui/Non'")
    private String besoinElectricite;

    @Column(name = "fsd_commentaire", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Autres commentaire et précisions'")
    private String commentaire;
}