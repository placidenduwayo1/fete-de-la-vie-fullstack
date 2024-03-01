package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.StandDTO;

import java.util.List;
import java.util.Optional;

public interface StandService {
    StandDTO save(StandDTO standDTO);

    Optional<StandDTO> partialUpdate(StandDTO standDTO);

    List<StandDTO> findAll();

    Optional<StandDTO> findOne(Long id);

    boolean existsById(Long id);

    void delete(Long id);
}