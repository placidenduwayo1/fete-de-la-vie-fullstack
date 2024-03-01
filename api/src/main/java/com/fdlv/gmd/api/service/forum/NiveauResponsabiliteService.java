package com.fdlv.gmd.api.service.forum;

import com.fdlv.gmd.api.dto.forum.NiveauResponsabiliteDTO;

import java.util.List;

public interface NiveauResponsabiliteService {
    List<NiveauResponsabiliteDTO> getAllResponsibilityLevel();

    List<NiveauResponsabiliteDTO> getNiveauResponsabiliteByLevel(char level);
}
