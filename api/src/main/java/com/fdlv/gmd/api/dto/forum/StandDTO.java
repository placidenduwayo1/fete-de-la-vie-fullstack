package com.fdlv.gmd.api.dto.forum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandDTO implements Serializable {
    private Long id;
    private StandSecteurDTO forumStandSecteur;
    private String standPhysique;
    private String libelle;
    private String reference;
    private String materiel;
    private String observation;
    private String besoinElectricite;
    private String commentaire;
}