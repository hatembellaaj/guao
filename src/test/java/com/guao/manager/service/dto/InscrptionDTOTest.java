package com.guao.manager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guao.manager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InscrptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InscrptionDTO.class);
        InscrptionDTO inscrptionDTO1 = new InscrptionDTO();
        inscrptionDTO1.setId(1L);
        InscrptionDTO inscrptionDTO2 = new InscrptionDTO();
        assertThat(inscrptionDTO1).isNotEqualTo(inscrptionDTO2);
        inscrptionDTO2.setId(inscrptionDTO1.getId());
        assertThat(inscrptionDTO1).isEqualTo(inscrptionDTO2);
        inscrptionDTO2.setId(2L);
        assertThat(inscrptionDTO1).isNotEqualTo(inscrptionDTO2);
        inscrptionDTO1.setId(null);
        assertThat(inscrptionDTO1).isNotEqualTo(inscrptionDTO2);
    }
}
