package com.guao.manager.web.rest;

import com.guao.manager.repository.InscrptionRepository;
import com.guao.manager.service.InscrptionService;
import com.guao.manager.service.dto.InscrptionDTO;
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
 * REST controller for managing {@link com.guao.manager.domain.Inscrption}.
 */
@RestController
@RequestMapping("/api")
public class InscrptionResource {

    private final Logger log = LoggerFactory.getLogger(InscrptionResource.class);

    private static final String ENTITY_NAME = "inscrption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InscrptionService inscrptionService;

    private final InscrptionRepository inscrptionRepository;

    public InscrptionResource(InscrptionService inscrptionService, InscrptionRepository inscrptionRepository) {
        this.inscrptionService = inscrptionService;
        this.inscrptionRepository = inscrptionRepository;
    }

    /**
     * {@code POST  /inscrptions} : Create a new inscrption.
     *
     * @param inscrptionDTO the inscrptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inscrptionDTO, or with status {@code 400 (Bad Request)} if the inscrption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inscrptions")
    public ResponseEntity<InscrptionDTO> createInscrption(@Valid @RequestBody InscrptionDTO inscrptionDTO) throws URISyntaxException {
        log.debug("REST request to save Inscrption : {}", inscrptionDTO);
        if (inscrptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new inscrption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InscrptionDTO result = inscrptionService.save(inscrptionDTO);
        return ResponseEntity
            .created(new URI("/api/inscrptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inscrptions/:id} : Updates an existing inscrption.
     *
     * @param id the id of the inscrptionDTO to save.
     * @param inscrptionDTO the inscrptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inscrptionDTO,
     * or with status {@code 400 (Bad Request)} if the inscrptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inscrptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inscrptions/{id}")
    public ResponseEntity<InscrptionDTO> updateInscrption(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InscrptionDTO inscrptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Inscrption : {}, {}", id, inscrptionDTO);
        if (inscrptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inscrptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inscrptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InscrptionDTO result = inscrptionService.update(inscrptionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inscrptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inscrptions/:id} : Partial updates given fields of an existing inscrption, field will ignore if it is null
     *
     * @param id the id of the inscrptionDTO to save.
     * @param inscrptionDTO the inscrptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inscrptionDTO,
     * or with status {@code 400 (Bad Request)} if the inscrptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inscrptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inscrptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inscrptions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InscrptionDTO> partialUpdateInscrption(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InscrptionDTO inscrptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inscrption partially : {}, {}", id, inscrptionDTO);
        if (inscrptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inscrptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inscrptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InscrptionDTO> result = inscrptionService.partialUpdate(inscrptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inscrptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inscrptions} : get all the inscrptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inscrptions in body.
     */
    @GetMapping("/inscrptions")
    public ResponseEntity<List<InscrptionDTO>> getAllInscrptions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Inscrptions");
        Page<InscrptionDTO> page = inscrptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inscrptions/:id} : get the "id" inscrption.
     *
     * @param id the id of the inscrptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inscrptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inscrptions/{id}")
    public ResponseEntity<InscrptionDTO> getInscrption(@PathVariable Long id) {
        log.debug("REST request to get Inscrption : {}", id);
        Optional<InscrptionDTO> inscrptionDTO = inscrptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inscrptionDTO);
    }

    /**
     * {@code DELETE  /inscrptions/:id} : delete the "id" inscrption.
     *
     * @param id the id of the inscrptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inscrptions/{id}")
    public ResponseEntity<Void> deleteInscrption(@PathVariable Long id) {
        log.debug("REST request to delete Inscrption : {}", id);
        inscrptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
