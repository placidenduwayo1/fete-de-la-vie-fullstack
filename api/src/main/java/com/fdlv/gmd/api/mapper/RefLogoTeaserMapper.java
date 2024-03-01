package com.fdlv.gmd.api.mapper;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.RefLogoTeaser;
import com.fdlv.gmd.api.dto.RefLogoTeaserDTO;

/**
 * Mapper for the entity {@link RefLogoTeaser} and its DTO {@link RefLogoTeaserDTO}.
 */
@Mapper(componentModel = "spring")
public interface RefLogoTeaserMapper extends EntityMapper<RefLogoTeaserDTO, RefLogoTeaser> {
}
