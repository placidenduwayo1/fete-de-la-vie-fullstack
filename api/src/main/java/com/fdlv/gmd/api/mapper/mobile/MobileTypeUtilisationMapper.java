package com.fdlv.gmd.api.mapper.mobile;

import com.fdlv.gmd.api.domain.Answer;
import com.fdlv.gmd.api.domain.mobile.MobileTypeUtilisation;
import com.fdlv.gmd.api.dto.AnswerDTO;
import com.fdlv.gmd.api.dto.mobile.MobileTypeUtilisationDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MobileTypeUtilisationMapper extends EntityMapper<MobileTypeUtilisationDTO, MobileTypeUtilisation> {

}
