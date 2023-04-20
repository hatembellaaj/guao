package com.guao.manager.service;

import com.guao.manager.domain.Eleve;
import com.guao.manager.repository.ClasseRepository;
import com.guao.manager.repository.EleveRepository;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.dto.EleveDTO;
import com.guao.manager.service.dto.MatiereDTO;
import com.guao.manager.service.mapper.ClasseMapper;
import com.guao.manager.service.mapper.EleveMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Eleve}.
 */
@Service
@Transactional
public class EleveService {

    private final Logger log = LoggerFactory.getLogger(EleveService.class);

    private final EleveRepository eleveRepository;

    private final EleveMapper eleveMapper;

    private final ClasseRepository classeRepository;

    private final ClasseMapper classeMapper;

    public EleveService(
        EleveRepository eleveRepository,
        EleveMapper eleveMapper,
        ClasseRepository classeRepository,
        ClasseMapper classeMapper
    ) {
        this.eleveRepository = eleveRepository;
        this.eleveMapper = eleveMapper;
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
    }

    /**
     * Save a eleve.
     *
     * @param eleveDTO the entity to save.
     * @return the persisted entity.
     */
    public EleveDTO save(EleveDTO eleveDTO) {
        log.debug("Request to save Eleve : {}", eleveDTO);
        Eleve eleve = eleveMapper.toEntity(eleveDTO);
        eleve = eleveRepository.save(eleve);
        return eleveMapper.toDto(eleve);
    }

    /**
     * Update a eleve.
     *
     * @param eleveDTO the entity to save.
     * @return the persisted entity.
     */
    public EleveDTO update(EleveDTO eleveDTO) {
        log.debug("Request to update Eleve : {}", eleveDTO);
        Eleve eleve = eleveMapper.toEntity(eleveDTO);
        eleve = eleveRepository.save(eleve);
        return eleveMapper.toDto(eleve);
    }

    /**
     * Partially update a eleve.
     *
     * @param eleveDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EleveDTO> partialUpdate(EleveDTO eleveDTO) {
        log.debug("Request to partially update Eleve : {}", eleveDTO);

        return eleveRepository
            .findById(eleveDTO.getId())
            .map(existingEleve -> {
                eleveMapper.partialUpdate(existingEleve, eleveDTO);

                return existingEleve;
            })
            .map(eleveRepository::save)
            .map(eleveMapper::toDto);
    }

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EleveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eleves");
        Page<EleveDTO> pedto = eleveRepository.findAll(pageable).map(eleveMapper::toDto);

        for (int i = 0; i < pedto.getSize(); i++) pedto.forEach(e -> {
            Optional<ClasseDTO> cdto = this.classeRepository.findById(e.getClasse().getId()).map(classeMapper::toDto);
            e.setClasse(cdto.get());
        });

        return pedto;
    }

    /**
     * Get one eleve by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EleveDTO> findOne(Long id) {
        log.debug("Request to get Eleve : {}", id);
        return eleveRepository.findById(id).map(eleveMapper::toDto);
    }

    /**
     * Delete the eleve by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Eleve : {}", id);
        eleveRepository.deleteById(id);
    }
}
