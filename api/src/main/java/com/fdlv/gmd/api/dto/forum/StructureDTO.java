package com.fdlv.gmd.api.dto.forum;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"contact"})
public class StructureDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String libelle;
    private String reference;
    private String niveauResponsabilite;
    private String logoDescription;
    private String logoUrl;
    private String charteDescription;
    private String charteUrl;
    private String adresse01;
    private String adresse02;
    private String cp;
    private String commune;
    private ActeurDTO contact;
    private String telAccueilStructure;
    private String emailAccueilStructure;
    private String commentaire;
    private String conventionDescription;
    private String conventionUrl;
}
