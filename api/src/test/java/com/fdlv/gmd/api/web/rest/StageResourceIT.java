package com.fdlv.gmd.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fdlv.gmd.api.IntegrationTest;
import com.fdlv.gmd.api.domain.Stage;
import com.fdlv.gmd.api.dto.StageDTO;
import com.fdlv.gmd.api.mapper.StageMapper;
import com.fdlv.gmd.api.repository.StageRepository;
import com.fdlv.gmd.api.web.rest.StageResource;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StageResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_MEDIA = ".mp4";
    private static final String UPDATED_TYPE_MEDIA = ".mp3";

    private static final String ENTITY_API_URL = "/api/stages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private StageMapper stageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStageMockMvc;

    private Stage stage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stage createEntity(EntityManager em) {
        Stage stage = new Stage()
            .label(DEFAULT_LABEL)
            .sequence(DEFAULT_SEQUENCE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .videoUrl(DEFAULT_VIDEO_URL)
            .typeMedia(DEFAULT_TYPE_MEDIA);
        return stage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stage createUpdatedEntity(EntityManager em) {
        Stage stage = new Stage()
            .label(UPDATED_LABEL)
            .sequence(UPDATED_SEQUENCE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .videoUrl(UPDATED_VIDEO_URL)
            .typeMedia(UPDATED_TYPE_MEDIA);
        return stage;
    }

    @BeforeEach
    public void initTest() {
        stage = createEntity(em);
    }

    @Test
    @Transactional
    void createStage() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();
        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);
        restStageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeCreate + 1);
        Stage testStage = stageList.get(stageList.size() - 1);
        assertThat(testStage.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testStage.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testStage.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testStage.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testStage.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testStage.getTypeMedia()).isEqualTo(DEFAULT_TYPE_MEDIA);
    }

    @Test
    @Transactional
    void createStageWithExistingId() throws Exception {
        // Create the Stage with an existing ID
        stage.setId(1L);
        StageDTO stageDTO = stageMapper.toDto(stage);

        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setLabel(null);

        // Create the Stage, which fails.
        StageDTO stageDTO = stageMapper.toDto(stage);

        restStageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isBadRequest());

        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setSequence(null);

        // Create the Stage, which fails.
        StageDTO stageDTO = stageMapper.toDto(stage);

        restStageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isBadRequest());

        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setLatitude(null);

        // Create the Stage, which fails.
        StageDTO stageDTO = stageMapper.toDto(stage);

        restStageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isBadRequest());

        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setLongitude(null);

        // Create the Stage, which fails.
        StageDTO stageDTO = stageMapper.toDto(stage);

        restStageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isBadRequest());

        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStages() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get all the stageList
        restStageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].typeMEdia").value(hasItem(DEFAULT_TYPE_MEDIA)));
    }

    @Test
    @Transactional
    void getStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get the stage
        restStageMockMvc
            .perform(get(ENTITY_API_URL_ID, stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stage.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.typeMedia").value(DEFAULT_TYPE_MEDIA));
    }

    @Test
    @Transactional
    void getNonExistingStage() throws Exception {
        // Get the stage
        restStageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage
        Stage updatedStage = stageRepository.findById(stage.getId()).get();
        // Disconnect from session so that the updates on updatedStage are not directly saved in db
        em.detach(updatedStage);
        updatedStage
            .label(UPDATED_LABEL)
            .sequence(UPDATED_SEQUENCE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .videoUrl(UPDATED_VIDEO_URL)
            .typeMedia(UPDATED_TYPE_MEDIA);
        StageDTO stageDTO = stageMapper.toDto(updatedStage);

        restStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stageList.get(stageList.size() - 1);
        assertThat(testStage.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testStage.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testStage.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStage.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStage.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testStage.getTypeMedia()).isEqualTo(UPDATED_TYPE_MEDIA);
    }

    @Test
    @Transactional
    void putNonExistingStage() throws Exception {
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();
        stage.setId(count.incrementAndGet());

        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStage() throws Exception {
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();
        stage.setId(count.incrementAndGet());

        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStage() throws Exception {
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();
        stage.setId(count.incrementAndGet());

        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStageWithPatch() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage using partial update
        Stage partialUpdatedStage = new Stage();
        partialUpdatedStage.setId(stage.getId());

        partialUpdatedStage.label(UPDATED_LABEL).sequence(UPDATED_SEQUENCE);

        restStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStage))
            )
            .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stageList.get(stageList.size() - 1);
        assertThat(testStage.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testStage.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testStage.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testStage.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testStage.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testStage.getTypeMedia()).isEqualTo(DEFAULT_TYPE_MEDIA);
    }

    @Test
    @Transactional
    void fullUpdateStageWithPatch() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage using partial update
        Stage partialUpdatedStage = new Stage();
        partialUpdatedStage.setId(stage.getId());

        partialUpdatedStage
            .label(UPDATED_LABEL)
            .sequence(UPDATED_SEQUENCE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .videoUrl(UPDATED_VIDEO_URL)
            .typeMedia(UPDATED_TYPE_MEDIA);

        restStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStage))
            )
            .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stageList.get(stageList.size() - 1);
        assertThat(testStage.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testStage.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testStage.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStage.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStage.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testStage.getTypeMedia()).isEqualTo(UPDATED_TYPE_MEDIA);
    }

    @Test
    @Transactional
    void patchNonExistingStage() throws Exception {
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();
        stage.setId(count.incrementAndGet());

        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStage() throws Exception {
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();
        stage.setId(count.incrementAndGet());

        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStage() throws Exception {
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();
        stage.setId(count.incrementAndGet());

        // Create the Stage
        StageDTO stageDTO = stageMapper.toDto(stage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stage in the database
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        int databaseSizeBeforeDelete = stageRepository.findAll().size();

        // Delete the stage
        restStageMockMvc
            .perform(delete(ENTITY_API_URL_ID, stage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stage> stageList = stageRepository.findAll();
        assertThat(stageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
