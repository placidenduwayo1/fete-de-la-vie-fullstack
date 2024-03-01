package com.fdlv.gmd.api.service.forum.impl;

import com.fdlv.gmd.api.domain.forum.NiveauResponsabilite;
import com.fdlv.gmd.api.dto.forum.NiveauResponsabiliteDTO;
import com.fdlv.gmd.api.mapper.forum.NiveauResponsabiliteMapper;
import com.fdlv.gmd.api.repository.forum.NiveauResponsabiliteRepository;
import com.fdlv.gmd.api.service.forum.NiveauResponsabiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NiveauResponsabiliteServiceImpl implements NiveauResponsabiliteService {
    private final NiveauResponsabiliteRepository repository;
    private final NiveauResponsabiliteMapper mapper;
    @Override
    public List<NiveauResponsabiliteDTO> getAllResponsibilityLevel() {
        return mapping(repository.findAll());
    }

    @Override
    public List<NiveauResponsabiliteDTO> getNiveauResponsabiliteByLevel(char level) {
       return mapping(repository.findByResponsibilityLevel(level));

    }
    private List<NiveauResponsabiliteDTO> mapping(List<NiveauResponsabilite> niveauResponsabilites){
        return niveauResponsabilites.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
