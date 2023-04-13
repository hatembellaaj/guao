package com.guao.manager.service;

import com.guao.manager.domain.Classe;
import com.guao.manager.repository.ClasseRepository;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.mapper.ClasseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Classe}.
 */
@Service
@Transactional
public class ClasseService {

    private final Logger log = LoggerFactory.getLogger(ClasseService.class);

    private final ClasseRepository classeRepository;

    private final ClasseMapper classeMapper;

    public ClasseService(ClasseRepository classeRepository, ClasseMapper classeMapper) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
    }

    /**
     * Save a classe.
     *
     * @param classeDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasseDTO save(ClasseDTO classeDTO) {
        log.debug("Request to save Classe : {}", classeDTO);
        Classe classe = classeMapper.toEntity(classeDTO);
        classe = classeRepository.save(classe);
        return classeMapper.toDto(classe);
    }

    /**
     * Update a classe.
     *
     * @param classeDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasseDTO update(ClasseDTO classeDTO) {
        log.debug("Request to update Classe : {}", classeDTO);
        Classe classe = classeMapper.toEntity(classeDTO);
        classe = classeRepository.save(classe);
        return classeMapper.toDto(classe);
    }

    /**
     * Partially update a classe.
     *
     * @param classeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClasseDTO> partialUpdate(ClasseDTO classeDTO) {
        log.debug("Request to partially update Classe : {}", classeDTO);

        return classeRepository
            .findById(classeDTO.getId())
            .map(existingClasse -> {
                classeMapper.partialUpdate(existingClasse, classeDTO);

                return existingClasse;
            })
            .map(classeRepository::save)
            .map(classeMapper::toDto);
    }

    /**
     * Get all the classes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Classes");
        return classeRepository.findAll(pageable).map(classeMapper::toDto);
    }

    /**
     * Get one classe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClasseDTO> findOne(Long id) {
        log.debug("Request to get Classe : {}", id);
        return classeRepository.findById(id).map(classeMapper::toDto);
    }

    /**
     * Delete the classe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Classe : {}", id);
        classeRepository.deleteById(id);
    }
}
