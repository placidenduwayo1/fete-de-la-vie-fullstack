package com.fdlv.gmd.api.dto;

import com.fdlv.gmd.api.domain.Stage;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Quizz} entity.
 */
public class QuizzDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    private Long stage_id;

    private Set<QuestionDetailsDTO> questions;

    public Set<StageCustomDTO> getStages() {
        return stages;
    }

    public void setStages(Set<StageCustomDTO> stages) {
        this.stages = stages;
    }

    private Set<StageCustomDTO> stages;

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

    public Set<QuestionDetailsDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionDetailsDTO> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuizzDetailsDTO)) {
            return false;
        }

        QuizzDetailsDTO quizzDTO = (QuizzDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quizzDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuizzDetailsDTO{" +
            "id=" + id +
            ", label='" + label + '\'' +
            ", questions=" + questions +
            '}';
    }

    public Long getStage_id() {
        return stage_id;
    }

    public void setStage_id(Long stage_id) {
        this.stage_id = stage_id;
    }
}
