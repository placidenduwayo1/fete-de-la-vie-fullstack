package com.fdlv.gmd.api.mapper.fdlv;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.fdlv.InfoPageWeb;
import com.fdlv.gmd.api.dto.fdlv.InfoPageWebDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;

/**
 * Mapper for the entity {@link InfoPageWeb} and its DTO {@link InfoPageWebDTO}.
 */
@Mapper(componentModel = "spring")
public interface InfoPageWebMapper extends EntityMapper<InfoPageWebDTO, InfoPageWeb> {
}
