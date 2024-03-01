package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;
public class ModuleApplicatifDTO implements Serializable {

    private Long id;
    private String codeModule;
    private String module;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeModule() {
        return codeModule;
    }

    public void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "ModuleApplicatifDTO{" +
                "id=" + id +
                ", codeModule='" + codeModule + '\'' +
                ", module='" + module + '\'' +
                '}';
    }
}