package com.fdlv.gmd.api.dto.fdlv;

import com.fdlv.gmd.api.domain.enumeration.ResponseType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Question} entity.
 */
public class FDLVQuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    @NotNull
    private String text;

    @NotNull
    private String type;

    private Long quizz_id;

    private Integer num_order;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public Integer getNum_order() {
        return num_order;
    }

    public void setNum_order(Integer num_order) {
        this.num_order = num_order;
    }

    public Long getQuizz_id() {
        return quizz_id;
    }

    public void setQuizz_id(Long quizz_id) {
        this.quizz_id = quizz_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FDLVQuestionDTO)) {
            return false;
        }

        FDLVQuestionDTO questionDTO = (FDLVQuestionDTO) o;
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
        return "QuestionByQuizzDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", text='" + getText() + "'" +
            ", type='" + getType() + "'" +
            ", quizzId=" + getQuizz_id() +"'" +
            ", num_order=" + getNum_order() +
            "}";
    }
}
