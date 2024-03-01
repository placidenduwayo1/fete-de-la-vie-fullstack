package com.fdlv.gmd.api.domain.forum;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "forum_module_applicatif")
public class ModuleApplicatif implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fam_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fam_code_module", length = 20, columnDefinition = "varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Code Module applicatif'")
	private String codeModule;

	@Column(name = "fam_module", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Libellé du module aapplicatif'")
	private String module;

	// L'autre direction :

//	@OneToMany(mappedBy = "moduleApplicatif")
//	@JsonIgnoreProperties()
//	private Set<ActeurHabilitationModule> acteurHabilitationModule = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setFam_id(Long famId) {
		this.id = famId;
	}

	public String getCodeModule() {
		return codeModule;
	}

	public void setCodeModule(String famCodeModule) {
		this.codeModule = famCodeModule;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String famModule) {
		this.module = famModule;
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		
		return "Module applicatif{" +
          "id=" + getId() +
        ", code module='" + getCodeModule() + "'" +
        ", module= '" + getModule() + "'";
       
	}
}
