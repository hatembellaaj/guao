package com.guao.manager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guao.manager.IntegrationTest;
import com.guao.manager.domain.Classe;
import com.guao.manager.domain.Examen;
import com.guao.manager.domain.Matiere;
import com.guao.manager.repository.ExamenRepository;
import com.guao.manager.service.dto.ExamenDTO;
import com.guao.manager.service.mapper.ExamenMapper;
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
 * Integration tests for the {@link ExamenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExamenResourceIT {

    private static final Integer DEFAULT_NUMEXAMEN = 1;
    private static final Integer UPDATED_NUMEXAMEN = 2;

    private static final Double DEFAULT_POURCENTAGE = 1D;
    private static final Double UPDATED_POURCENTAGE = 2D;

    private static final Boolean DEFAULT_VALIDE = false;
    private static final Boolean UPDATED_VALIDE = true;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;

    private static final String ENTITY_API_URL = "/api/examen";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private ExamenMapper examenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamenMockMvc;

    private Examen examen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createEntity(EntityManager em) {
        Examen examen = new Examen()
            .numexamen(DEFAULT_NUMEXAMEN)
            .pourcentage(DEFAULT_POURCENTAGE)
            .valide(DEFAULT_VALIDE)
            .date(DEFAULT_DATE)
            .duree(DEFAULT_DUREE);
        // Add required entity
        Matiere matiere;
        if (TestUtil.findAll(em, Matiere.class).isEmpty()) {
            matiere = MatiereResourceIT.createEntity(em);
            em.persist(matiere);
            em.flush();
        } else {
            matiere = TestUtil.findAll(em, Matiere.class).get(0);
        }
        examen.setMatiere(matiere);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        examen.setClasse(classe);
        return examen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createUpdatedEntity(EntityManager em) {
        Examen examen = new Examen()
            .numexamen(UPDATED_NUMEXAMEN)
            .pourcentage(UPDATED_POURCENTAGE)
            .valide(UPDATED_VALIDE)
            .date(UPDATED_DATE)
            .duree(UPDATED_DUREE);
        // Add required entity
        Matiere matiere;
        if (TestUtil.findAll(em, Matiere.class).isEmpty()) {
            matiere = MatiereResourceIT.createUpdatedEntity(em);
            em.persist(matiere);
            em.flush();
        } else {
            matiere = TestUtil.findAll(em, Matiere.class).get(0);
        }
        examen.setMatiere(matiere);
        // Add required entity
        Classe classe;
        if (TestUtil.findAll(em, Classe.class).isEmpty()) {
            classe = ClasseResourceIT.createUpdatedEntity(em);
            em.persist(classe);
            em.flush();
        } else {
            classe = TestUtil.findAll(em, Classe.class).get(0);
        }
        examen.setClasse(classe);
        return examen;
    }

    @BeforeEach
    public void initTest() {
        examen = createEntity(em);
    }

    @Test
    @Transactional
    void createExamen() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();
        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);
        restExamenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isCreated());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate + 1);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getNumexamen()).isEqualTo(DEFAULT_NUMEXAMEN);
        assertThat(testExamen.getPourcentage()).isEqualTo(DEFAULT_POURCENTAGE);
        assertThat(testExamen.getValide()).isEqualTo(DEFAULT_VALIDE);
        assertThat(testExamen.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testExamen.getDuree()).isEqualTo(DEFAULT_DUREE);
    }

    @Test
    @Transactional
    void createExamenWithExistingId() throws Exception {
        // Create the Examen with an existing ID
        examen.setId(1L);
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        int databaseSizeBeforeCreate = examenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumexamenIsRequired() throws Exception {
        int databaseSizeBeforeTest = examenRepository.findAll().size();
        // set the field null
        examen.setNumexamen(null);

        // Create the Examen, which fails.
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        restExamenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isBadRequest());

        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get all the examenList
        restExamenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examen.getId().intValue())))
            .andExpect(jsonPath("$.[*].numexamen").value(hasItem(DEFAULT_NUMEXAMEN)))
            .andExpect(jsonPath("$.[*].pourcentage").value(hasItem(DEFAULT_POURCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].valide").value(hasItem(DEFAULT_VALIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)));
    }

    @Test
    @Transactional
    void getExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get the examen
        restExamenMockMvc
            .perform(get(ENTITY_API_URL_ID, examen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examen.getId().intValue()))
            .andExpect(jsonPath("$.numexamen").value(DEFAULT_NUMEXAMEN))
            .andExpect(jsonPath("$.pourcentage").value(DEFAULT_POURCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.valide").value(DEFAULT_VALIDE.booleanValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE));
    }

    @Test
    @Transactional
    void getNonExistingExamen() throws Exception {
        // Get the examen
        restExamenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Update the examen
        Examen updatedExamen = examenRepository.findById(examen.getId()).get();
        // Disconnect from session so that the updates on updatedExamen are not directly saved in db
        em.detach(updatedExamen);
        updatedExamen
            .numexamen(UPDATED_NUMEXAMEN)
            .pourcentage(UPDATED_POURCENTAGE)
            .valide(UPDATED_VALIDE)
            .date(UPDATED_DATE)
            .duree(UPDATED_DUREE);
        ExamenDTO examenDTO = examenMapper.toDto(updatedExamen);

        restExamenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examenDTO))
            )
            .andExpect(status().isOk());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getNumexamen()).isEqualTo(UPDATED_NUMEXAMEN);
        assertThat(testExamen.getPourcentage()).isEqualTo(UPDATED_POURCENTAGE);
        assertThat(testExamen.getValide()).isEqualTo(UPDATED_VALIDE);
        assertThat(testExamen.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExamen.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    void putNonExistingExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();
        examen.setId(count.incrementAndGet());

        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();
        examen.setId(count.incrementAndGet());

        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();
        examen.setId(count.incrementAndGet());

        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExamenWithPatch() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Update the examen using partial update
        Examen partialUpdatedExamen = new Examen();
        partialUpdatedExamen.setId(examen.getId());

        partialUpdatedExamen.pourcentage(UPDATED_POURCENTAGE).valide(UPDATED_VALIDE).duree(UPDATED_DUREE);

        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExamen))
            )
            .andExpect(status().isOk());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getNumexamen()).isEqualTo(DEFAULT_NUMEXAMEN);
        assertThat(testExamen.getPourcentage()).isEqualTo(UPDATED_POURCENTAGE);
        assertThat(testExamen.getValide()).isEqualTo(UPDATED_VALIDE);
        assertThat(testExamen.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testExamen.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    void fullUpdateExamenWithPatch() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Update the examen using partial update
        Examen partialUpdatedExamen = new Examen();
        partialUpdatedExamen.setId(examen.getId());

        partialUpdatedExamen
            .numexamen(UPDATED_NUMEXAMEN)
            .pourcentage(UPDATED_POURCENTAGE)
            .valide(UPDATED_VALIDE)
            .date(UPDATED_DATE)
            .duree(UPDATED_DUREE);

        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExamen))
            )
            .andExpect(status().isOk());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getNumexamen()).isEqualTo(UPDATED_NUMEXAMEN);
        assertThat(testExamen.getPourcentage()).isEqualTo(UPDATED_POURCENTAGE);
        assertThat(testExamen.getValide()).isEqualTo(UPDATED_VALIDE);
        assertThat(testExamen.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExamen.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    void patchNonExistingExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();
        examen.setId(count.incrementAndGet());

        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, examenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();
        examen.setId(count.incrementAndGet());

        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();
        examen.setId(count.incrementAndGet());

        // Create the Examen
        ExamenDTO examenDTO = examenMapper.toDto(examen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(examenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        int databaseSizeBeforeDelete = examenRepository.findAll().size();

        // Delete the examen
        restExamenMockMvc
            .perform(delete(ENTITY_API_URL_ID, examen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
