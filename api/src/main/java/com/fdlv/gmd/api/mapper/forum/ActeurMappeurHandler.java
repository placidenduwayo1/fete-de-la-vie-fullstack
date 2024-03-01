package com.fdlv.gmd.api.mapper.forum;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fdlv.gmd.api.domain.forum.Acteur;
import com.fdlv.gmd.api.domain.forum.Forum;
import com.fdlv.gmd.api.domain.forum.InterventionActeur;
import com.fdlv.gmd.api.domain.forum.RoleActeur;
import com.fdlv.gmd.api.domain.forum.Structure;
import com.fdlv.gmd.api.dto.forum.ActeurDTO;
import com.fdlv.gmd.api.dto.forum.ForumDTO;
import com.fdlv.gmd.api.dto.forum.RoleActeurDTO;
import com.fdlv.gmd.api.dto.forum.StructureDTO;

@Component
public class ActeurMappeurHandler implements EntityMapper<ActeurDTO, Acteur> {

	@Override
	public ActeurDTO entityToDto(Acteur acteur) {
		final ActeurDTO acteurDTO = new ActeurDTO();
		acteurDTO.setId(acteur.getId());
		acteurDTO.setReference(acteur.getReference());
		acteurDTO.setName(acteur.getName());
		acteurDTO.setLastName(acteur.getLastName());
		acteurDTO.setLogin(acteur.getLogin());
		acteurDTO.setPasswordHash(acteur.getPasswordHash());
		acteurDTO.setCreatedBy(acteur.getCreatedBy());
		acteurDTO.setPhotoUrl(acteur.getPhotoUrl());
		acteurDTO.setNumTelBureau(acteur.getNumTelBureau());
		acteurDTO.setNumTelPortable(acteur.getNumTelPortable());
		acteurDTO.setEmail(acteur.getEmail());
		acteurDTO.setActeurFDLV(acteur.getActeurFDLV());
		acteurDTO.setResponsable(acteur.getResponsable());
		acteurDTO.setCreerUsers(acteur.getCreerUsers());
		acteurDTO.setBenevole(acteur.getBenevole());
		acteurDTO.setCorrespondant(acteur.getCorrespondant());
		acteurDTO.setService(acteur.getService());
		acteurDTO.setCommentaire(acteur.getCommentaire());
		acteurDTO.setDateDebutValidite(acteur.getDateDebutValidite());
		acteurDTO.setDateFinValidite(acteur.getDateFinValidite());
		acteurDTO.setActif(acteur.getActif());

		// Structure auquelle est rattachée l'acteur
		if (acteur.getStructureId() != null) {
			final StructureDTO structureDTO = new StructureDTO();
			structureDTO.setId(acteur.getStructureId().getId());
			structureDTO.setLibelle(acteur.getStructureId().getLibelle());
			acteurDTO.setStructureId(structureDTO);
		}

		// Forum le plus récent de l'acteur
		final Optional<Forum> forum = this.getForum(acteur);
		if (forum.isPresent()) {
			final ForumDTO forumDTO = new ForumDTO();
			final Forum realForum = forum.get();
			forumDTO.setId(realForum.getId());
			forumDTO.setLibelle(realForum.getLibelle());
			forumDTO.setCodePostal(realForum.getCodePostal());
			acteurDTO.setForum(forumDTO);
		}

		if (acteur.getCreatedByActor() != null) {
			final ActeurDTO createdByActor = new ActeurDTO();
			createdByActor.setId(acteur.getCreatedByActor().getId());
			createdByActor.setActeurFDLV(acteur.getCreatedByActor().getActeurFDLV());
			createdByActor.setActif(acteur.getCreatedByActor().getActif());
			createdByActor.setBenevole(acteur.getCreatedByActor().getBenevole());
			createdByActor.setCommentaire(acteur.getCreatedByActor().getCommentaire());
			createdByActor.setCorrespondant(acteur.getCreatedByActor().getCorrespondant());
			createdByActor.setCreatedBy(acteur.getCreatedByActor().getCreatedBy());
			createdByActor.setCreerUsers(acteur.getCreatedByActor().getCreerUsers());
			createdByActor.setDateDebutValidite(acteur.getCreatedByActor().getDateDebutValidite());
			createdByActor.setDateFinValidite(acteur.getCreatedByActor().getDateFinValidite());
			createdByActor.setEmail(acteur.getCreatedByActor().getEmail());
			createdByActor.setLastName(acteur.getCreatedByActor().getLastName());
			createdByActor.setLogin(acteur.getCreatedByActor().getLogin());
			createdByActor.setName(acteur.getCreatedByActor().getName());
			createdByActor.setNumTelBureau(acteur.getCreatedByActor().getNumTelBureau());
			createdByActor.setNumTelPortable(acteur.getCreatedByActor().getNumTelPortable());
			createdByActor.setPhotoUrl(acteur.getCreatedByActor().getPhotoUrl());
			createdByActor.setReference(acteur.getCreatedByActor().getReference());
			createdByActor.setResponsable(acteur.getCreatedByActor().getResponsable());
			createdByActor.setService(acteur.getCreatedByActor().getService());

			acteurDTO.setCreatedByActor(createdByActor);
		}
		if (acteur.getResponsableActor() != null) {
			final ActeurDTO responsableActeur = new ActeurDTO();
			responsableActeur.setId(acteur.getCreatedByActor().getId());
			responsableActeur.setActeurFDLV(acteur.getCreatedByActor().getActeurFDLV());
			responsableActeur.setActif(acteur.getCreatedByActor().getActif());
			responsableActeur.setBenevole(acteur.getCreatedByActor().getBenevole());
			responsableActeur.setCommentaire(acteur.getCreatedByActor().getCommentaire());
			responsableActeur.setCorrespondant(acteur.getCreatedByActor().getCorrespondant());
			responsableActeur.setCreatedBy(acteur.getCreatedByActor().getCreatedBy());
			responsableActeur.setCreerUsers(acteur.getCreatedByActor().getCreerUsers());
			responsableActeur.setDateDebutValidite(acteur.getCreatedByActor().getDateDebutValidite());
			responsableActeur.setDateFinValidite(acteur.getCreatedByActor().getDateFinValidite());
			responsableActeur.setEmail(acteur.getCreatedByActor().getEmail());
			responsableActeur.setLastName(acteur.getCreatedByActor().getLastName());
			responsableActeur.setLogin(acteur.getCreatedByActor().getLogin());
			responsableActeur.setName(acteur.getCreatedByActor().getName());
			responsableActeur.setNumTelBureau(acteur.getCreatedByActor().getNumTelBureau());
			responsableActeur.setNumTelPortable(acteur.getCreatedByActor().getNumTelPortable());
			responsableActeur.setPhotoUrl(acteur.getCreatedByActor().getPhotoUrl());
			responsableActeur.setReference(acteur.getCreatedByActor().getReference());
			responsableActeur.setResponsable(acteur.getCreatedByActor().getResponsable());
			responsableActeur.setService(acteur.getCreatedByActor().getService());

			acteurDTO.setResponsableActor(responsableActeur);
		}

		if (acteur.getForumRoleActeur() != null) {
			final RoleActeurDTO roleActeurDTO = new RoleActeurDTO();

			roleActeurDTO.setId(acteur.getForumRoleActeur().getId());
			roleActeurDTO.setCodeRoleActeur(acteur.getForumRoleActeur().getCodeRoleActeur());
			roleActeurDTO.setRoleActeur(acteur.getForumRoleActeur().getRoleActeur());

			acteurDTO.setForumRoleActeur(roleActeurDTO);
		}

		return acteurDTO;
	}

	@Override
	public Acteur dtoToEntity(ActeurDTO acteurDTO) {
		final Acteur acteur = new Acteur();

		acteur.setActeurFDLV(acteurDTO.getActeurFDLV());
		acteur.setActif(acteurDTO.getActif());
		acteur.setBenevole(acteurDTO.getBenevole());
		acteur.setCommentaire(acteurDTO.getCommentaire());
		acteur.setCorrespondant(acteurDTO.getCorrespondant());
		acteur.setCreatedBy(acteurDTO.getCreatedBy());
		acteur.setCreerUsers(acteurDTO.getCreerUsers());
		acteur.setDateDebutValidite(acteurDTO.getDateDebutValidite());
		acteur.setDateFinValidite(acteurDTO.getDateFinValidite());
		acteur.setEmail(acteurDTO.getEmail());
		acteur.setId(acteurDTO.getId());
		acteur.setLastName(acteurDTO.getLastName());
		acteur.setLogin(acteurDTO.getLogin());
		acteur.setName(acteurDTO.getName());
		acteur.setNumTelBureau(acteurDTO.getNumTelBureau());
		acteur.setNumTelPortable(acteurDTO.getNumTelPortable());
		acteur.setPasswordHash(acteurDTO.getPasswordHash());
		acteur.setPhotoUrl(acteurDTO.getPhotoUrl());
		acteur.setReference(acteurDTO.getReference());
		acteur.setResponsable(acteurDTO.getResponsable());
		acteur.setService(acteurDTO.getService());

		if (acteurDTO.getForumRoleActeur() != null) {
			final RoleActeur roleActeur = new RoleActeur();
			acteur.setForumRoleActeur(roleActeur);
		}

		if (acteurDTO.getForum() != null) {
			final ForumDTO forumDTO = acteurDTO.getForum();
			final Forum forum = new Forum();
			forum.setId(forumDTO.getId());
			final InterventionActeur interventionActeur = new InterventionActeur();
			interventionActeur.setForum(forum);
			acteur.getInterventionActeurs().add(interventionActeur);
		}

		if (acteurDTO.getStructureId() != null) {
			final Structure structure = new Structure();
			structure.setId(acteurDTO.getStructureId().getId());
			acteur.setStructureId(structure);
		}

		if (acteurDTO.getCreatedByActor() != null) {
			final Acteur createdByActor = new Acteur();
			createdByActor.setId(acteurDTO.getCreatedByActor().getId());
			acteur.setCreatedByActor(createdByActor);
		}
		if (acteurDTO.getResponsableActor() != null) {
			final Acteur responsableActeur = new Acteur();
			responsableActeur.setId(acteurDTO.getCreatedByActor().getId());
			acteur.setResponsableActor(responsableActeur);
		}

		return acteur;
	}

	private Optional<Forum> getForum(Acteur acteur) {
		final Set<InterventionActeur> interventionActeurs = acteur.getInterventionActeurs();
		if (interventionActeurs.isEmpty()) {
			return Optional.empty();
		}

		if (interventionActeurs.size() == 1) {
			return interventionActeurs.stream().map(InterventionActeur::getForum).findFirst();
		}

		return interventionActeurs.stream().map(InterventionActeur::getForum)
				.max((forum1, forum2) -> forum1.getDateOuverture().compareTo(forum2.getDateOuverture()));
	}

}
