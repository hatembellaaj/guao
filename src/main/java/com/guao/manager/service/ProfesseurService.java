package com.guao.manager.service;

import com.guao.manager.domain.Professeur;
import com.guao.manager.repository.ProfesseurRepository;
import com.guao.manager.service.dto.ProfesseurDTO;
import com.guao.manager.service.mapper.ProfesseurMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Professeur}.
 */
@Service
@Transactional
public class ProfesseurService {

    private final Logger log = LoggerFactory.getLogger(ProfesseurService.class);

    private final ProfesseurRepository professeurRepository;

    private final ProfesseurMapper professeurMapper;

    public ProfesseurService(ProfesseurRepository professeurRepository, ProfesseurMapper professeurMapper) {
        this.professeurRepository = professeurRepository;
        this.professeurMapper = professeurMapper;
    }

    /**
     * Save a professeur.
     *
     * @param professeurDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfesseurDTO save(ProfesseurDTO professeurDTO) {
        log.debug("Request to save Professeur : {}", professeurDTO);
        Professeur professeur = professeurMapper.toEntity(professeurDTO);
        professeur = professeurRepository.save(professeur);
        return professeurMapper.toDto(professeur);
    }

    /**
     * Update a professeur.
     *
     * @param professeurDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfesseurDTO update(ProfesseurDTO professeurDTO) {
        log.debug("Request to update Professeur : {}", professeurDTO);
        Professeur professeur = professeurMapper.toEntity(professeurDTO);
        professeur = professeurRepository.save(professeur);
        return professeurMapper.toDto(professeur);
    }

    /**
     * Partially update a professeur.
     *
     * @param professeurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProfesseurDTO> partialUpdate(ProfesseurDTO professeurDTO) {
        log.debug("Request to partially update Professeur : {}", professeurDTO);

        return professeurRepository
            .findById(professeurDTO.getId())
            .map(existingProfesseur -> {
                professeurMapper.partialUpdate(existingProfesseur, professeurDTO);

                return existingProfesseur;
            })
            .map(professeurRepository::save)
            .map(professeurMapper::toDto);
    }

    /**
     * Get all the professeurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfesseurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Professeurs");
        return professeurRepository.findAll(pageable).map(professeurMapper::toDto);
    }

    /**
     * Get all the professeurs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProfesseurDTO> findAllWithEagerRelationships(Pageable pageable) {
        return professeurRepository.findAllWithEagerRelationships(pageable).map(professeurMapper::toDto);
    }

    /**
     * Get one professeur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfesseurDTO> findOne(Long id) {
        log.debug("Request to get Professeur : {}", id);
        return professeurRepository.findOneWithEagerRelationships(id).map(professeurMapper::toDto);
    }

    /**
     * Delete the professeur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Professeur : {}", id);
        professeurRepository.deleteById(id);
    }
}
