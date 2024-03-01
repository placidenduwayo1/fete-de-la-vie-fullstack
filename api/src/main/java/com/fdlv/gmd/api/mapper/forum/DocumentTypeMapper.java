package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.DocumentType;
import com.fdlv.gmd.api.dto.forum.DocumentTypeDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper extends EntityMapper<DocumentTypeDTO, DocumentType> {
    DocumentTypeMapper INSTANCE = Mappers.getMapper(DocumentTypeMapper.class);

    DocumentTypeDTO toDto(DocumentType documentType);
    DocumentType toEntity(DocumentTypeDTO documentTypeDTO);
}