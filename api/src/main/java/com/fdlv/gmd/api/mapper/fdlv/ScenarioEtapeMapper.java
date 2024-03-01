package com.fdlv.gmd.api.mapper.fdlv;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.domain.fdlv.ScenarioEtape;
import com.fdlv.gmd.api.dto.fdlv.ScenarioEtapeDTO;
import com.fdlv.gmd.api.mapper.EntityMapper;
import com.fdlv.gmd.api.repository.QuizzRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


public interface ScenarioEtapeMapper extends EntityMapper<ScenarioEtapeDTO, ScenarioEtape> {


/*    @Mapping(target = "fse_id",source = "id")
    @Mapping(target = "fse_sps_id",source = "choixOrganisateur.id")
    @Mapping(target = "fse_titre",source = "titre")
    @Mapping(target = "fse_sequence",source = "sequence")
    @Mapping(target = "fse_video_id",source = "listeVideos.id")
    @Mapping(target = "fse_latitude",source = "latitude")
    @Mapping(target = "fse_longitude",source = "longitude")
    @Mapping(target = "fse_quizz_id",source = "quizz.id")
    @Mapping(target = "fse_flv_id",source = "flvId")
    @Mapping(target = "fse_defi",source = "defi")
    @Mapping(target = "fse_defi_video",source = "defiVideo")
    @Mapping(target = "fse_defi_partage",source = "defiPartage")*/
    ScenarioEtapeDTO toDto(ScenarioEtape scenarioEtape);



/*    @Mapping(source = "fse_id",target = "id")
    @Mapping(source = "fse_sps_id",target = "choixOrganisateur.id")
    @Mapping(source = "fse_titre",target = "titre")
    @Mapping(source = "fse_sequence",target = "sequence")
    @Mapping(source = "fse_video_id",target = "listeVideos.id")
    @Mapping(source = "fse_latitude",target = "latitude")
    @Mapping(source = "fse_longitude",target = "longitude")
    @Mapping(source = "fse_quizz_id",target = "quizz.id",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "fse_flv_id",target = "flvId")
    @Mapping(source = "fse_defi",target = "defi")
    @Mapping(source = "fse_defi_video",target = "defiVideo")
    @Mapping(source = "fse_defi_partage",target = "defiPartage")*/
    ScenarioEtape toEntity(ScenarioEtapeDTO scenarioEtapeDTO);



}


