package com.fdlv.gmd.api.mapper.mobile;


import com.fdlv.gmd.api.domain.mobile.MobileQuestionFinParcours;
import com.fdlv.gmd.api.dto.mobile.MobileQuestionFinParcoursDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MobileQuestionFinParcoursMapper extends EntityMapper<MobileQuestionFinParcoursDTO, MobileQuestionFinParcours> {
}
