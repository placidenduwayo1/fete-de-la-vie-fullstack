package com.fdlv.gmd.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fdlv.gmd.api.IntegrationTest;
import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.dto.QuizzDTO;
import com.fdlv.gmd.api.mapper.QuizzMapper;
import com.fdlv.gmd.api.repository.QuizzRepository;
import com.fdlv.gmd.api.web.rest.QuizzResource;

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
 * Integration tests for the {@link QuizzResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuizzResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quizzes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuizzRepository quizzRepository;

    @Autowired
    private QuizzMapper quizzMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuizzMockMvc;

    private Quizz quizz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quizz createEntity(EntityManager em) {
        Quizz quizz = new Quizz().label(DEFAULT_LABEL);
        return quizz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quizz createUpdatedEntity(EntityManager em) {
        Quizz quizz = new Quizz().label(UPDATED_LABEL);
        return quizz;
    }

    @BeforeEach
    public void initTest() {
        quizz = createEntity(em);
    }

    @Test
    @Transactional
    void createQuizz() throws Exception {
        int databaseSizeBeforeCreate = quizzRepository.findAll().size();
        // Create the Quizz
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);
        restQuizzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quizzDTO)))
            .andExpect(status().isCreated());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeCreate + 1);
        Quizz testQuizz = quizzList.get(quizzList.size() - 1);
        assertThat(testQuizz.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void createQuizzWithExistingId() throws Exception {
        // Create the Quizz with an existing ID
        quizz.setId(1L);
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);

        int databaseSizeBeforeCreate = quizzRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuizzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quizzDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = quizzRepository.findAll().size();
        // set the field null
        quizz.setLabel(null);

        // Create the Quizz, which fails.
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);

        restQuizzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quizzDTO)))
            .andExpect(status().isBadRequest());

        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuizzes() throws Exception {
        // Initialize the database
        quizzRepository.saveAndFlush(quizz);

        // Get all the quizzList
        restQuizzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quizz.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getQuizz() throws Exception {
        // Initialize the database
        quizzRepository.saveAndFlush(quizz);

        // Get the quizz
        restQuizzMockMvc
            .perform(get(ENTITY_API_URL_ID, quizz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quizz.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingQuizz() throws Exception {
        // Get the quizz
        restQuizzMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuizz() throws Exception {
        // Initialize the database
        quizzRepository.saveAndFlush(quizz);

        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();

        // Update the quizz
        Quizz updatedQuizz = quizzRepository.findById(quizz.getId()).get();
        // Disconnect from session so that the updates on updatedQuizz are not directly saved in db
        em.detach(updatedQuizz);
        updatedQuizz.label(UPDATED_LABEL);
        QuizzDTO quizzDTO = quizzMapper.toDto(updatedQuizz);

        restQuizzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quizzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quizzDTO))
            )
            .andExpect(status().isOk());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
        Quizz testQuizz = quizzList.get(quizzList.size() - 1);
        assertThat(testQuizz.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void putNonExistingQuizz() throws Exception {
        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();
        quizz.setId(count.incrementAndGet());

        // Create the Quizz
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuizzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quizzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quizzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuizz() throws Exception {
        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();
        quizz.setId(count.incrementAndGet());

        // Create the Quizz
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuizzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quizzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuizz() throws Exception {
        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();
        quizz.setId(count.incrementAndGet());

        // Create the Quizz
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuizzMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quizzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuizzWithPatch() throws Exception {
        // Initialize the database
        quizzRepository.saveAndFlush(quizz);

        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();

        // Update the quizz using partial update
        Quizz partialUpdatedQuizz = new Quizz();
        partialUpdatedQuizz.setId(quizz.getId());

        restQuizzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuizz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuizz))
            )
            .andExpect(status().isOk());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
        Quizz testQuizz = quizzList.get(quizzList.size() - 1);
        assertThat(testQuizz.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void fullUpdateQuizzWithPatch() throws Exception {
        // Initialize the database
        quizzRepository.saveAndFlush(quizz);

        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();

        // Update the quizz using partial update
        Quizz partialUpdatedQuizz = new Quizz();
        partialUpdatedQuizz.setId(quizz.getId());

        partialUpdatedQuizz.label(UPDATED_LABEL);

        restQuizzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuizz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuizz))
            )
            .andExpect(status().isOk());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
        Quizz testQuizz = quizzList.get(quizzList.size() - 1);
        assertThat(testQuizz.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void patchNonExistingQuizz() throws Exception {
        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();
        quizz.setId(count.incrementAndGet());

        // Create the Quizz
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuizzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quizzDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quizzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuizz() throws Exception {
        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();
        quizz.setId(count.incrementAndGet());

        // Create the Quizz
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuizzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quizzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuizz() throws Exception {
        int databaseSizeBeforeUpdate = quizzRepository.findAll().size();
        quizz.setId(count.incrementAndGet());

        // Create the Quizz
        QuizzDTO quizzDTO = quizzMapper.toDto(quizz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuizzMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quizzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quizz in the database
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuizz() throws Exception {
        // Initialize the database
        quizzRepository.saveAndFlush(quizz);

        int databaseSizeBeforeDelete = quizzRepository.findAll().size();

        // Delete the quizz
        restQuizzMockMvc
            .perform(delete(ENTITY_API_URL_ID, quizz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quizz> quizzList = quizzRepository.findAll();
        assertThat(quizzList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
