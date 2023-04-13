package com.guao.manager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.guao.manager.domain.Note} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NoteDTO implements Serializable {

    private Long id;

    @NotNull
    private Double noteexamen;

    private String remarque;

    private EleveDTO eleve;

    private ExamenDTO examen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNoteexamen() {
        return noteexamen;
    }

    public void setNoteexamen(Double noteexamen) {
        this.noteexamen = noteexamen;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public EleveDTO getEleve() {
        return eleve;
    }

    public void setEleve(EleveDTO eleve) {
        this.eleve = eleve;
    }

    public ExamenDTO getExamen() {
        return examen;
    }

    public void setExamen(ExamenDTO examen) {
        this.examen = examen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoteDTO)) {
            return false;
        }

        NoteDTO noteDTO = (NoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + getId() +
            ", noteexamen=" + getNoteexamen() +
            ", remarque='" + getRemarque() + "'" +
            ", eleve=" + getEleve() +
            ", examen=" + getExamen() +
            "}";
    }
}
