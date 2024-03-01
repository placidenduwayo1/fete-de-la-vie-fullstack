package com.fdlv.gmd.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fdlv.gmd.api.dto.QuizzDTO;
import com.fdlv.gmd.api.web.rest.TestUtil;

import org.junit.jupiter.api.Test;

class QuizzDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuizzDTO.class);
        QuizzDTO quizzDTO1 = new QuizzDTO();
        quizzDTO1.setId(1L);
        QuizzDTO quizzDTO2 = new QuizzDTO();
        assertThat(quizzDTO1).isNotEqualTo(quizzDTO2);
        quizzDTO2.setId(quizzDTO1.getId());
        assertThat(quizzDTO1).isEqualTo(quizzDTO2);
        quizzDTO2.setId(2L);
        assertThat(quizzDTO1).isNotEqualTo(quizzDTO2);
        quizzDTO1.setId(null);
        assertThat(quizzDTO1).isNotEqualTo(quizzDTO2);
    }
}
