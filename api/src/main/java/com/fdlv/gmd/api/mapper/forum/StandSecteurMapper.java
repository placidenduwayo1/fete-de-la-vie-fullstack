package com.fdlv.gmd.api.mapper.forum;

import com.fdlv.gmd.api.domain.forum.StandSecteur;
import com.fdlv.gmd.api.dto.forum.StandSecteurDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StandSecteurMapper extends EntityMapper<StandSecteurDTO, StandSecteur> {
    StandSecteurDTO toDto(StandSecteur standSecteur);

    StandSecteur toEntity(StandSecteurDTO standSecteurDTO);

    List<StandSecteurDTO> toDtoList(List<StandSecteur> standSecteurs);
}
