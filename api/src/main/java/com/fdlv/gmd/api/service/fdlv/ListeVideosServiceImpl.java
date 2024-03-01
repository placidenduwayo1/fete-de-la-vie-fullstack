package com.fdlv.gmd.api.service.fdlv;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.domain.fdlv.ListeVideos;
import com.fdlv.gmd.api.dto.fdlv.FdlvVideoDTO;
import com.fdlv.gmd.api.dto.fdlv.ListeVideosThemeDTO;
import com.fdlv.gmd.api.mapper.fdlv.ListeVideosThemeMapper;
import com.fdlv.gmd.api.repository.QuizzRepository;
import com.fdlv.gmd.api.repository.fdlv.ListeVideosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ListeVideos}.
 */
@Service
@Transactional
public class ListeVideosServiceImpl implements ListeVideosService {

    private final Logger log = LoggerFactory.getLogger(ListeVideosServiceImpl.class);

    private final ListeVideosRepository listeVideosRepository;

    private final ListeVideosThemeMapper listeVideosMapper;

    private final QuizzRepository quizzRepository;

    public ListeVideosServiceImpl(ListeVideosRepository listeVideosRepository, ListeVideosThemeMapper listeVideosMapper, QuizzRepository quizzRepository) {
        this.listeVideosRepository = listeVideosRepository;
        this.listeVideosMapper = listeVideosMapper;
        this.quizzRepository = quizzRepository;
    }

    @Override
    public ListeVideosThemeDTO save(ListeVideosThemeDTO listeVideosDTO) {
        log.debug("Request to save ListeVideos : {}", listeVideosDTO);
        ListeVideos listeVideos = listeVideosMapper.toEntity(listeVideosDTO);
        listeVideos = listeVideosRepository.save(listeVideos);
        return listeVideosMapper.toDto(listeVideos);
    }

    @Override
    public Optional<ListeVideosThemeDTO> partialUpdate(ListeVideosThemeDTO listeVideosDTO) {
        log.debug("Request to partially update listeVideos : {}", listeVideosDTO);

        return listeVideosRepository
            .findById(listeVideosDTO.getId())
            .map(
                existingListeVideos -> {
                    listeVideosMapper.partialUpdate(existingListeVideos, listeVideosDTO);
                    return existingListeVideos;
                }
            )
            .map(listeVideosRepository::save)
            .map(listeVideosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListeVideosThemeDTO> findAll() {
        log.debug("Request to get all ListeVideoss");
        return listeVideosRepository.findAll().stream().map(listeVideosMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListeVideosThemeDTO> findOne(Long id) {
        log.debug("Request to get ListeVideos : {}", id);
        return listeVideosRepository.findById(id).map(listeVideosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return listeVideosRepository.existsById(id);
    }

    @Override
    public void hardDelete(Long id) {
        log.debug("Request to hard delete ListeVideos : {}", id);
        listeVideosRepository.deleteById(id);
    }

    @Override
    public void softDelete(Long id) {
        log.debug("Request to soft delete ListeVideos : {}", id);
        ListeVideos listeVideoADesactiver = listeVideosRepository.getOne(id);
        listeVideoADesactiver.setActive(false);
        listeVideoADesactiver.setFlvMediaValide("9");
        partialUpdate(listeVideosMapper.toDto(listeVideoADesactiver));
    }

//    @Override
//    public boolean isVideoUsed(Long id) {
//        log.debug("Request to check if this video id is used before deleting ListeVideos : {}", id);
//        // Boolean result = false;
//        ListeVideos video = listeVideosRepository.getOne(id);
//        return  false || video.getActive();
//        // If Samir asks, we can also add checks against Stage, fdlvScenarioEtape, videoquizz, fdlvScenarioEtape
//    }

    @Override
    public ListeVideosThemeDTO updateVideoQuizz (Long videoID, List<Long> quizzVideoIDlist) {
        if(videoID == null || quizzVideoIDlist == null){
            return null;
        }
        
        ListeVideos listeVideos = listeVideosRepository.findById(videoID).orElseThrow(RuntimeException::new);
        List<Quizz> listQuizz = new ArrayList<>();
        for(Long id : quizzVideoIDlist) {
            Quizz quizz = this.quizzRepository.findById(id).orElseThrow(RuntimeException::new);
            listQuizz.add(quizz);
        }
        listeVideos.setQuizzs(listQuizz);
        listeVideos = listeVideosRepository.save(listeVideos);
        return listeVideosMapper.toDto(listeVideos);
    }

    @Transactional(readOnly = true)
    public Optional<ListeVideosThemeDTO> findByUrl(String url) {
        log.debug("Request to get ListeVideos by URL: {}", url);
        return listeVideosRepository.findByUrlVideo(url)
                .map(listeVideosMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Long getMaxMediaId() {
        log.debug("Request to get maximum media ID");
        return listeVideosRepository.findMaxId();
    }

    @Override
    public List<FdlvVideoDTO> findDisplayable() {
        log.debug("Request to get displayable ListeVideos");
        return listeVideosRepository.findDisplayableDTO().stream().map(listeVideosMapper::toDto).map(FdlvVideoDTO::new).collect(Collectors.toList());

    }
}
