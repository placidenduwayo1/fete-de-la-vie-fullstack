package com.fdlv.gmd.api.domain.forum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "forum_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fdt_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "fdt_numero", precision = 4, scale = 0, columnDefinition = "decimal(4, 0) NULL DEFAULT NULL COMMENT 'Numéro séquentiel du document géré par lurilisateur'")
    private BigDecimal numero;

    @Column(name = "fdt_libelle", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Description du document'")
    private String libelle;

    @Column(name = "fdt_reference", columnDefinition = "varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'Référence du document composée de la concaténation de :\r\nfdt_date_document (sous format YYYYMMJJ)-fdt_id-fdt_numero-fdt_ffo_id-fdt_fse_id-fdt_fac_id-fdt_version_document).\r\nCette référence est générée automatiquement et non modifiable par l\'utilisateur \r\nSi les champs fdt_fse_id fdt_fac_id ne sont pas renseignés (non connus = NULL) alors mettre la valeur ZZ (structure et acteur à prévoir pour le rattachement du document)\r\n'", length = 15)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "fdt_fse_id", nullable = true, foreignKey = @ForeignKey(name = "fk_fdt_fse_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "fse_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Id de la Structure du document'")
    private Structure structure;

    @ManyToOne
    @JoinColumn(name = "fdt_ftd_id", foreignKey = @ForeignKey(name = "fk_fdt_ftd_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "ftd_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Référence le type du document '")
    private DocumentType typeDocument;

    @ManyToOne
    @JoinColumn(name = "fdt_ffo_id", foreignKey = @ForeignKey(name = "fk_fdt_ffo_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "ffo_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Référence l\'ID du Forum'")
    private Forum forum;

    @ManyToOne
    @JoinColumn(name = "fdt_fac_id", foreignKey = @ForeignKey(name = "fk_fdt_fac_id", value = ConstraintMode.CONSTRAINT), referencedColumnName = "fac_id", columnDefinition = "bigint NULL DEFAULT NULL COMMENT 'Acteur responsable du document'")
    private Acteur acteur;

    @Column(name = "fdt_extension_doc", columnDefinition = "varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Extension du Document : .mp4, .mp3, .jpg, .jpeg, .png, .doc, . xls, .pdf., .txt, '", length = 5)
    private String extensionDocument;

    @Column(name = "fdt_document_url", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String urlDocument;

    @Column(name = "fdt_date_document", columnDefinition = "datetime NULL DEFAULT NULL")
    private LocalDateTime dateDocument;

    @Column(name = "fdt_version_document", columnDefinition = "varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'La version du document est fixée par l’utilisateur. La valeur par défaut est V00 (version de document de travail).'", length = 20)
    private String versionDocument;

    // Following Samir's SQL logic
    @Column(name = "fdt_niveau_diffusion", columnDefinition = "varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Niveau diffusion :\r\n1 = Document à diffuser uniquement en interne FDLV (Acteurs Niveau de responsabilité = 1)\r\n2 = Document à diffusion restreinte auprès de certaines structures/acteurs de niveau 2 (Acteurs Niveau de responsabilité 1 et 2)\r\n3 = Document à diffusion restreinte auprès de certaines structures/acteurs de niveau 3 (Acteurs Niveau de responsabilité 1, 2 et 3)\r\n4 = Document à diffusion restreinte auprès de certaines structures/acteurs de niveaux 4 (Acteurs Niveau de responsabilité 4)\r\n5 = Document à diffusion uniquement aux acteurs intervenant au sein du Forum et aux structures/acteurs de niveau 5 (Acteurs Niveau de responsabilité 5)\r\n9  = Document à diffuser au niveau tout public\r\n\r\n'", length = 1)
    private String niveauDiffusion;

    @Column(name = "fdt_doc_valide", columnDefinition = "varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '= Oui si document validé pour diffusion sinon = Non'", length = 1)
    private String docValide;

    @Column(name = "fdt_commentaire", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL")
    private String commentaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNumero() {
        return numero;
    }

    public void setNumero(BigDecimal numero) {
        this.numero = numero;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public DocumentType getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(DocumentType typeDocument) {
        this.typeDocument = typeDocument;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }

    public String getExtensionDocument() {
        return extensionDocument;
    }

    public void setExtensionDocument(String extensionDocument) {
        this.extensionDocument = extensionDocument;
    }

    public String getUrlDocument() {
        return urlDocument;
    }

    public void setUrlDocument(String urlDocument) {
        this.urlDocument = urlDocument;
    }

    public LocalDateTime getDateDocument() {
        return dateDocument;
    }

    public void setDateDocument(LocalDateTime dateDocument) {
        this.dateDocument = dateDocument;
    }

    public String getVersionDocument() {
        return versionDocument;
    }

    public void setVersionDocument(String versionDocument) {
        this.versionDocument = versionDocument;
    }

    public String getNiveauDiffusion() {
        return niveauDiffusion;
    }

    public void setNiveauDiffusion(String niveauDiffusion) {
        this.niveauDiffusion = niveauDiffusion;
    }

    public String getDocValide() {
        return docValide;
    }

    public void setDocValide(String docValide) {
        this.docValide = docValide;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", numero=" + numero +
                ", libelle='" + libelle + '\'' +
                ", reference='" + reference + '\'' +
                ", structure=" + structure +
                ", typeDocument=" + typeDocument +
                ", forum=" + forum +
                ", acteur=" + acteur +
                ", extensionDocument='" + extensionDocument + '\'' +
                ", urlDocument='" + urlDocument + '\'' +
                ", dateDocument=" + dateDocument +
                ", versionDocument='" + versionDocument + '\'' +
                ", niveauDiffusion='" + niveauDiffusion + '\'' +
                ", docValide='" + docValide + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
