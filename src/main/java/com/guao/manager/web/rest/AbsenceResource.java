package com.guao.manager.web.rest;

import com.guao.manager.repository.AbsenceRepository;
import com.guao.manager.service.AbsenceService;
import com.guao.manager.service.dto.AbsenceDTO;
import com.guao.manager.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.guao.manager.domain.Absence}.
 */
@RestController
@RequestMapping("/api")
public class AbsenceResource {

    private final Logger log = LoggerFactory.getLogger(AbsenceResource.class);

    private static final String ENTITY_NAME = "absence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbsenceService absenceService;

    private final AbsenceRepository absenceRepository;

    public AbsenceResource(AbsenceService absenceService, AbsenceRepository absenceRepository) {
        this.absenceService = absenceService;
        this.absenceRepository = absenceRepository;
    }

    /**
     * {@code POST  /absences} : Create a new absence.
     *
     * @param absenceDTO the absenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new absenceDTO, or with status {@code 400 (Bad Request)} if the absence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/absences")
    public ResponseEntity<AbsenceDTO> createAbsence(@Valid @RequestBody AbsenceDTO absenceDTO) throws URISyntaxException {
        log.debug("REST request to save Absence : {}", absenceDTO);
        if (absenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new absence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbsenceDTO result = absenceService.save(absenceDTO);
        return ResponseEntity
            .created(new URI("/api/absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /absences/:id} : Updates an existing absence.
     *
     * @param id the id of the absenceDTO to save.
     * @param absenceDTO the absenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated absenceDTO,
     * or with status {@code 400 (Bad Request)} if the absenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the absenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/absences/{id}")
    public ResponseEntity<AbsenceDTO> updateAbsence(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AbsenceDTO absenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Absence : {}, {}", id, absenceDTO);
        if (absenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, absenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!absenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AbsenceDTO result = absenceService.update(absenceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, absenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /absences/:id} : Partial updates given fields of an existing absence, field will ignore if it is null
     *
     * @param id the id of the absenceDTO to save.
     * @param absenceDTO the absenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated absenceDTO,
     * or with status {@code 400 (Bad Request)} if the absenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the absenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the absenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/absences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AbsenceDTO> partialUpdateAbsence(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AbsenceDTO absenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Absence partially : {}, {}", id, absenceDTO);
        if (absenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, absenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!absenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AbsenceDTO> result = absenceService.partialUpdate(absenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, absenceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /absences} : get all the absences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of absences in body.
     */
    @GetMapping("/absences")
    public ResponseEntity<List<AbsenceDTO>> getAllAbsences(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Absences");
        Page<AbsenceDTO> page = absenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /absences/:id} : get the "id" absence.
     *
     * @param id the id of the absenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the absenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/absences/{id}")
    public ResponseEntity<AbsenceDTO> getAbsence(@PathVariable Long id) {
        log.debug("REST request to get Absence : {}", id);
        Optional<AbsenceDTO> absenceDTO = absenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(absenceDTO);
    }

    /**
     * {@code DELETE  /absences/:id} : delete the "id" absence.
     *
     * @param id the id of the absenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/absences/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        log.debug("REST request to delete Absence : {}", id);
        absenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
