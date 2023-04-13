package com.guao.manager.service.mapper;

import com.guao.manager.domain.Eleve;
import com.guao.manager.domain.Paiement;
import com.guao.manager.service.dto.EleveDTO;
import com.guao.manager.service.dto.PaiementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {
    @Mapping(target = "eleve", source = "eleve", qualifiedByName = "eleveId")
    PaiementDTO toDto(Paiement s);

    @Named("eleveId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EleveDTO toDtoEleveId(Eleve eleve);
}
