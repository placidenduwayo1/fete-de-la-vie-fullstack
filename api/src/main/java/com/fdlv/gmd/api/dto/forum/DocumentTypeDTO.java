package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;

public class DocumentTypeDTO implements Serializable {

    private Long id;
    private String codeTypeDoc;
    private String typeDoc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeDoc() {
        return codeTypeDoc;
    }

    public void setCodeTypeDoc(String codeTypeDoc) {
        this.codeTypeDoc = codeTypeDoc;
    }

    public String getTypeDoc() {
        return typeDoc;
    }

    public void setTypeDoc(String typeDoc) {
        this.typeDoc = typeDoc;
    }

    @Override
    public String toString() {
        return "DocumentTypeDTO{" +
                "id=" + id +
                ", codeTypeDoc='" + codeTypeDoc + '\'' +
                ", typeDoc='" + typeDoc + '\'' +
                '}';
    }
}