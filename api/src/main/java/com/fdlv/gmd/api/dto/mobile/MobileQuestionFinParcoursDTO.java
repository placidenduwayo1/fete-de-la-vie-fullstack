package com.fdlv.gmd.api.dto.mobile;

import java.io.Serializable;

public class MobileQuestionFinParcoursDTO implements Serializable {

    private Long id;

    private String texte;

    private Long numQuestion;

    private String Question;

    private boolean questionActive;

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
