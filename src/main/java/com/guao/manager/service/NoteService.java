package com.guao.manager.service;

import com.guao.manager.domain.Note;
import com.guao.manager.repository.ClasseRepository;
import com.guao.manager.repository.EleveRepository;
import com.guao.manager.repository.ExamenRepository;
import com.guao.manager.repository.MatiereRepository;
import com.guao.manager.repository.NoteRepository;
import com.guao.manager.service.dto.ClasseDTO;
import com.guao.manager.service.dto.EleveDTO;
import com.guao.manager.service.dto.ExamenDTO;
import com.guao.manager.service.dto.MatiereDTO;
import com.guao.manager.service.dto.NoteDTO;
import com.guao.manager.service.mapper.EleveMapper;
import com.guao.manager.service.mapper.ExamenMapper;
import com.guao.manager.service.mapper.NoteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Note}.
 */
@Service
@Transactional
public class NoteService {

    private final Logger log = LoggerFactory.getLogger(NoteService.class);

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    private final ExamenRepository examenRepository;

    private final ExamenMapper examenMapper;

    private final EleveRepository eleveRepository;

    private final EleveMapper eleveMapper;

    public NoteService(
        NoteRepository noteRepository,
        ExamenRepository examenRepository,
        ExamenMapper examenMapper,
        EleveRepository eleveRepository,
        EleveMapper eleveMapper,
        NoteMapper noteMapper
    ) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.examenRepository = examenRepository;
        this.examenMapper = examenMapper;
        this.eleveMapper = eleveMapper;
        this.eleveRepository = eleveRepository;
    }

    /**
     * Save a note.
     *
     * @param noteDTO the entity to save.
     * @return the persisted entity.
     */
    public NoteDTO save(NoteDTO noteDTO) {
        log.debug("Request to save Note : {}", noteDTO);
        Note note = noteMapper.toEntity(noteDTO);
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    /**
     * Update a note.
     *
     * @param noteDTO the entity to save.
     * @return the persisted entity.
     */
    public NoteDTO update(NoteDTO noteDTO) {
        log.debug("Request to update Note : {}", noteDTO);
        Note note = noteMapper.toEntity(noteDTO);
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    /**
     * Partially update a note.
     *
     * @param noteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NoteDTO> partialUpdate(NoteDTO noteDTO) {
        log.debug("Request to partially update Note : {}", noteDTO);

        return noteRepository
            .findById(noteDTO.getId())
            .map(existingNote -> {
                noteMapper.partialUpdate(existingNote, noteDTO);

                return existingNote;
            })
            .map(noteRepository::save)
            .map(noteMapper::toDto);
    }

    /**
     * Get all the notes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        Page<NoteDTO> pndto = noteRepository.findAll(pageable).map(noteMapper::toDto);

        for (int i = 0; i < pndto.getSize(); i++) pndto.forEach(e -> {
            Optional<ExamenDTO> exdto = this.examenRepository.findById(e.getExamen().getId()).map(examenMapper::toDto);
            e.setExamen(exdto.get());

            Optional<EleveDTO> edto = this.eleveRepository.findById(e.getEleve().getId()).map(eleveMapper::toDto);
            e.setEleve(edto.get());
        });

        return pndto;
    }

    /**
     * Get one note by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NoteDTO> findOne(Long id) {
        log.debug("Request to get Note : {}", id);
        return noteRepository.findById(id).map(noteMapper::toDto);
    }

    /**
     * Delete the note by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Note : {}", id);
        noteRepository.deleteById(id);
    }
}
