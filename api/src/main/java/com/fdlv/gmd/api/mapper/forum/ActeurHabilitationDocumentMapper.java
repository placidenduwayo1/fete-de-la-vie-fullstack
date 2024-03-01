package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.ActeurHabilitationDocument;
import com.fdlv.gmd.api.dto.forum.ActeurHabilitationDocumentDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { DocumentMapper.class, RoleActeurMapper.class })
public interface ActeurHabilitationDocumentMapper extends EntityMapper<ActeurHabilitationDocumentDTO, ActeurHabilitationDocument> {
    ActeurHabilitationDocumentMapper INSTANCE = Mappers.getMapper(ActeurHabilitationDocumentMapper.class);

    ActeurHabilitationDocumentDTO toDto(ActeurHabilitationDocument acteurHabilitationDocument);
    ActeurHabilitationDocument toEntity(ActeurHabilitationDocumentDTO acteurHabilitationDocumentDTO);
}