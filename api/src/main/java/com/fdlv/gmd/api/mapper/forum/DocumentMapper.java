package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.Document;
import com.fdlv.gmd.api.dto.forum.DocumentDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { StructureMapper.class, DocumentTypeMapper.class, ForumMapper.class, ActeurMapper.class })
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentDTO toDto(Document document);
    Document toEntity(DocumentDTO documentDTO);
}