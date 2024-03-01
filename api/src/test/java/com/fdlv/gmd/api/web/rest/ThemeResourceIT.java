package com.fdlv.gmd.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fdlv.gmd.api.IntegrationTest;
import com.fdlv.gmd.api.domain.Theme;
import com.fdlv.gmd.api.dto.ThemeDTO;
import com.fdlv.gmd.api.mapper.ThemeMapper;
import com.fdlv.gmd.api.repository.ThemeRepository;
import com.fdlv.gmd.api.web.rest.ThemeResource;

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
 * Integration tests for the {@link ThemeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThemeResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/themes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemeMockMvc;

    private Theme theme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Theme createEntity(EntityManager em) {
        Theme theme = new Theme().label(DEFAULT_LABEL);
        return theme;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Theme createUpdatedEntity(EntityManager em) {
        Theme theme = new Theme().label(UPDATED_LABEL);
        return theme;
    }

    @BeforeEach
    public void initTest() {
        theme = createEntity(em);
    }

    @Test
    @Transactional
    void createTheme() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();
        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);
        restThemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isCreated());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate + 1);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    void createThemeWithExistingId() throws Exception {
        // Create the Theme with an existing ID
        theme.setId(1L);
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        int databaseSizeBeforeCreate = themeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeRepository.findAll().size();
        // set the field null
        theme.setLabel(null);

        // Create the Theme, which fails.
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        restThemeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isBadRequest());

        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThemes() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList
        restThemeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theme.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get the theme
        restThemeMockMvc
            .perform(get(ENTITY_API_URL_ID, theme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(theme.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingTheme() throws Exception {
        // Get the theme
        restThemeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Update the theme
        Theme updatedTheme = themeRepository.findById(theme.getId()).get();
        // Disconnect from session so that the updates on updatedTheme are not directly saved in db
        em.detach(updatedTheme);
        updatedTheme.label(UPDATED_LABEL);
        ThemeDTO themeDTO = themeMapper.toDto(updatedTheme);

        restThemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, themeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(themeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void putNonExistingTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();
        theme.setId(count.incrementAndGet());

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, themeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(themeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();
        theme.setId(count.incrementAndGet());

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(themeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();
        theme.setId(count.incrementAndGet());

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThemeWithPatch() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Update the theme using partial update
        Theme partialUpdatedTheme = new Theme();
        partialUpdatedTheme.setId(theme.getId());

        partialUpdatedTheme.label(UPDATED_LABEL);

        restThemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTheme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTheme))
            )
            .andExpect(status().isOk());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void fullUpdateThemeWithPatch() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Update the theme using partial update
        Theme partialUpdatedTheme = new Theme();
        partialUpdatedTheme.setId(theme.getId());

        partialUpdatedTheme.label(UPDATED_LABEL);

        restThemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTheme.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTheme))
            )
            .andExpect(status().isOk());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    void patchNonExistingTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();
        theme.setId(count.incrementAndGet());

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, themeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(themeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();
        theme.setId(count.incrementAndGet());

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(themeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();
        theme.setId(count.incrementAndGet());

        // Create the Theme
        ThemeDTO themeDTO = themeMapper.toDto(theme);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThemeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(themeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeDelete = themeRepository.findAll().size();

        // Delete the theme
        restThemeMockMvc
            .perform(delete(ENTITY_API_URL_ID, theme.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
