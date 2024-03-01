package com.fdlv.gmd.api.service;

import com.fdlv.gmd.api.domain.fdlv.VideoQuizz;

import java.util.List;

public interface VideoQuizzService {
    List<VideoQuizz> getByIdQuizz(Long idQuizz);
}
