package com.fdlv.gmd.api.domain.forum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "forum_banniere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Banniere implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ban_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "ban_fth_id")
	private Long forumThemeId;
	@Column(name = "ban_code", nullable = false, length = 20, columnDefinition = "varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
	private String code;
	@Column(name = "ban_libelle", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
    private String libelle;
	@Column(name = "ban_url", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
	private String url;
	@Column(name = "ban_fdlv", nullable = false)
	private String fdlv;
	@Column(name = "ban_commentaire", nullable = false, columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL")
    private String commentaire;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getForumThemeId() {
		return forumThemeId;
	}

	public void setForumThemeId(Long forumThemeId) { this.forumThemeId = forumThemeId;}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFdlv() {
		return fdlv;
	}

	public void setFdlv(String fdlv) {
		this.fdlv = fdlv;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Banniere)) {
			return false;
		}
		return id !=null && id.equals(((Banniere) obj).id);
	}
	@Override
	public String toString() {
		return "ForumTheme {"+
				"  id=" + getId() +
				", forumThemeId=" + getForumThemeId() +
				", code='" + getCode() +  "'" +
				", libelle='" + getLibelle() +  "'" +
				", fdlv='" + getFdlv() +  "'" +
				", url='" + getUrl() +  "'" +
				", commentaire='" + getCommentaire() +  "'" +
				"}";
	}
}
