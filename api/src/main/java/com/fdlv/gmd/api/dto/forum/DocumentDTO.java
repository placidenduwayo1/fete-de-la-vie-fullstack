package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DocumentDTO implements Serializable {

    private Long id;
    private BigDecimal numero;
    private String libelle;
    private String reference;
    private StructureDTO structure;
    private DocumentTypeDTO typeDocument;
    private ForumDTO forum;
    private ActeurDTO acteur;
    private String extensionDocument;
    private String urlDocument;
    private LocalDateTime dateDocument;
    private String versionDocument;
    private String niveauDiffusion;
    private String docValide;
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

    public StructureDTO getStructure() {
        return structure;
    }

    public void setStructure(StructureDTO structure) {
        this.structure = structure;
    }

    public DocumentTypeDTO getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(DocumentTypeDTO typeDocument) {
        this.typeDocument = typeDocument;
    }

    public ForumDTO getForum() {
        return forum;
    }

    public void setForum(ForumDTO forum) {
        this.forum = forum;
    }

    public ActeurDTO getActeur() {
        return acteur;
    }

    public void setActeur(ActeurDTO acteur) {
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
        return "DocumentDTO{" +
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
