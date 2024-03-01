package com.fdlv.gmd.api.mapper.mobile;


import com.fdlv.gmd.api.domain.mobile.MobileAvisParcours;
import com.fdlv.gmd.api.dto.mobile.MobileAvisParcoursDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MobileAvisParcoursMapper extends EntityMapper<MobileAvisParcoursDTO, MobileAvisParcours> {
}
