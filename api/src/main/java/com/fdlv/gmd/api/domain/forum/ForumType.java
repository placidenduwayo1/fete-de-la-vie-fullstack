package com.fdlv.gmd.api.domain.forum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="forum_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ForumType implements Serializable  {
    
    private static final long serialVersionUID=1L;

    @Id
    @Column(name="tfm_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="tfm_code_type_forum", length=5, nullable=true, unique=false, columnDefinition="CHAR(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String code;

    @Column(name="tfm_type_forum", length=50, nullable=true, unique=false, columnDefinition="VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String type;
    
//    @OneToMany(mappedBy = "typeForum")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "typeForum" }, allowSetters = true)
//    private Set<Forum> forums =new HashSet<>();

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public Set<Forum> getForums() {
//        return forums;
//    }
//
//    public void setForums(Set<Forum> forums) {
//        this.forums = forums;
//    }
    
    @Override
    public int hashCode() {
       return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ForumType)) {
            return false;
        }
        return id !=null && id.equals(((ForumType) obj).id);
    }
    @Override
    public String toString() {
        return "ForumType {"+
            "id=" + getId() + 
            ", code='" + getCode() +  "'" +
            ", type='" + getType() +  "'" +
        "}";
    }
}
