package com.fdlv.gmd.api.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

import com.fdlv.gmd.api.domain.enumeration.ResponseType;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Question} entity.
 */
public class QuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private String text;

    @NotNull
    private ResponseType type;

    private QuizzDTO quizz;

    private Integer order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public QuizzDTO getQuizz() {
        return quizz;
    }

    public void setQuizz(QuizzDTO quizz) {
        this.quizz = quizz;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionDTO)) {
            return false;
        }

        QuestionDTO questionDTO = (QuestionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, questionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", text='" + getText() + "'" +
            ", type='" + getType() + "'" +
            ", quizz=" + getQuizz() +
            "}";
    }
}
