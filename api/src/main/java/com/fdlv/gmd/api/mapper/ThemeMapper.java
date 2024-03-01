package com.fdlv.gmd.api.mapper;

import org.mapstruct.*;

import com.fdlv.gmd.api.domain.*;
import com.fdlv.gmd.api.dto.ThemeDTO;

/**
 * Mapper for the entity {@link Theme} and its DTO {@link ThemeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThemeMapper extends EntityMapper<ThemeDTO, Theme> {
    @Named("label")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    ThemeDTO toDtoLabel(Theme theme);
}
