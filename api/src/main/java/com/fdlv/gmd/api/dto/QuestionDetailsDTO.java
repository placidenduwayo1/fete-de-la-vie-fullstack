package com.fdlv.gmd.api.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.domain.enumeration.ResponseType;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Question} entity.
 */
public class QuestionDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private String text;

    @NotNull
    private ResponseType type;

    private Set<AnswerDetailsDTO> answers;

    private Integer order;

    private QuizzDTO quizz;

    public QuizzDTO getQuizz() {
        return quizz;
    }

    public void setQuizz(QuizzDTO quizz) {
        this.quizz = quizz;
    }

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

    public Set<AnswerDetailsDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerDetailsDTO> answers) {
        this.answers = answers;
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
        if (!(o instanceof QuestionDetailsDTO)) {
            return false;
        }

        QuestionDetailsDTO questionDTO = (QuestionDetailsDTO) o;
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
        return "QuestionDetailsDTO{" +
            "id=" + id +
            ", label='" + label + '\'' +
            ", text='" + text + '\'' +
            ", type=" + type +
            ", quizz=" + getQuizz() +
            ", answers=" + answers +
            '}';
    }
}
