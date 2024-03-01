package com.fdlv.gmd.api.mapper.fdlv;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.fdlv.gmd.api.domain.fdlv.FdlvUser;
import com.fdlv.gmd.api.dto.fdlv.FdlvUserDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;

/**
 * Mapper for the entity {@link FdlvUser} and its DTO {@link FdlvUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface FdlvUserMapper extends EntityMapper<FdlvUserDTO, FdlvUser> {
}
