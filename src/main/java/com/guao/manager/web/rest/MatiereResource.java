package com.guao.manager.web.rest;

import com.guao.manager.repository.MatiereRepository;
import com.guao.manager.service.MatiereService;
import com.guao.manager.service.dto.MatiereDTO;
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
 * REST controller for managing {@link com.guao.manager.domain.Matiere}.
 */
@RestController
@RequestMapping("/api")
public class MatiereResource {

    private final Logger log = LoggerFactory.getLogger(MatiereResource.class);

    private static final String ENTITY_NAME = "matiere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatiereService matiereService;

    private final MatiereRepository matiereRepository;

    public MatiereResource(MatiereService matiereService, MatiereRepository matiereRepository) {
        this.matiereService = matiereService;
        this.matiereRepository = matiereRepository;
    }

    /**
     * {@code POST  /matieres} : Create a new matiere.
     *
     * @param matiereDTO the matiereDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matiereDTO, or with status {@code 400 (Bad Request)} if the matiere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matieres")
    public ResponseEntity<MatiereDTO> createMatiere(@Valid @RequestBody MatiereDTO matiereDTO) throws URISyntaxException {
        log.debug("REST request to save Matiere : {}", matiereDTO);
        if (matiereDTO.getId() != null) {
            throw new BadRequestAlertException("A new matiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatiereDTO result = matiereService.save(matiereDTO);
        return ResponseEntity
            .created(new URI("/api/matieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matieres/:id} : Updates an existing matiere.
     *
     * @param id the id of the matiereDTO to save.
     * @param matiereDTO the matiereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matiereDTO,
     * or with status {@code 400 (Bad Request)} if the matiereDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matiereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matieres/{id}")
    public ResponseEntity<MatiereDTO> updateMatiere(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MatiereDTO matiereDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Matiere : {}, {}", id, matiereDTO);
        if (matiereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matiereDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matiereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MatiereDTO result = matiereService.update(matiereDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matiereDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /matieres/:id} : Partial updates given fields of an existing matiere, field will ignore if it is null
     *
     * @param id the id of the matiereDTO to save.
     * @param matiereDTO the matiereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matiereDTO,
     * or with status {@code 400 (Bad Request)} if the matiereDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matiereDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matiereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/matieres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatiereDTO> partialUpdateMatiere(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MatiereDTO matiereDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Matiere partially : {}, {}", id, matiereDTO);
        if (matiereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matiereDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matiereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatiereDTO> result = matiereService.partialUpdate(matiereDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matiereDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /matieres} : get all the matieres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matieres in body.
     */
    @GetMapping("/matieres")
    public ResponseEntity<List<MatiereDTO>> getAllMatieres(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Matieres");
        Page<MatiereDTO> page = matiereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matieres/:id} : get the "id" matiere.
     *
     * @param id the id of the matiereDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matiereDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matieres/{id}")
    public ResponseEntity<MatiereDTO> getMatiere(@PathVariable Long id) {
        log.debug("REST request to get Matiere : {}", id);
        Optional<MatiereDTO> matiereDTO = matiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matiereDTO);
    }

    /**
     * {@code DELETE  /matieres/:id} : delete the "id" matiere.
     *
     * @param id the id of the matiereDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matieres/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        log.debug("REST request to delete Matiere : {}", id);
        matiereService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
