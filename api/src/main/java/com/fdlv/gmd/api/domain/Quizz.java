package com.fdlv.gmd.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Quizz.
 */
@Entity
@Table(name = "quizz")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quizz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "stage_id",nullable = true)
    private Long stage_id;

    @OneToMany(mappedBy = "quizz")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"event", "quizz"}, allowSetters = true)
    private Set<Stage> stages = new HashSet<>();

    @OneToMany(mappedBy = "quizz", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER)
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"quizz", "answers"}, allowSetters = true)
    private Set<Question> questions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quizz id(Long id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Quizz label(String label) {
        this.label = label;
        return this;
    }

    public Long getStage_id() {
        return stage_id;
    }

    public void setStage_id(Long stage_id) {
        this.stage_id = stage_id;
    }

    public Set<Stage> getStages() {
        return this.stages;
    }

    public void setStages(Set<Stage> stages) {
        if (this.stages != null) {
            this.stages.forEach(i -> i.setQuizz(null));
        }
        if (stages != null) {
            stages.forEach(i -> i.setQuizz(this));
        }
        this.stages = stages;
    }

    public Quizz stages(Set<Stage> stages) {
        this.setStages(stages);
        return this;
    }

    public Quizz addStage(Stage stage) {
        this.stages.add(stage);
        stage.setQuizz(this);
        return this;
    }

    public Quizz removeStage(Stage stage) {
        this.stages.remove(stage);
        stage.setQuizz(null);
        return this;
    }

    public Set<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(Set<Question> questions) {
        if (this.questions != null) {
            this.questions.forEach(i -> i.setQuizz(null));
        }
        if (questions != null) {
            questions.forEach(i -> i.setQuizz(this));
        }
        this.questions = questions;
    }

    public Quizz questions(Set<Question> questions) {
        this.setQuestions(questions);
        return this;
    }

    public Quizz addQuestion(Question question) {
        this.questions.add(question);
        question.setQuizz(this);
        return this;
    }

    public Quizz removeQuestion(Question question) {
        this.questions.remove(question);
        question.setQuizz(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quizz)) {
            return false;
        }
        return id != null && id.equals(((Quizz) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quizz{" +
                "id=" + getId() +
                ", label='" + getLabel() + "'" +
                "}";
    }
}
