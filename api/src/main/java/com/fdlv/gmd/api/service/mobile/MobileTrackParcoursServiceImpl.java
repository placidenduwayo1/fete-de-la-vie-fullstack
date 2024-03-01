package com.fdlv.gmd.api.service.mobile;

import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.domain.Question;
import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.domain.Stage;
import com.fdlv.gmd.api.domain.mobile.MobileTrackParcours;
import com.fdlv.gmd.api.dto.mobile.MobileTrackParcoursDTO;
import com.fdlv.gmd.api.dto.mobile.MobileTrackParcoursFlatDTO;
import com.fdlv.gmd.api.mapper.mobile.MobileTrackParcoursMapper;
import com.fdlv.gmd.api.repository.EventRepository;
import com.fdlv.gmd.api.repository.QuestionRepository;
import com.fdlv.gmd.api.repository.QuizzRepository;
import com.fdlv.gmd.api.repository.StageRepository;
import com.fdlv.gmd.api.repository.mobile.MobileTrackParcoursRepository;
import javassist.NotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@Transactional
public class MobileTrackParcoursServiceImpl implements MobileTrackParcousService {

    private final Logger log = LoggerFactory.getLogger(MobileTrackParcoursServiceImpl.class);

    private final MobileTrackParcoursMapper mobileTrackParcoursMapper;
    private final MobileTrackParcoursRepository mobileTrackParcoursRepository;
    private final EventRepository eventRepository;
    private final StageRepository stageRepository;
    private final QuizzRepository quizzRepository;
    private final QuestionRepository questionRepository;


    public MobileTrackParcoursServiceImpl(MobileTrackParcoursMapper mobileTrackParcoursMapper,
                                          MobileTrackParcoursRepository mobileTrackParcoursRepository,
                                          EventRepository eventRepository,
                                          StageRepository stageRepository,
                                          QuizzRepository quizzRepository,
                                          QuestionRepository questionRepository
    ) {
        this.mobileTrackParcoursMapper = mobileTrackParcoursMapper;
        this.mobileTrackParcoursRepository = mobileTrackParcoursRepository;
        this.eventRepository = eventRepository;
        this.stageRepository = stageRepository;
        this.quizzRepository = quizzRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public MobileTrackParcoursDTO save(MobileTrackParcoursDTO mobileTrackParcoursDTO) {
        log.debug("Request to save MobileTrackParcours : {}", mobileTrackParcoursDTO);
        MobileTrackParcours mobileTrackParcours = mobileTrackParcoursMapper.toEntity(mobileTrackParcoursDTO);
        mobileTrackParcours = mobileTrackParcoursRepository.save(mobileTrackParcours);
        return mobileTrackParcoursMapper.toDto(mobileTrackParcours);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return mobileTrackParcoursRepository.existsById(id);
    }

    @Override
    public void findAllCSV(Writer writer) {
        log.debug("Request to get all mobile track parcours in a csv file");

        List<MobileTrackParcours> mobileTrackParcours = mobileTrackParcoursRepository.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(
                    "ID",
                    "MSP ID",
                    "Event",
                    "Stage ID",
                    "Defi",
                    "Defi Accepte",
                    "Defi Partagé",
                    "Quizz ID",
                    "Question ID",
                    "Reponse",
                    "Date Action"
            );
            for (MobileTrackParcours mtp : mobileTrackParcours) {
                if (mtp.getEvent().getId() == null)
                    mtp.setEvent(new Event());
                if (mtp.getStage().getId() == null)
                    mtp.setStage(new Stage());
                if (mtp.getQuizz().getId() == null)
                    mtp.setQuizz(new Quizz());
                if (mtp.getQuestion().getId() == null)
                    mtp.setQuestion(new Question());

                csvPrinter.printRecord(
                        mtp.getId(),
                        mtp.getMobileStatParcours().getId(),
                        eventRepository.findById(mtp.getEvent().getId()).orElseGet(Event::new).getLabel(),
                        stageRepository.findById(mtp.getStage().getId()).orElseGet(Stage::new).getLabel(),
                        mtp.isDefiPartage(),
                        quizzRepository.findById(mtp.getQuizz().getId()).orElseGet(Quizz::new).getLabel(),
                        questionRepository.findById(mtp.getQuestion().getId()).orElseGet(Question::new).getLabel(),
                        mtp.getReponseOk(),
                        mtp.getDateAction()
                );
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }

    @Override
    public MobileTrackParcours getMobileTrackParcoursById(Long id) {
        return mobileTrackParcoursRepository.findById(id).orElse(null);
    }

    @Override
    public MobileTrackParcours createMobileTrackParcours(MobileTrackParcours mobileTrackParcours) {
        return mobileTrackParcoursRepository.save(mobileTrackParcours);
    }

    @Override
    public Long save(MobileTrackParcoursFlatDTO mobileTrackParcoursDTO) throws NotFoundException {
        log.debug("Request to save MobileTrackParcours : {}", mobileTrackParcoursDTO);
        MobileTrackParcours mobileTrackParcours;

        if (mobileTrackParcoursDTO.getId() != null) {
            mobileTrackParcours = mobileTrackParcoursRepository.findById(mobileTrackParcoursDTO.getId()).orElse(null);
            if (mobileTrackParcours == null) {
                throw new NotFoundException("MobileTrackParcours not found");
            }
            // Update the existing entity with flat DTO data
            mobileTrackParcoursMapper.updateEntityFromFlatDTO(mobileTrackParcoursDTO, mobileTrackParcours);
        } else {
            // Create a new entity from flat DTO
            mobileTrackParcours = mobileTrackParcoursMapper.toEntityFromFlatDTO(mobileTrackParcoursDTO);
        }

        mobileTrackParcours = mobileTrackParcoursRepository.save(mobileTrackParcours);
        return mobileTrackParcours.getId();
    }

    @Override
    public MobileTrackParcours updateMobileTrackParcours(MobileTrackParcours mobileTrackParcours) {
        return mobileTrackParcoursRepository.save(mobileTrackParcours);
    }

    @Override
    public void deleteMobileTrackParcours(Long id) {
        mobileTrackParcoursRepository.deleteById(id);
    }

    @Override
    public boolean existsMobileTrackParcours(Long id) {
        return mobileTrackParcoursRepository.existsById(id);
    }

    @Override
    public List<MobileTrackParcours> getAllMobileTrackParcours() {
        return mobileTrackParcoursRepository.findAll();
    }

    @Override
    public void share(Long id)  {
        mobileTrackParcoursRepository.share(id);
    }

    @Override
    public void acceptDefi(Long id)  {
        mobileTrackParcoursRepository.acceptDefi(id);
    }

    @Override
    public void addQuizz(Long id, Long idQuizz) {
        mobileTrackParcoursRepository.addQuizz(id,idQuizz);
    }
}
