package com.fdlv.gmd.api.mapper.fdlv;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.fdlv.Temoignage;
import com.fdlv.gmd.api.dto.fdlv.TemoignageDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;

/**
 * Mapper for the entity {@link Temoignage} and its DTO {@link TemoignageDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemoignageMapper extends EntityMapper<TemoignageDTO, Temoignage> {
}
