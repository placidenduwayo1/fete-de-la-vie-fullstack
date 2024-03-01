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
public class RoleActeurDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@Length(max = 10, message = "codeRoleActeur : Le nombre de caractère de code du rôle acteur doit être inférieur ou égal à 10.")
	private String codeRoleActeur;

	@Length(max = 255, message = "roleActeur : Le nombre de caractère du rôle acteur doit être inférieur ou égal à 255.")
	private String roleActeur;
}