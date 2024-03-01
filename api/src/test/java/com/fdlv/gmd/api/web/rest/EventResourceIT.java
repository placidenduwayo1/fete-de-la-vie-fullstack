package com.fdlv.gmd.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fdlv.gmd.api.IntegrationTest;
import com.fdlv.gmd.api.domain.Event;
import com.fdlv.gmd.api.dto.EventDTO;
import com.fdlv.gmd.api.mapper.EventMapper;
import com.fdlv.gmd.api.repository.EventRepository;
import com.fdlv.gmd.api.web.rest.EventResource;

import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_LOGO_URL = "AAAAAAAAAA";
    private static final String UPDATED_CITY_LOGO_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_OTHER_EVENT = false;
    private static final Boolean UPDATED_OTHER_EVENT = true;

    private static final String DEFAULT_USEFUL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_USEFUL_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_TEASER_URL = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TEASER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_CODE_PARCOURS = "1234";
    private static final String UPDATED_EVENT_CODE_PARCOURS = "1235";

    private static final Boolean DEFAULT_EVT_DEMO = false;
    private static final Boolean UPDATED_EVT_DEMO = true;

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventMockMvc;

    private Event event;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .zipCode(DEFAULT_ZIP_CODE)
            .city(DEFAULT_CITY)
            .cityLogoUrl(DEFAULT_CITY_LOGO_URL)
            .startAt(DEFAULT_START_AT)
            .endAt(DEFAULT_END_AT)
            .otherEvent(DEFAULT_OTHER_EVENT)
            .usefulInformation(DEFAULT_USEFUL_INFORMATION)
            .eventTeaserUrl(DEFAULT_EVENT_TEASER_URL)
            .codeParcours(DEFAULT_EVENT_CODE_PARCOURS)
            .evtDemo(DEFAULT_EVT_DEMO);
        return event;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity(EntityManager em) {
        Event event = new Event()
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .cityLogoUrl(UPDATED_CITY_LOGO_URL)
            .startAt(UPDATED_START_AT)
            .endAt(UPDATED_END_AT)
            .otherEvent(UPDATED_OTHER_EVENT)
            .usefulInformation(UPDATED_USEFUL_INFORMATION)
            .eventTeaserUrl(UPDATED_EVENT_TEASER_URL)
            .codeParcours(UPDATED_EVENT_CODE_PARCOURS)
            .evtDemo(UPDATED_EVT_DEMO);
        return event;
    }

    @BeforeEach
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();
        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEvent.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testEvent.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEvent.getCityLogoUrl()).isEqualTo(DEFAULT_CITY_LOGO_URL);
        assertThat(testEvent.getStartAt()).isEqualTo(DEFAULT_START_AT);
        assertThat(testEvent.getEndAt()).isEqualTo(DEFAULT_END_AT);
        assertThat(testEvent.isOtherEvent()).isEqualTo(DEFAULT_OTHER_EVENT);
        assertThat(testEvent.getUsefulInformation()).isEqualTo(DEFAULT_USEFUL_INFORMATION);
        assertThat(testEvent.getEventTeaserUrl()).isEqualTo(DEFAULT_EVENT_TEASER_URL);
        assertThat(testEvent.getCodeParcours()).isEqualTo(DEFAULT_EVENT_CODE_PARCOURS);
        assertThat(testEvent.isEvtDemo()).isEqualTo(DEFAULT_EVT_DEMO);
    }

    @Test
    @Transactional
    void createEventWithExistingId() throws Exception {
        // Create the Event with an existing ID
        event.setId(1L);
        EventDTO eventDTO = eventMapper.toDto(event);

        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setLabel(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setDescription(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setAddress(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setZipCode(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setCity(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setStartAt(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setEndAt(null);

        // Create the Event, which fails.
        EventDTO eventDTO = eventMapper.toDto(event);

        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].cityLogoUrl").value(hasItem(DEFAULT_CITY_LOGO_URL)))
            .andExpect(jsonPath("$.[*].startAt").value(hasItem(DEFAULT_START_AT.toString())))
            .andExpect(jsonPath("$.[*].endAt").value(hasItem(DEFAULT_END_AT.toString())))
            .andExpect(jsonPath("$.[*].otherEvent").value(hasItem(DEFAULT_OTHER_EVENT)))
            .andExpect(jsonPath("$.[*].usefulInformation").value(hasItem(DEFAULT_USEFUL_INFORMATION)))
            .andExpect(jsonPath("$.[*].eventTeaserUrl").value(hasItem(DEFAULT_EVENT_TEASER_URL)))
            .andExpect(jsonPath("$.[*].codeParcours").value(hasItem(DEFAULT_EVENT_CODE_PARCOURS)))
            .andExpect(jsonPath("$.[*].evtDemo").value(hasItem(DEFAULT_EVT_DEMO)));
    }

    @Test
    @Transactional
    void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc
            .perform(get(ENTITY_API_URL_ID, event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.cityLogoUrl").value(DEFAULT_CITY_LOGO_URL))
            .andExpect(jsonPath("$.startAt").value(DEFAULT_START_AT.toString()))
            .andExpect(jsonPath("$.endAt").value(DEFAULT_END_AT.toString()))
            .andExpect(jsonPath("$.otherEvent").value(DEFAULT_OTHER_EVENT))
            .andExpect(jsonPath("$.usefulInformation").value((DEFAULT_USEFUL_INFORMATION)))
            .andExpect(jsonPath("$.eventTeaserUrl").value(DEFAULT_EVENT_TEASER_URL))
            .andExpect(jsonPath("$.codeParcours").value(DEFAULT_EVENT_CODE_PARCOURS))
            .andExpect(jsonPath("$.evtDemo").value(DEFAULT_EVT_DEMO));
    }

    @Test
    @Transactional
    void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .cityLogoUrl(UPDATED_CITY_LOGO_URL)
            .startAt(UPDATED_START_AT)
            .endAt(UPDATED_END_AT)
            .otherEvent(UPDATED_OTHER_EVENT)
            .usefulInformation(UPDATED_USEFUL_INFORMATION)
            .eventTeaserUrl(UPDATED_EVENT_TEASER_URL)
            .codeParcours(UPDATED_EVENT_CODE_PARCOURS)
            .evtDemo(UPDATED_EVT_DEMO);
        EventDTO eventDTO = eventMapper.toDto(updatedEvent);

        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEvent.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testEvent.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEvent.getCityLogoUrl()).isEqualTo(UPDATED_CITY_LOGO_URL);
        assertThat(testEvent.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testEvent.getEndAt()).isEqualTo(UPDATED_END_AT);
        assertThat(testEvent.isOtherEvent()).isEqualTo(UPDATED_OTHER_EVENT);
        assertThat(testEvent.getUsefulInformation()).isEqualTo(UPDATED_USEFUL_INFORMATION);
        assertThat(testEvent.getEventTeaserUrl()).isEqualTo(UPDATED_EVENT_TEASER_URL);
        assertThat(testEvent.getCodeParcours()).isEqualTo(UPDATED_EVENT_CODE_PARCOURS);
        assertThat(testEvent.isEvtDemo()).isEqualTo(UPDATED_EVT_DEMO);
    }

    @Test
    @Transactional
    void putNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .label(UPDATED_LABEL)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .cityLogoUrl(UPDATED_CITY_LOGO_URL)
            .endAt(UPDATED_END_AT);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEvent.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testEvent.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEvent.getCityLogoUrl()).isEqualTo(UPDATED_CITY_LOGO_URL);
        assertThat(testEvent.getStartAt()).isEqualTo(DEFAULT_START_AT);
        assertThat(testEvent.getEndAt()).isEqualTo(UPDATED_END_AT);
        assertThat(testEvent.isOtherEvent()).isEqualTo(DEFAULT_OTHER_EVENT);
        assertThat(testEvent.getUsefulInformation()).isEqualTo(DEFAULT_USEFUL_INFORMATION);
        assertThat(testEvent.getEventTeaserUrl()).isEqualTo(DEFAULT_EVENT_TEASER_URL);
        assertThat(testEvent.getCodeParcours()).isEqualTo(DEFAULT_EVENT_CODE_PARCOURS);
        assertThat(testEvent.isEvtDemo()).isEqualTo(DEFAULT_EVT_DEMO);
    }

    @Test
    @Transactional
    void fullUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .cityLogoUrl(UPDATED_CITY_LOGO_URL)
            .startAt(UPDATED_START_AT)
            .endAt(UPDATED_END_AT)
            .otherEvent(UPDATED_OTHER_EVENT)
            .usefulInformation(UPDATED_USEFUL_INFORMATION)
            .eventTeaserUrl(UPDATED_EVENT_TEASER_URL)
            .codeParcours(UPDATED_EVENT_CODE_PARCOURS)
            .evtDemo(UPDATED_EVT_DEMO);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEvent.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testEvent.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEvent.getCityLogoUrl()).isEqualTo(UPDATED_CITY_LOGO_URL);
        assertThat(testEvent.getStartAt()).isEqualTo(UPDATED_START_AT);
        assertThat(testEvent.getEndAt()).isEqualTo(UPDATED_END_AT);
        assertThat(testEvent.isOtherEvent()).isEqualTo(UPDATED_OTHER_EVENT);
        assertThat(testEvent.getUsefulInformation()).isEqualTo(UPDATED_USEFUL_INFORMATION);
        assertThat(testEvent.getEventTeaserUrl()).isEqualTo(UPDATED_EVENT_TEASER_URL);
        assertThat(testEvent.getCodeParcours()).isEqualTo(UPDATED_EVENT_CODE_PARCOURS);
        assertThat(testEvent.isEvtDemo()).isEqualTo(UPDATED_EVT_DEMO);
    }

    @Test
    @Transactional
    void patchNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, event.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
