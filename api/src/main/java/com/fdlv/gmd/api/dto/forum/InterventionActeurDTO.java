package com.fdlv.gmd.api.dto.forum;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"acteur", "forum","roleActeur","structure"})
public class InterventionActeurDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long finId;
	private ActeurDTO acteur;
	private ForumDTO forum;
	private RoleActeurDTO roleActeur;
	private StructureDTO structure;
	@NotEmpty(message = "la description de l'intervention est obligatioire")
	private String finDescription;
	private LocalDate finDateDebut;
	private LocalDate finDateFin;
	private String finCommentaire;
	private String finCharteAssoSigne;
	private LocalDate finCharteAssoSigneDate;
}