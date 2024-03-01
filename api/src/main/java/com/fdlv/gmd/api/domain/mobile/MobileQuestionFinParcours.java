package com.fdlv.gmd.api.domain.mobile;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;

@Entity
@Table(name = "mobile_qfin_parcours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MobileQuestionFinParcours implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qfp_id")
    private Long id;

    @Column(name = "qfp_texte")
    private String texte;

    @Column(name = "qfp_num_question")
    private Long numQuestion;

    @Column(name = "qfp_question")
    private String Question;

    @Column(name = "qfp_questionactive")
    private boolean questionActive;

    @Column(name = "qfp_type_question")
    private String typeQuestion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Long getNumQuestion() {
        return numQuestion;
    }

    public void setNumQuestion(Long numQuestion) {
        this.numQuestion = numQuestion;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public boolean isQuestionActive() {
        return questionActive;
    }

    public void setQuestionActive(boolean questionActive) {
        this.questionActive = questionActive;
    }

    public String getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(String typeQuestion) {
        this.typeQuestion = typeQuestion;
    }
}
