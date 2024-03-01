package com.fdlv.gmd.api.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.domain.enumeration.EtatScenario;
import com.fdlv.gmd.api.domain.fdlv.FdlvUser;

import lombok.Builder;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.fdlv.ChoixOrganisateur} entity.
 */

public class ChoixOrganisateurDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 619314736274664976L;
	@NotNull
	private Long id;
	private Long fcoFusId;
	private Integer numScenario;
	private Integer numOrdre;
	private String commentaire;
	private LocalDate dateScenario;
	private Long themeId;
	private String cp;
	private String commune;
	private String titreParcours;
	private String description;
	private String adresse;
	private String urlLogo;
	private String urlTeaser;
	private LocalDate dateDebutEvent;
	private LocalDate dateFinEvent;
	private String infoPratique;
	private LocalDate datePropose;
	private LocalDate dateValide;
	private Boolean fixOrder;
	private String codeParcours;
	private Boolean evenementDemo;
	private Boolean autreEvenement;
	private Boolean eventArchStatus;
	private LocalDate dateArchEvent;
	private String labelFinParcours;
	private String finParcoursPdf;
	private EtatScenario etatScenario;
	private Integer etatScenarioId;
	private FdlvUser fco;

	private Double evtLongitude;

	private Double evtLatitude;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFcoFusId() {
		return fcoFusId;
	}

	public void setFcoFusId(Long fcoFusId) {
		this.fcoFusId = fcoFusId;
	}

	public Integer getNumScenario() {
		return numScenario;
	}

	public void setNumScenario(Integer numScenario) {
		this.numScenario = numScenario;
	}

	public Integer getNumOrdre() {
		return numOrdre;
	}

	public void setNumOrdre(Integer numOrdre) {
		this.numOrdre = numOrdre;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public LocalDate getDateScenario() {
		return dateScenario;
	}

	public void setDateScenario(LocalDate dateScenario) {
		this.dateScenario = dateScenario;
	}

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public Integer getEtatScenarioId() {
		return etatScenarioId;
	}

	public void setEtatScenarioId(Integer etatScenarioId) {
		this.etatScenarioId = etatScenarioId;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getTitreParcours() {
		return titreParcours;
	}

	public void setTitreParcours(String titreParcours) {
		this.titreParcours = titreParcours;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}

	public String getUrlTeaser() {
		return urlTeaser;
	}

	public void setUrlTeaser(String urlTeaser) {
		this.urlTeaser = urlTeaser;
	}

	public LocalDate getDateDebutEvent() {
		return dateDebutEvent;
	}

	public void setDateDebutEvent(LocalDate dateDebutEvent) {
		this.dateDebutEvent = dateDebutEvent;
	}

	public LocalDate getDateFinEvent() {
		return dateFinEvent;
	}

	public void setDateFinEvent(LocalDate dateFinEvent) {
		this.dateFinEvent = dateFinEvent;
	}

	public String getInfoPratique() {
		return infoPratique;
	}

	public void setInfoPratique(String infoPratique) {
		this.infoPratique = infoPratique;
	}

	public LocalDate getDatePropose() {
		return datePropose;
	}

	public void setDatePropose(LocalDate datePropose) {
		this.datePropose = datePropose;
	}

	public LocalDate getDateValide() {
		return dateValide;
	}

	public void setDateValide(LocalDate dateValide) {
		this.dateValide = dateValide;
	}

	public Boolean getFixOrder() {
		return fixOrder;
	}

	public void setFixOrder(Boolean fixOrder) {
		this.fixOrder = fixOrder;
	}

	public String getCodeParcours() {
		return codeParcours;
	}

	public void setCodeParcours(String codeParcours) {
		this.codeParcours = codeParcours;
	}

	public Boolean getEvenementDemo() {
		return evenementDemo;
	}

	public void setEvenementDemo(Boolean evenementDemo) {
		this.evenementDemo = evenementDemo;
	}

	public Boolean getAutreEvenement() {
		return autreEvenement;
	}

	public void setAutreEvenement(Boolean autreEvenement) {
		this.autreEvenement = autreEvenement;
	}

	public Boolean getEventArchStatus() {
		return eventArchStatus;
	}

	public void setEventArchStatus(Boolean eventArchStatus) {
		this.eventArchStatus = eventArchStatus;
	}

	public LocalDate getDateArchEvent() {
		return dateArchEvent;
	}

	public void setDateArchEvent(LocalDate dateArchEvent) {
		this.dateArchEvent = dateArchEvent;
	}

	public Double getEvtLongitude() {
		return evtLongitude;
	}

	public void setEvtLongitude(Double evtLongitude) {
		this.evtLongitude = evtLongitude;
	}

	public Double getEvtLatitude() {
		return evtLatitude;
	}

	public void setEvtLatitude(Double evtLatitude) {
		this.evtLatitude = evtLatitude;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "ChoixOrganisateurDTO{" +
				"id='" + getId() + "'" +
				", FcoFusId='" + getFcoFusId() + "'" +
				", NumScenario='" + getNumScenario() + "'" +
				", NumOrdre='" + getNumOrdre() + "'" +
				", Commentaire='" + getCommentaire() + "'" +
				", DateScenario='" + getDateScenario() + "'" +
				", DateScenario='" + getDateScenario() + "'" +
				", Cp='" + getCp() + "'" +
				", Commune='" + getCommune() + "'" +
				", TitreParcours='" + getTitreParcours() + "'" +
				", Description='" + getDescription() + "'" +
				", Adresse='" + getAdresse() + "'" +
				", UrlLogo='" + getUrlLogo() + "'" +
				", UrlTeaser='" + getUrlTeaser() + "'" +
				", DateDebutEvent='" + getDateDebutEvent() + "'" +
				", DateFinEvent='" + getDateFinEvent() + "'" +
				", etatScenario='" + getEtatScenario() + "'" +
				", labelFinParcours='" + getLabelFinParcours() + "'" +
				", finParcoursPdf='" + getFinParcoursPdf() + "'" +
				", InfoPratique='" + getInfoPratique() + "'" +
				", DatePropose='" + getDatePropose() + "'" +
				", DateValide='" + getDateValide() + "'" +
				", FixOrder='" + getFixOrder() + "'" +
				", CodeParcours='" + getCodeParcours() + "'" +
				", EvenementDemo='" + getEvenementDemo() + "'" +
				", AutreEvenement='" + getAutreEvenement() + "'" +
				", EventArchStatus='" + getEventArchStatus() + "'" +
				", DateArchEvent='" + getDateArchEvent() +
				"}";
	}

	public String getFusLogin() {
		return "["+
				this.fco.getLogin()+
				"] - "+
				this.id+" - "+
				this.numScenario+" - "+
				this.cp+" - "+
				this.titreParcours;
	}

	public String getLabelFinParcours() {
		return labelFinParcours;
	}

	public void setLabelFinParcours(String labelFinParcours) {
		this.labelFinParcours = labelFinParcours;
	}

	public String getFinParcoursPdf() {
		return finParcoursPdf;
	}

	public void setFinParcoursPdf(String finParcoursPdf) {
		this.finParcoursPdf = finParcoursPdf;
	}

	public EtatScenario getEtatScenario() {
		return etatScenario;
	}

	public void setEtatScenario(EtatScenario etatScenario) {
		this.etatScenario = etatScenario;
	}

	public FdlvUser getFco() {
		return fco;
	}

	public void setFco(FdlvUser fco) {
		this.fco = fco;
	}
}
