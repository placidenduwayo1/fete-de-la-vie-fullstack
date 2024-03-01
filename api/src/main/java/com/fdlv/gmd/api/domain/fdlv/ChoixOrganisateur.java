package com.fdlv.gmd.api.domain.fdlv;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fdlv.gmd.api.domain.enumeration.EtatScenario;

@Entity
@Table(name = "fdlv_choix_organisateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChoixOrganisateur implements Serializable {
	private static final long serialVersionUID = -2164820831485187147L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fco_id")
	private Long id;

	@Column(name = "fco_fus_id")
	private Long fcoFusId;

	@ManyToOne
	@JoinColumn(name = "fco_fus_id", insertable = false, updatable = false)
	private FdlvUser fco;

	@Column(name = "fco_num_scenario")
	private Integer numScenario;

	@Column(name = "fco_num_ordre")
	private Integer numOrdre;

	@Column(name = "fco_commentaire", length = 255)
	private String commentaire;

	@Column(name = "fco_date_scenario")
	private LocalDate dateScenario;

	@Column(name = "fco_etat_scenario")
	@Convert(converter = EtatScenarioConverter.class)
	private EtatScenario etatScenario;

	// TODO : fix non existing foreign key
	@Column(name = "fco_theme_id")
	private Long themeId;

	@Column(name = "fco_cp", length = 20)
	private String cp;

	@Column(name = "fco_commune", length = 45)
	private String commune;

	@Column(name = "fco_titre_parcours", length = 45)
	private String titreParcours;

	@Column(name = "fco_description", length = 255)
	private String description;

	@Column(name = "fco_adresse", length = 45)
	private String adresse;

	@Column(name = "fco_logo", length = 150)
	private String urlLogo;

	@Column(name = "fco_label_fin_parcours")
	private String labelFinParcours;

	@Column(name = "fco_fin_parcours_pdf")
	private String finParcoursPdf;

	@Column(name = "fco_teaser", length = 150)
	private String urlTeaser;

	@Column(name = "fco_date_debut_evt")
	private LocalDate dateDebutEvent;

	@Column(name = "fco_date_fin_evt")
	private LocalDate dateFinEvent;

	@Column(name = "fco_info_pratique", length = 255)
	private String infoPratique;

	@Column(name = "fco_date_propose")
	private LocalDate datePropose;

	@Column(name = "fco_date_valide")
	private LocalDate dateValide;

	@Column(name = "fco_fix_order", columnDefinition = "BOOLEAN")
	private Boolean fixOrder;

	@Column(name = "fco_code_parcours", length = 50)
	private String codeParcours;

	@Column(name = "fco_evt_demo", columnDefinition = "BOOLEAN")
	private Boolean evenementDemo;

	@Column(name = "fco_other_event", columnDefinition = "BOOLEAN")
	private Boolean autreEvenement;

	@Column(name = "fco_arch_status", columnDefinition = "BOOLEAN")
	private Boolean eventArchStatus;

	@Column(name = "fco_arch_date")
	private LocalDate dateArchEvent;

	@Column(name = "event_latitude", nullable = false)
	private Double evtLatitude;

	@Column(name = "event_longitude", nullable = false)
	private Double evtLongitude;

	public Double getEvtLatitude() {
		return evtLatitude;
	}

	public void setEvtLatitude(Double evtLatitude) {
		this.evtLatitude = evtLatitude;
	}

	public Double getEvtLongitude() {
		return evtLongitude;
	}

	public void setEvtLongitude(Double evtLongitude) {
		this.evtLongitude = evtLongitude;
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

	public Long getFcoFusId() {
		return fcoFusId;
	}

	public void setFcoFusId(Long fcoFusId) {
		this.fcoFusId = fcoFusId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FdlvUser getFco() {
		return fco;
	}

	public void setFco(FdlvUser fco) {
		this.fco = fco;
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

	public EtatScenario getEtatScenario() {
		return etatScenario;
	}

	public void setEtatScenario(EtatScenario etatScenario) {
		this.etatScenario = etatScenario;
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

}
