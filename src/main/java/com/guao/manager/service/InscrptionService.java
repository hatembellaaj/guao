package com.guao.manager.service;

import com.guao.manager.domain.Inscrption;
import com.guao.manager.repository.ClasseRepository;
import com.guao.manager.repository.InscrptionRepository;
import com.guao.manager.repository.MatiereRepository;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.dto.InscrptionDTO;
import com.guao.manager.service.dto.MatiereDTO;
import com.guao.manager.service.mapper.ClasseMapper;
import com.guao.manager.service.mapper.ExamenMapper;
import com.guao.manager.service.mapper.InscrptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Inscrption}.
 */
@Service
@Transactional
public class InscrptionService {

    private final Logger log = LoggerFactory.getLogger(InscrptionService.class);

    private final InscrptionRepository inscrptionRepository;

    private final InscrptionMapper inscrptionMapper;

    private final ClasseRepository classeRepository;

    private final ClasseMapper classeMapper;

    public InscrptionService(
        InscrptionRepository inscrptionRepository,
        ClasseRepository classeRepository,
        ClasseMapper classeMapper,
        InscrptionMapper inscrptionMapper
    ) {
        this.inscrptionRepository = inscrptionRepository;
        this.inscrptionMapper = inscrptionMapper;
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
    }

    /**
     * Save a inscrption.
     *
     * @param inscrptionDTO the entity to save.
     * @return the persisted entity.
     */
    public InscrptionDTO save(InscrptionDTO inscrptionDTO) {
        log.debug("Request to save Inscrption : {}", inscrptionDTO);
        Inscrption inscrption = inscrptionMapper.toEntity(inscrptionDTO);
        inscrption = inscrptionRepository.save(inscrption);
        return inscrptionMapper.toDto(inscrption);
    }

    /**
     * Update a inscrption.
     *
     * @param inscrptionDTO the entity to save.
     * @return the persisted entity.
     */
    public InscrptionDTO update(InscrptionDTO inscrptionDTO) {
        log.debug("Request to update Inscrption : {}", inscrptionDTO);
        Inscrption inscrption = inscrptionMapper.toEntity(inscrptionDTO);
        inscrption = inscrptionRepository.save(inscrption);
        return inscrptionMapper.toDto(inscrption);
    }

    /**
     * Partially update a inscrption.
     *
     * @param inscrptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InscrptionDTO> partialUpdate(InscrptionDTO inscrptionDTO) {
        log.debug("Request to partially update Inscrption : {}", inscrptionDTO);

        return inscrptionRepository
            .findById(inscrptionDTO.getId())
            .map(existingInscrption -> {
                inscrptionMapper.partialUpdate(existingInscrption, inscrptionDTO);

                return existingInscrption;
            })
            .map(inscrptionRepository::save)
            .map(inscrptionMapper::toDto);
    }

    /**
     * Get all the inscrptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InscrptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inscrptions");
        Page<InscrptionDTO> pidto = inscrptionRepository.findAll(pageable).map(inscrptionMapper::toDto);
        for (int i = 0; i < pidto.getSize(); i++) pidto.forEach(e -> {
            Optional<ClasseDTO> cdto = this.classeRepository.findById(e.getClasse().getId()).map(classeMapper::toDto);
            e.setClasse(cdto.get());
        });

        return pidto;
    }

    /**
     * Get one inscrption by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InscrptionDTO> findOne(Long id) {
        log.debug("Request to get Inscrption : {}", id);
        return inscrptionRepository.findById(id).map(inscrptionMapper::toDto);
    }

    /**
     * Delete the inscrption by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Inscrption : {}", id);
        inscrptionRepository.deleteById(id);
    }
}
