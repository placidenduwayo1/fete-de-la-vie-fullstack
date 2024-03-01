package com.fdlv.gmd.api.mapper;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur;
import com.fdlv.gmd.api.dto.ChoixOrganisateurDTO;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ChoixOrganisateur} and its DTO {@link ChoixOrganisateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChoixOrganisateurMapper extends EntityMapper<ChoixOrganisateurDTO, ChoixOrganisateur> {
    @Mapping(source = "etatScenario.dbValue",target = "etatScenarioId")
    ChoixOrganisateurDTO toDto(ChoixOrganisateur choixOrganisateur);
}


