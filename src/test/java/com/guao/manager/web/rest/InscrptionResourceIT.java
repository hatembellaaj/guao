package com.guao.manager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guao.manager.IntegrationTest;
import com.guao.manager.domain.Classe;
import com.guao.manager.domain.Inscrption;
import com.guao.manager.repository.InscrptionRepository;
import com.guao.manager.service.dto.InscrptionDTO;
import com.guao.manager.service.mapper.InscrptionMapper;
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
 * Integration tests for the {@link InscrptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InscrptionResourceIT {

    private static final LocalDate DEFAULT_DATEINSCRIPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEINSCRIPTION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Double DEFAULT_TARIFINSCRIPTION = 1D;
    private static final Double UPDATED_TARIFINSCRIPTION = 2D;

    private static final String ENTITY_API_URL = "/api/inscrptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InscrptionRepository inscrptionRepository;

    @Autowired
    private InscrptionMapper inscrptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInscrptionMockMvc;

    private Inscrption inscrption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscrption createEntity(EntityManager em) {
        Inscrption inscrption = new Inscrption()
            .dateinscription(DEFAULT_DATEINSCRIPTION)
            .commentaire(DEFAULT_COMMENTAIRE)
            .tarifinscription(DEFAULT_TARIFINSCRIPTION);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        inscrption.setClasse(classe);
        return inscrption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscrption createUpdatedEntity(EntityManager em) {
        Inscrption inscrption = new Inscrption()
            .dateinscription(UPDATED_DATEINSCRIPTION)
            .commentaire(UPDATED_COMMENTAIRE)
            .tarifinscription(UPDATED_TARIFINSCRIPTION);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createUpdatedEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        inscrption.setClasse(classe);
        return inscrption;
    }

    @BeforeEach
    public void initTest() {
        inscrption = createEntity(em);
    }

    @Test
    @Transactional
    void createInscrption() throws Exception {
        int databaseSizeBeforeCreate = inscrptionRepository.findAll().size();
        // Create the Inscrption
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);
        restInscrptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscrptionDTO)))
            .andExpect(status().isCreated());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeCreate + 1);
        Inscrption testInscrption = inscrptionList.get(inscrptionList.size() - 1);
        assertThat(testInscrption.getDateinscription()).isEqualTo(DEFAULT_DATEINSCRIPTION);
        assertThat(testInscrption.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testInscrption.getTarifinscription()).isEqualTo(DEFAULT_TARIFINSCRIPTION);
    }

    @Test
    @Transactional
    void createInscrptionWithExistingId() throws Exception {
        // Create the Inscrption with an existing ID
        inscrption.setId(1L);
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);

        int databaseSizeBeforeCreate = inscrptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscrptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscrptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateinscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = inscrptionRepository.findAll().size();
        // set the field null
        inscrption.setDateinscription(null);

        // Create the Inscrption, which fails.
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);

        restInscrptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscrptionDTO)))
            .andExpect(status().isBadRequest());

        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInscrptions() throws Exception {
        // Initialize the database
        inscrptionRepository.saveAndFlush(inscrption);

        // Get all the inscrptionList
        restInscrptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscrption.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateinscription").value(hasItem(DEFAULT_DATEINSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].tarifinscription").value(hasItem(DEFAULT_TARIFINSCRIPTION.doubleValue())));
    }

    @Test
    @Transactional
    void getInscrption() throws Exception {
        // Initialize the database
        inscrptionRepository.saveAndFlush(inscrption);

        // Get the inscrption
        restInscrptionMockMvc
            .perform(get(ENTITY_API_URL_ID, inscrption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inscrption.getId().intValue()))
            .andExpect(jsonPath("$.dateinscription").value(DEFAULT_DATEINSCRIPTION.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.tarifinscription").value(DEFAULT_TARIFINSCRIPTION.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingInscrption() throws Exception {
        // Get the inscrption
        restInscrptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInscrption() throws Exception {
        // Initialize the database
        inscrptionRepository.saveAndFlush(inscrption);

        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();

        // Update the inscrption
        Inscrption updatedInscrption = inscrptionRepository.findById(inscrption.getId()).get();
        // Disconnect from session so that the updates on updatedInscrption are not directly saved in db
        em.detach(updatedInscrption);
        updatedInscrption
            .dateinscription(UPDATED_DATEINSCRIPTION)
            .commentaire(UPDATED_COMMENTAIRE)
            .tarifinscription(UPDATED_TARIFINSCRIPTION);
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(updatedInscrption);

        restInscrptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscrptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscrptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
        Inscrption testInscrption = inscrptionList.get(inscrptionList.size() - 1);
        assertThat(testInscrption.getDateinscription()).isEqualTo(UPDATED_DATEINSCRIPTION);
        assertThat(testInscrption.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testInscrption.getTarifinscription()).isEqualTo(UPDATED_TARIFINSCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingInscrption() throws Exception {
        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();
        inscrption.setId(count.incrementAndGet());

        // Create the Inscrption
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscrptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscrptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscrptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInscrption() throws Exception {
        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();
        inscrption.setId(count.incrementAndGet());

        // Create the Inscrption
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscrptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscrptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInscrption() throws Exception {
        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();
        inscrption.setId(count.incrementAndGet());

        // Create the Inscrption
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscrptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscrptionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInscrptionWithPatch() throws Exception {
        // Initialize the database
        inscrptionRepository.saveAndFlush(inscrption);

        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();

        // Update the inscrption using partial update
        Inscrption partialUpdatedInscrption = new Inscrption();
        partialUpdatedInscrption.setId(inscrption.getId());

        partialUpdatedInscrption.commentaire(UPDATED_COMMENTAIRE);

        restInscrptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscrption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInscrption))
            )
            .andExpect(status().isOk());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
        Inscrption testInscrption = inscrptionList.get(inscrptionList.size() - 1);
        assertThat(testInscrption.getDateinscription()).isEqualTo(DEFAULT_DATEINSCRIPTION);
        assertThat(testInscrption.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testInscrption.getTarifinscription()).isEqualTo(DEFAULT_TARIFINSCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateInscrptionWithPatch() throws Exception {
        // Initialize the database
        inscrptionRepository.saveAndFlush(inscrption);

        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();

        // Update the inscrption using partial update
        Inscrption partialUpdatedInscrption = new Inscrption();
        partialUpdatedInscrption.setId(inscrption.getId());

        partialUpdatedInscrption
            .dateinscription(UPDATED_DATEINSCRIPTION)
            .commentaire(UPDATED_COMMENTAIRE)
            .tarifinscription(UPDATED_TARIFINSCRIPTION);

        restInscrptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscrption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInscrption))
            )
            .andExpect(status().isOk());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
        Inscrption testInscrption = inscrptionList.get(inscrptionList.size() - 1);
        assertThat(testInscrption.getDateinscription()).isEqualTo(UPDATED_DATEINSCRIPTION);
        assertThat(testInscrption.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testInscrption.getTarifinscription()).isEqualTo(UPDATED_TARIFINSCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingInscrption() throws Exception {
        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();
        inscrption.setId(count.incrementAndGet());

        // Create the Inscrption
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscrptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inscrptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscrptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInscrption() throws Exception {
        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();
        inscrption.setId(count.incrementAndGet());

        // Create the Inscrption
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscrptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscrptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInscrption() throws Exception {
        int databaseSizeBeforeUpdate = inscrptionRepository.findAll().size();
        inscrption.setId(count.incrementAndGet());

        // Create the Inscrption
        InscrptionDTO inscrptionDTO = inscrptionMapper.toDto(inscrption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscrptionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(inscrptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscrption in the database
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInscrption() throws Exception {
        // Initialize the database
        inscrptionRepository.saveAndFlush(inscrption);

        int databaseSizeBeforeDelete = inscrptionRepository.findAll().size();

        // Delete the inscrption
        restInscrptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, inscrption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inscrption> inscrptionList = inscrptionRepository.findAll();
        assertThat(inscrptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
