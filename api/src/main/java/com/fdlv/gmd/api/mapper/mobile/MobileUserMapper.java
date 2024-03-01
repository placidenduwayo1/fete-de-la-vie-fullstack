package com.fdlv.gmd.api.mapper.mobile;

import org.mapstruct.Mapper;

import com.fdlv.gmd.api.domain.mobile.MobileUser;
import com.fdlv.gmd.api.dto.mobile.MobileUserDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;

/**
 * Mapper for the entity {@link MobileUser} and its DTO {@link MobileUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface MobileUserMapper extends EntityMapper<MobileUserDTO, MobileUser> {
}
