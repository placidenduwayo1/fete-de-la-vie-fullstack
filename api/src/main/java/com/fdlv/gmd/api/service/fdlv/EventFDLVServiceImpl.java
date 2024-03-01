package com.fdlv.gmd.api.service.fdlv;

import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.domain.Stage;
import com.fdlv.gmd.api.domain.fdlv.Challenge;
import com.fdlv.gmd.api.dto.fdlv.EventFDLVDTO;
import com.fdlv.gmd.api.dto.fdlv.EventPostFDLVDTO;
import com.fdlv.gmd.api.dto.fdlv.ListeVideosThemeDTO;
import com.fdlv.gmd.api.dto.fdlv.ScenarioEtapeDTO;
import com.fdlv.gmd.api.mapper.fdlv.EventFDLVMapper;
import com.fdlv.gmd.api.mapper.fdlv.ListeVideosThemeMapper;
import com.fdlv.gmd.api.repository.EventRepository;
import com.fdlv.gmd.api.repository.StageRepository;
import com.fdlv.gmd.api.service.QuizzService;
import com.fdlv.gmd.api.service.impl.EventServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation de EventFDLVService
 */
@Service
@Transactional
public class EventFDLVServiceImpl implements EventFDLVService {
    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    private final QuizzService quizzService;
    private final ListeVideosService listeVideosService;
    private final ChallengeService challengeService;
    private final StageRepository stageRepository;


    private final EventFDLVMapper eventMapper;
    private final ListeVideosThemeMapper listeVideosThemeMapper;

    public EventFDLVServiceImpl(EventRepository eventRepository, QuizzService quizzService, ListeVideosService listeVideosService, ChallengeService challengeService, StageRepository stageRepository, EventFDLVMapper eventMapper, ListeVideosThemeMapper listeVideosThemeMapper) {
        this.eventRepository = eventRepository;
        this.quizzService = quizzService;
        this.listeVideosService = listeVideosService;
        this.challengeService = challengeService;
        this.stageRepository = stageRepository;
        this.eventMapper = eventMapper;
        this.listeVideosThemeMapper = listeVideosThemeMapper;
    }


    @Override
    public EventFDLVDTO save(EventPostFDLVDTO eventDTO) {
        log.debug("Request to save Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO.getEvent());
        event = eventRepository.save(event);
        for(ScenarioEtapeDTO scenarioEtapeDTO: eventDTO.getStage()){
            Stage stage = new Stage();
           if(scenarioEtapeDTO.getFse_quizz_id()!=null){
               if(quizzService.existsById(scenarioEtapeDTO.getFse_quizz_id()))
                    stage.setQuizz(quizzService.findOneEntity(scenarioEtapeDTO.getFse_quizz_id()).get());
           }

           stage.setLabel(scenarioEtapeDTO.getFse_titre());
           stage.setSequence(scenarioEtapeDTO.getFse_sequence());
            stage.setLatitude(scenarioEtapeDTO.getFse_latitude());
            stage.setLongitude(scenarioEtapeDTO.getFse_longitude());

            if(listeVideosService.existsById(scenarioEtapeDTO.getFse_video_id())) {
                ListeVideosThemeDTO lv = listeVideosService.findOne(scenarioEtapeDTO.getFse_video_id()).get();

                stage.setVideoDescription(lv.getDescription());
                stage.setVideoImageUrl(lv.getUrlImage());
                stage.setVideoUrl(lv.getUrlVideo());
                stage.setEvent(event);
                stage.setTypeMedia(lv.getTypeMedia());
                stage.setStage_defi_video(scenarioEtapeDTO.getFse_defi_video());
                stage.setStage_defi_partage(scenarioEtapeDTO.getFse_defi_partage());
                stage.setVideo(listeVideosThemeMapper.toEntity(lv));
            }
            stageRepository.save(stage);



        }
        return eventMapper.toDto(event);
    }



    @Override
    @Transactional(readOnly = true)
    public List<EventFDLVDTO> findAll() {
        //log.debug("Request to get all Events");
        return eventRepository.findAll().stream().map(eventMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventFDLVDTO> findOne(Long id) {
        //log.debug("Request to get Event : {}", id);
        return eventRepository.findById(id).map(eventMapper::toDto);
    }


}
