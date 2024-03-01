package com.fdlv.gmd.api.dto.forum;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NiveauResponsabiliteDTO {
    private Long id;
    private char responsibilityLevel;
    private String libelle;
}
