package com.guao.manager.service;

import com.guao.manager.domain.Classe;
import com.guao.manager.domain.Examen;
import com.guao.manager.repository.ClasseRepository;
import com.guao.manager.repository.ExamenRepository;
import com.guao.manager.repository.MatiereRepository;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.dto.ExamenDTO;
import com.guao.manager.service.dto.MatiereDTO;
import com.guao.manager.service.mapper.ClasseMapper;
import com.guao.manager.service.mapper.ExamenMapper;
import com.guao.manager.service.mapper.MatiereMapper;
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

    private final ClasseRepository classeRepository;

    private final MatiereRepository matiereRepository;

    private final ExamenMapper examenMapper;

    private final ClasseMapper classeMapper;

    private final MatiereMapper matiereMapper;

    public ExamenService(
        ExamenRepository examenRepository,
        ClasseRepository classeRepository,
        ClasseMapper classeMapper,
        MatiereRepository matiereRepository,
        MatiereMapper matiereMapper,
        ExamenMapper examenMapper
    ) {
        this.examenRepository = examenRepository;
        this.examenMapper = examenMapper;
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
        this.matiereMapper = matiereMapper;
        this.matiereRepository = matiereRepository;
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
        Page<ExamenDTO> pedto = examenRepository.findAll(pageable).map(examenMapper::toDto);

        for (int i = 0; i < pedto.getSize(); i++) pedto.forEach(e -> {
            Optional<ClasseDTO> cdto = this.classeRepository.findById(e.getClasse().getId()).map(classeMapper::toDto);
            e.setClasse(cdto.get());
            Optional<MatiereDTO> mdto = this.matiereRepository.findById(e.getMatiere().getId()).map(matiereMapper::toDto);

            e.setMatiere(mdto.get());
        });

        return pedto;
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
