package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.StandSecteurDTO;

import java.util.List;
import java.util.Optional;

public interface StandSecteurService {
    StandSecteurDTO save(StandSecteurDTO standSecteurDTO);

    Optional<StandSecteurDTO> partialUpdate(StandSecteurDTO standSecteurDTO);

    List<StandSecteurDTO> findAll();

    Optional<StandSecteurDTO> findOne(Long id);

    boolean existsById(Long id);

    void delete(Long id);
}