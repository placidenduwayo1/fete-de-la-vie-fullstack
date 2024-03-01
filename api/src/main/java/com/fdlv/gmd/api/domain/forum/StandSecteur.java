package com.fdlv.gmd.api.domain.forum;
import lombok.*;
import org.hibernate.annotations.Formula;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "forum_stand_secteur")
public class StandSecteur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fss_id", nullable = false, columnDefinition = "bigint NOT NULL COMMENT 'Id Thème du Stand du Forum'")
    private Long id;

    @Column(name = "fss_code_secteur", length = 3, nullable = false, columnDefinition = "varchar(3) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Code secteur du Stand du Forum (Prévention, Animation, Démonstration, Organisation, etc ...)'")
    private String codeSecteur;

    @Column(name = "fss_libelle", nullable = false, columnDefinition = "varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'Libellé du Thème du Stand du Forum (Prévention, Animation, Démonstration, Organisation, etc ...)'")
    private String libelle;

    @Formula(value = "(SELECT COUNT(*) FROM forum_stand " +
            "WHERE forum_stand.fsd_fss_id = fss_id)")
    private int nbStands;

}