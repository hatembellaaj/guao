package com.guao.manager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.guao.manager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfesseurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Professeur.class);
        Professeur professeur1 = new Professeur();
        professeur1.setId(1L);
        Professeur professeur2 = new Professeur();
        professeur2.setId(professeur1.getId());
        assertThat(professeur1).isEqualTo(professeur2);
        professeur2.setId(2L);
        assertThat(professeur1).isNotEqualTo(professeur2);
        professeur1.setId(null);
        assertThat(professeur1).isNotEqualTo(professeur2);
    }
}
