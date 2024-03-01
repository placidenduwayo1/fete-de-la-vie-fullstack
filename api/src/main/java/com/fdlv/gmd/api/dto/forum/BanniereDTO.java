package com.fdlv.gmd.api.dto.forum;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BanniereDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "id Thème : le id Thème du bannière est obligatoire.")
    private Long forumThemeId;

    @NotEmpty(message = "code : le code du bannière est obligatoire.")
    private String code;

    @NotEmpty(message = "libelle : le libelle du bannière est obligatoire.")
    private String libelle;

    private String url;

    @NotEmpty(message = "fdlv : cette valeur est obligatoire.")
    private String fdlv;

    @Length(max = 255, message = "commentaire : Le nombre de caractère maximum d'un commentaire est 255.")
    private String commentaire;
}