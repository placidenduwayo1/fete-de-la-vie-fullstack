package com.fdlv.gmd.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fdlv.gmd.api.IntegrationTest;
import com.fdlv.gmd.api.domain.Answer;
import com.fdlv.gmd.api.dto.AnswerDTO;
import com.fdlv.gmd.api.mapper.AnswerMapper;
import com.fdlv.gmd.api.repository.AnswerRepository;
import com.fdlv.gmd.api.web.rest.AnswerResource;

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
 * Integration tests for the {@link AnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnswerResourceIT {

    private static final String DEFAULT_REPONSE = "AAAAAAAAAA";
    private static final String UPDATED_REPONSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CORRECT = false;
    private static final Boolean UPDATED_IS_CORRECT = true;

    private static final String ENTITY_API_URL = "/api/answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnswerMockMvc;

    private Answer answer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createEntity(EntityManager em) {
        Answer answer = new Answer().reponse(DEFAULT_REPONSE).isCorrect(DEFAULT_IS_CORRECT);
        return answer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createUpdatedEntity(EntityManager em) {
        Answer answer = new Answer().reponse(UPDATED_REPONSE).isCorrect(UPDATED_IS_CORRECT);
        return answer;
    }

    @BeforeEach
    public void initTest() {
        answer = createEntity(em);
    }

    @Test
    @Transactional
    void createAnswer() throws Exception {
        int databaseSizeBeforeCreate = answerRepository.findAll().size();
        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);
        restAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isCreated());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeCreate + 1);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.getReponse()).isEqualTo(DEFAULT_REPONSE);
        assertThat(testAnswer.getIsCorrect()).isEqualTo(DEFAULT_IS_CORRECT);
    }

    @Test
    @Transactional
    void createAnswerWithExistingId() throws Exception {
        // Create the Answer with an existing ID
        answer.setId(1L);
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        int databaseSizeBeforeCreate = answerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkReponseIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerRepository.findAll().size();
        // set the field null
        answer.setReponse(null);

        // Create the Answer, which fails.
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        restAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsCorrectIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerRepository.findAll().size();
        // set the field null
        answer.setIsCorrect(null);

        // Create the Answer, which fails.
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        restAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnswers() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList
        restAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answer.getId().intValue())))
            .andExpect(jsonPath("$.[*].reponse").value(hasItem(DEFAULT_REPONSE)))
            .andExpect(jsonPath("$.[*].isCorrect").value(hasItem(DEFAULT_IS_CORRECT.booleanValue())));
    }

    @Test
    @Transactional
    void getAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get the answer
        restAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, answer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(answer.getId().intValue()))
            .andExpect(jsonPath("$.reponse").value(DEFAULT_REPONSE))
            .andExpect(jsonPath("$.isCorrect").value(DEFAULT_IS_CORRECT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnswer() throws Exception {
        // Get the answer
        restAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Update the answer
        Answer updatedAnswer = answerRepository.findById(answer.getId()).get();
        // Disconnect from session so that the updates on updatedAnswer are not directly saved in db
        em.detach(updatedAnswer);
        updatedAnswer.reponse(UPDATED_REPONSE).isCorrect(UPDATED_IS_CORRECT);
        AnswerDTO answerDTO = answerMapper.toDto(updatedAnswer);

        restAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, answerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(answerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.getReponse()).isEqualTo(UPDATED_REPONSE);
        assertThat(testAnswer.getIsCorrect()).isEqualTo(UPDATED_IS_CORRECT);
    }

    @Test
    @Transactional
    void putNonExistingAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();
        answer.setId(count.incrementAndGet());

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, answerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(answerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();
        answer.setId(count.incrementAndGet());

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(answerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();
        answer.setId(count.incrementAndGet());

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnswerWithPatch() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Update the answer using partial update
        Answer partialUpdatedAnswer = new Answer();
        partialUpdatedAnswer.setId(answer.getId());

        partialUpdatedAnswer.reponse(UPDATED_REPONSE);

        restAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnswer))
            )
            .andExpect(status().isOk());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.getReponse()).isEqualTo(UPDATED_REPONSE);
        assertThat(testAnswer.getIsCorrect()).isEqualTo(DEFAULT_IS_CORRECT);
    }

    @Test
    @Transactional
    void fullUpdateAnswerWithPatch() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Update the answer using partial update
        Answer partialUpdatedAnswer = new Answer();
        partialUpdatedAnswer.setId(answer.getId());

        partialUpdatedAnswer.reponse(UPDATED_REPONSE).isCorrect(UPDATED_IS_CORRECT);

        restAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnswer))
            )
            .andExpect(status().isOk());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.getReponse()).isEqualTo(UPDATED_REPONSE);
        assertThat(testAnswer.getIsCorrect()).isEqualTo(UPDATED_IS_CORRECT);
    }

    @Test
    @Transactional
    void patchNonExistingAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();
        answer.setId(count.incrementAndGet());

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, answerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(answerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();
        answer.setId(count.incrementAndGet());

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(answerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();
        answer.setId(count.incrementAndGet());

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(answerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeDelete = answerRepository.findAll().size();

        // Delete the answer
        restAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, answer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
