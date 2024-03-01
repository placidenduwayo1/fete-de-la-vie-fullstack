package com.fdlv.gmd.api.dto.forum;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ForumThemeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "code : le code du thème forum est obligatoire.")
	private String code;

	@NotEmpty(message = "libelle : le libelle du thème forum est obligatoire.")
	private String libelle;

	@Length(max = 255, message = "commentaire : Le nombre de caractère maximum d'un commentaire est 255.")
	private String commentaire;
}