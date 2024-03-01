package com.fdlv.gmd.api.domain.forum;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "forum_theme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ForumTheme implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "fth_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fthId;
	@Column(name = "fth_code_theme", nullable = false, length = 10, columnDefinition = "varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
	private String fthCode;
	@Column(name = "fth_libelle_theme", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
    private String fthLibelle;
	@Column(name = "fth_commentaire", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
    private String fthCommentaire;

	public Long getId() {
		return fthId;
	}

	public void setId(Long id) {
		this.fthId = id;
	}

	public String getCode() {
		return fthCode;
	}

	public void setCode(String code) {
		this.fthCode = code;
	}

	public String getLibelle() {
		return fthLibelle;
	}

	public void setLibelle(String libelle) {
		this.fthLibelle = libelle;
	}

	public String getCommentaire() {
		return fthCommentaire;
	}

	public void setCommentaire(String commentaire) {
		this.fthCommentaire = commentaire;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ForumTheme)) {
			return false;
		}
		return fthId !=null && fthId.equals(((ForumTheme) obj).fthId);
	}
	@Override
	public String toString() {
		return "ForumTheme {"+
				"id=" + getId() +
				", code='" + getCode() +  "'" +
				", libelle='" + getLibelle() +  "'" +
				", commentaire='" + getCommentaire() +  "'" +
				"}";
	}
}
