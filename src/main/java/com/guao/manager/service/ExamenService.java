package com.guao.manager.service;

import com.guao.manager.domain.Examen;
import com.guao.manager.repository.ExamenRepository;
import com.guao.manager.service.dto.ExamenDTO;
import com.guao.manager.service.mapper.ExamenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Examen}.
 */
@Service
@Transactional
public class ExamenService {

    private final Logger log = LoggerFactory.getLogger(ExamenService.class);

    private final ExamenRepository examenRepository;

    private final ExamenMapper examenMapper;

    public ExamenService(ExamenRepository examenRepository, ExamenMapper examenMapper) {
        this.examenRepository = examenRepository;
        this.examenMapper = examenMapper;
    }

    /**
     * Save a examen.
     *
     * @param examenDTO the entity to save.
     * @return the persisted entity.
     */
    public ExamenDTO save(ExamenDTO examenDTO) {
        log.debug("Request to save Examen : {}", examenDTO);
        Examen examen = examenMapper.toEntity(examenDTO);
        examen = examenRepository.save(examen);
        return examenMapper.toDto(examen);
    }

    /**
     * Update a examen.
     *
     * @param examenDTO the entity to save.
     * @return the persisted entity.
     */
    public ExamenDTO update(ExamenDTO examenDTO) {
        log.debug("Request to update Examen : {}", examenDTO);
        Examen examen = examenMapper.toEntity(examenDTO);
        examen = examenRepository.save(examen);
        return examenMapper.toDto(examen);
    }

    /**
     * Partially update a examen.
     *
     * @param examenDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExamenDTO> partialUpdate(ExamenDTO examenDTO) {
        log.debug("Request to partially update Examen : {}", examenDTO);

        return examenRepository
            .findById(examenDTO.getId())
            .map(existingExamen -> {
                examenMapper.partialUpdate(existingExamen, examenDTO);

                return existingExamen;
            })
            .map(examenRepository::save)
            .map(examenMapper::toDto);
    }

    /**
     * Get all the examen.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Examen");
        return examenRepository.findAll(pageable).map(examenMapper::toDto);
    }

    /**
     * Get one examen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExamenDTO> findOne(Long id) {
        log.debug("Request to get Examen : {}", id);
        return examenRepository.findById(id).map(examenMapper::toDto);
    }

    /**
     * Delete the examen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Examen : {}", id);
        examenRepository.deleteById(id);
    }
}
