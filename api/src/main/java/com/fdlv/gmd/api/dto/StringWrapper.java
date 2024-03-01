package com.fdlv.gmd.api.dto;

/**
 * Classe de Wrapper bidon pour permettre d'envoyer une réponse HTTP 
 * contenant seulement un String
 *
 */
public class StringWrapper {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
