package com.guao.manager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.guao.manager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InscrptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inscrption.class);
        Inscrption inscrption1 = new Inscrption();
        inscrption1.setId(1L);
        Inscrption inscrption2 = new Inscrption();
        inscrption2.setId(inscrption1.getId());
        assertThat(inscrption1).isEqualTo(inscrption2);
        inscrption2.setId(2L);
        assertThat(inscrption1).isNotEqualTo(inscrption2);
        inscrption1.setId(null);
        assertThat(inscrption1).isNotEqualTo(inscrption2);
    }
}
