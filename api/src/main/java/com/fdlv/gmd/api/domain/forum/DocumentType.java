package com.fdlv.gmd.api.domain.forum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "forum_type_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftd_id", nullable = false)
    private Long id;

    @Column(name = "fdt_code_type_doc", nullable = false, length = 10, columnDefinition = "varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
    private String codeTypeDoc;

    @Column(name = "fdt_type_doc", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
    private String typeDoc;


    // Inverse Direction:
//    @OneToMany(mappedBy = "typeDocument")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "typeDocument"}, allowSetters = true)
//    private Set<Document> documents = new HashSet<>();


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
        return "DocumentType{" +
                "id=" + id +
                ", codeTypeDoc='" + codeTypeDoc + '\'' +
                ", typeDoc='" + typeDoc + '\'' +
                '}';
    }
}
