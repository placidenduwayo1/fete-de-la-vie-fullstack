package com.fdlv.gmd.api.mapper.fdlv;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur;
import com.fdlv.gmd.api.domain.fdlv.ListeVideos;
import com.fdlv.gmd.api.domain.fdlv.ScenarioEtape;
import com.fdlv.gmd.api.dto.fdlv.ScenarioEtapeDTO;
import com.fdlv.gmd.api.repository.QuizzRepository;
import com.fdlv.gmd.api.repository.fdlv.ChoixOrganisateurRepository;
import com.fdlv.gmd.api.repository.fdlv.ListeVideosRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScenarioEtapeMapperImpl implements ScenarioEtapeMapper{
    @Autowired
    ChoixOrganisateurRepository choixOrganisateurRepository;
    @Autowired
    QuizzRepository quizzRepository;
    @Autowired
    ListeVideosRepository listeVideosRepository;

    @Override
    public List<ScenarioEtape> toEntity(List<ScenarioEtapeDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<ScenarioEtapeDTO> toDto(List<ScenarioEtape> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void partialUpdate(ScenarioEtape entity, ScenarioEtapeDTO dto) {

        if(dto.getFse_sps_id()!=null) {
            if (choixOrganisateurRepository.findById(dto.getFse_sps_id()).isPresent()) {
              entity.setChoixOrganisateur(choixOrganisateurRepository.findById(dto.getFse_sps_id()).get());
            }
        }

        if(dto.getFse_quizz_id()!=null) {
            if (quizzRepository.findById(dto.getFse_quizz_id()).isPresent()) {
                entity.setQuizz(quizzRepository.findById(dto.getFse_quizz_id()).get());
            }
        }
        if(dto.getFse_video_id()!=null){
            if (listeVideosRepository.findById(dto.getFse_video_id()).isPresent()) {
                entity.setListeVideos(listeVideosRepository.findById(dto.getFse_video_id()).get());
            }
        }
       if(dto.getFse_id()!=null){
           entity.setId(dto.getFse_video_id());
       }
       if(dto.getFse_sequence()!=null){
           entity.setSequence(dto.getFse_sequence());
       }
       if(dto.getFse_titre()!=null){
           entity.setTitre(dto.getFse_titre());
       }
       if(dto.getFse_latitude()!=null){
           entity.setLatitude(dto.getFse_latitude());
       }
       if(dto.getFse_longitude()!=null){
           entity.setLongitude(dto.getFse_longitude());
       }
       if(dto.getFse_defi()!=null){
           entity.setDefi(dto.getFse_defi());
       }
       if(dto.getFse_flv_id()!=null){
           entity.setFlvId(dto.getFse_flv_id());
       }
       if(dto.getFse_defi_video()!=null){
           entity.setDefiVideo(dto.getFse_defi_video());
       }
       if(dto.getFse_defi_partage()!=null){
           entity.setDefiPartage(dto.getFse_defi_partage());
       }
    }

    @Override
    public ScenarioEtapeDTO toDto(ScenarioEtape scenarioEtape) {
        ScenarioEtapeDTO seDTO = new ScenarioEtapeDTO();
        seDTO.setFse_id(scenarioEtape.getId());
        if(scenarioEtape.getChoixOrganisateur()!=null)
            seDTO.setFse_sps_id(scenarioEtape.getChoixOrganisateur().getId());
        seDTO.setFse_sequence(scenarioEtape.getSequence());
        seDTO.setFse_titre(scenarioEtape.getTitre());
        if(scenarioEtape.getListeVideos()!=null)
            seDTO.setFse_video_id(scenarioEtape.getListeVideos().getId());
        seDTO.setFse_latitude(scenarioEtape.getLatitude());
        seDTO.setFse_longitude(scenarioEtape.getLongitude());
        if(scenarioEtape.getQuizz()!=null)
            seDTO.setFse_quizz_id(scenarioEtape.getQuizz().getId());
        seDTO.setFse_defi(scenarioEtape.getDefi());
        seDTO.setFse_flv_id(scenarioEtape.getFlvId());
        seDTO.setFse_defi_video(scenarioEtape.getDefiVideo());
        seDTO.setFse_defi_partage(scenarioEtape.getDefiPartage());

        return seDTO;
    }

    @Override
    public ScenarioEtape toEntity(ScenarioEtapeDTO scenarioEtapeDTO) {

        ChoixOrganisateur co = null;
        if(scenarioEtapeDTO.getFse_sps_id()!=null) {
            if (choixOrganisateurRepository.findById(scenarioEtapeDTO.getFse_sps_id()).isPresent()) {
                co = choixOrganisateurRepository.findById(scenarioEtapeDTO.getFse_sps_id()).get();
            }
        }
        Quizz qu = null;
        if(scenarioEtapeDTO.getFse_quizz_id()!=null) {
            if (quizzRepository.findById(scenarioEtapeDTO.getFse_quizz_id()).isPresent()) {
                qu = quizzRepository.findById(scenarioEtapeDTO.getFse_quizz_id()).get();
            }
        }
        ListeVideos lv = null;
        if(scenarioEtapeDTO.getFse_video_id()!=null){
            if (listeVideosRepository.findById(scenarioEtapeDTO.getFse_video_id()).isPresent()) {
                lv = listeVideosRepository.findById(scenarioEtapeDTO.getFse_video_id()).get();
            }
        }
        ScenarioEtape se = new ScenarioEtape();
        se.setId(scenarioEtapeDTO.getFse_id());
        se.setChoixOrganisateur(co);
        se.setSequence(scenarioEtapeDTO.getFse_sequence());
        se.setTitre(scenarioEtapeDTO.getFse_titre());
        se.setListeVideos(lv);
        se.setLatitude(scenarioEtapeDTO.getFse_latitude());
        se.setLongitude(scenarioEtapeDTO.getFse_longitude());
        se.setQuizz(qu);
        se.setDefi(scenarioEtapeDTO.getFse_defi());
        se.setFlvId(scenarioEtapeDTO.getFse_flv_id());
        se.setDefiVideo(scenarioEtapeDTO.getFse_defi_video());
        se.setDefiPartage(scenarioEtapeDTO.getFse_defi_partage());

        return se;
    }
}
