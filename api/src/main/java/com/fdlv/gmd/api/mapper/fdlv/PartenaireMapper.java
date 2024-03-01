package com.fdlv.gmd.api.mapper.fdlv;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.fdlv.Partenaire;
import com.fdlv.gmd.api.dto.fdlv.PartenaireDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;

/**
 * Mapper for the entity {@link Partenaire} and its DTO {@link PartenaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartenaireMapper extends EntityMapper<PartenaireDTO,Partenaire>{
    
    
}
