
package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fdlv.gmd.api.domain.forum.InterventionActeur;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import com.fdlv.gmd.api.annotation.DateComparaison;
import com.fdlv.gmd.api.annotation.DateComparaisons;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"createdByActor","responsableActor", "forumRoleActeur","structureId","interventionActeurs"})
@DateComparaisons({ @DateComparaison(dateProperties = { "dateDebutValidite",
		"dateFinValidite" }, message = "dateDebutValidite|dateFinValidite : La date de fin de validité doit être postérieure à la date de début validité.") })
public class ActeurDTO implements Serializable {
	private static final long serialVersionUID = 1646565563L;

	private Long id;

	@NotEmpty(message = "name : Le nom est obligatoire.")
	@Length(min = 3, message = "name : Le nom doit avoir plus de 3 caractères.")
	private String name;

	@NotEmpty(message = "lastName : Le prénom est obligatoire.")
	@Length(min = 3, message = "lastName : Le prénom doit avoir plus de 3 caractères.")
	private String lastName;

	private String login;

	@NotEmpty(message = "passwordHash : le mot de passse est obligatoire.")
	@Length(message = "passwordHash : le mot de passe ne doit pas dépasser 100 caractères ", max = 100)
	private String passwordHash;

	private String createdBy;

	private String photoUrl;

	@Length(min = 10, max = 21, message = "numTelPortable : Le numéro portable doit avoir entre 10 et 21 caractères.")
	private String numTelPortable;

	@Length(max = 21, message = "numTelBureau : Le numéro telephone doit avoir entre 10 et 21 caractères.")
	private String numTelBureau;

	@NotEmpty(message = "email : L'email est obligatoire.")
	private String email;

	@Length(max = 1, message = "acteurFDLV : acteurFDLV doit avoir un seul caractère, 0 ou 1.")
	private String acteurFDLV;

	@Length(max = 1, message = "responsable : responsable doit avoir un seul caractère, 0 ou 1.")
	private String responsable;

	@Length(max = 1, message = "creerUsers : creerUsers doit avoir un seul caractère, 0 ou 1.")
	private String creerUsers;

	@NotEmpty(message = "benevole : Important de savoir si benevole?")
	@Length(max = 1, message = "benevole : benevole doit avoir un seul caractère, 0 ou 1.")
	private String benevole;

	@NotEmpty(message = "correspondant : Important de savoir si correspondant?")
	@Length(max = 1, message = "correspondant : benevole doit avoir un seul caractère, 0 ou 1.")
	private String correspondant;

	private String actif;

	private String structure;

	private String service;

	private String commentaire;

	private String presenceStand;

	@NotNull(message = "dateDebutValidite: La de début validité est obligatoire.")
	private LocalDateTime dateDebutValidite;
	private LocalDateTime dateFinValidite;
	private ForumDTO forum;
	private ActeurDTO createdByActor;
	private ActeurDTO responsableActor;
	private RoleActeurDTO forumRoleActeur;
	private StructureDTO structureId;
	private String reference;
	private Set<InterventionActeur> interventionActeurs = new HashSet<>();;
}
