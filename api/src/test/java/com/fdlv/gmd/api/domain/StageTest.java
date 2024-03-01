package com.fdlv.gmd.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fdlv.gmd.api.domain.Stage;
import com.fdlv.gmd.api.web.rest.TestUtil;

import org.junit.jupiter.api.Test;

class StageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stage.class);
        Stage stage1 = new Stage();
        stage1.setId(1L);
        Stage stage2 = new Stage();
        stage2.setId(stage1.getId());
        assertThat(stage1).isEqualTo(stage2);
        stage2.setId(2L);
        assertThat(stage1).isNotEqualTo(stage2);
        stage1.setId(null);
        assertThat(stage1).isNotEqualTo(stage2);
    }
}
