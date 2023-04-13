package com.guao.manager.service.mapper;

import com.guao.manager.domain.Eleve;
import com.guao.manager.domain.Examen;
import com.guao.manager.domain.Note;
import com.guao.manager.service.dto.EleveDTO;
import com.guao.manager.service.dto.ExamenDTO;
import com.guao.manager.service.dto.NoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {
    @Mapping(target = "eleve", source = "eleve", qualifiedByName = "eleveId")
    @Mapping(target = "examen", source = "examen", qualifiedByName = "examenId")
    NoteDTO toDto(Note s);

    @Named("eleveId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EleveDTO toDtoEleveId(Eleve eleve);

    @Named("examenId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExamenDTO toDtoExamenId(Examen examen);
}
