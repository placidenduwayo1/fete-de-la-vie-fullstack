package com.fdlv.gmd.api.domain.forum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
@Table(name = "forum_role_structure")
public class RoleStructure implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fro_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long froId;

	@Column(name = "fro_code", nullable = false, length = 10, columnDefinition = "varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Code Rôle de la Structure qui intervient'")
    private String froCode;

	@Column(name = "fro_role", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Rôle de la Structure qui intervient (Structure Acceuillante, ...)'")
    private String froRole;
}
