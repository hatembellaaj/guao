package com.guao.manager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InscrptionMapperTest {

    private InscrptionMapper inscrptionMapper;

    @BeforeEach
    public void setUp() {
        inscrptionMapper = new InscrptionMapperImpl();
    }
}
