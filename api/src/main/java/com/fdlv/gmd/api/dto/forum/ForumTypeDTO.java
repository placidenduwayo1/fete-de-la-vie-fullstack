package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class ForumTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;

    private String code;

    private String type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ForumTypeDTO)) {
            return false;
        }

        ForumTypeDTO forumtypeDTO = (ForumTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, forumtypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "ForumTypeDTO {"+
            "id=" + getId() + 
            ", code='" + getCode() +  "'" +
            ", type='" + getType() +  "'" +
        "}";
    }
}
