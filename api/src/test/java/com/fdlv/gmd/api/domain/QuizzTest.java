package com.fdlv.gmd.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fdlv.gmd.api.domain.Quizz;
import com.fdlv.gmd.api.web.rest.TestUtil;

import org.junit.jupiter.api.Test;

class QuizzTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quizz.class);
        Quizz quizz1 = new Quizz();
        quizz1.setId(1L);
        Quizz quizz2 = new Quizz();
        quizz2.setId(quizz1.getId());
        assertThat(quizz1).isEqualTo(quizz2);
        quizz2.setId(2L);
        assertThat(quizz1).isNotEqualTo(quizz2);
        quizz1.setId(null);
        assertThat(quizz1).isNotEqualTo(quizz2);
    }
}
