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
public class StandSecteurDTO implements Serializable {
    private Long id;
    private String codeSecteur;
    private String libelle;
    private int nbStands;
}
