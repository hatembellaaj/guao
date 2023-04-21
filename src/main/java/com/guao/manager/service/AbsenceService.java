package com.guao.manager.service;

import com.guao.manager.domain.Absence;
import com.guao.manager.repository.AbsenceRepository;
import com.guao.manager.repository.EleveRepository;
import com.guao.manager.service.dto.AbsenceDTO;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.dto.EleveDTO;
import com.guao.manager.service.dto.MatiereDTO;
import com.guao.manager.service.mapper.AbsenceMapper;
import com.guao.manager.service.mapper.EleveMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Absence}.
 */
@Service
@Transactional
public class AbsenceService {

    private final Logger log = LoggerFactory.getLogger(AbsenceService.class);

    private final AbsenceRepository absenceRepository;

    private final AbsenceMapper absenceMapper;

    private final EleveRepository eleveRepository;

    private final EleveMapper eleveMapper;

    public AbsenceService(
        AbsenceRepository absenceRepository,
        EleveRepository eleveRepository,
        EleveMapper eleveMapper,
        AbsenceMapper absenceMapper
    ) {
        this.absenceRepository = absenceRepository;
        this.absenceMapper = absenceMapper;
        this.eleveMapper = eleveMapper;
        this.eleveRepository = eleveRepository;
    }

    /**
     * Save a absence.
     *
     * @param absenceDTO the entity to save.
     * @return the persisted entity.
     */
    public AbsenceDTO save(AbsenceDTO absenceDTO) {
        log.debug("Request to save Absence : {}", absenceDTO);
        Absence absence = absenceMapper.toEntity(absenceDTO);
        absence = absenceRepository.save(absence);
        return absenceMapper.toDto(absence);
    }

    /**
     * Update a absence.
     *
     * @param absenceDTO the entity to save.
     * @return the persisted entity.
     */
    public AbsenceDTO update(AbsenceDTO absenceDTO) {
        log.debug("Request to update Absence : {}", absenceDTO);
        Absence absence = absenceMapper.toEntity(absenceDTO);
        absence = absenceRepository.save(absence);
        return absenceMapper.toDto(absence);
    }

    /**
     * Partially update a absence.
     *
     * @param absenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AbsenceDTO> partialUpdate(AbsenceDTO absenceDTO) {
        log.debug("Request to partially update Absence : {}", absenceDTO);

        return absenceRepository
            .findById(absenceDTO.getId())
            .map(existingAbsence -> {
                absenceMapper.partialUpdate(existingAbsence, absenceDTO);

                return existingAbsence;
            })
            .map(absenceRepository::save)
            .map(absenceMapper::toDto);
    }

    /**
     * Get all the absences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AbsenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Absences");
        Page<AbsenceDTO> padto = absenceRepository.findAll(pageable).map(absenceMapper::toDto);

        for (int i = 0; i < padto.getSize(); i++) padto.forEach(e -> {
            Optional<EleveDTO> edto = this.eleveRepository.findById(e.getEleve().getId()).map(eleveMapper::toDto);
            e.setEleve(edto.get());
        });

        return padto;
    }

    /**
     * Get one absence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AbsenceDTO> findOne(Long id) {
        log.debug("Request to get Absence : {}", id);
        return absenceRepository.findById(id).map(absenceMapper::toDto);
    }

    /**
     * Delete the absence by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Absence : {}", id);
        absenceRepository.deleteById(id);
    }
}
