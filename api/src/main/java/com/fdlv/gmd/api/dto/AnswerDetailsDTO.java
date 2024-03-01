package com.fdlv.gmd.api.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Answer} entity.
 */
public class AnswerDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String reponse;

    @NotNull
    private Boolean isCorrect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerDetailsDTO)) {
            return false;
        }

        AnswerDetailsDTO answerDTO = (AnswerDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, answerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerDTO{" +
            "id=" + getId() +
            ", reponse='" + getReponse() + "'" +
            ", isCorrect='" + getIsCorrect() + "'" +
            "}";
    }
}
