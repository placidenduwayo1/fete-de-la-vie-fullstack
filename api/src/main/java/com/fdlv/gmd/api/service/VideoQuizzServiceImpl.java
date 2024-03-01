package com.fdlv.gmd.api.service;

import com.fdlv.gmd.api.domain.fdlv.VideoQuizz;
import com.fdlv.gmd.api.repository.VideoQuizzRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
@Service
@Transactional
public class VideoQuizzServiceImpl implements VideoQuizzService {

    private final VideoQuizzRepository videoQuizzRepository;

    public VideoQuizzServiceImpl(VideoQuizzRepository videoQuizzRepository) {
        this.videoQuizzRepository = videoQuizzRepository;
    }

    @Override
    public List<VideoQuizz> getByIdQuizz(Long idQuizz) {
        return videoQuizzRepository.findByIdQuizz(idQuizz);
    }
}
