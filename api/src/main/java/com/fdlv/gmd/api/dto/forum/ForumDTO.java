package com.fdlv.gmd.api.dto.forum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import com.fdlv.gmd.api.annotation.DateComparaison;
import com.fdlv.gmd.api.annotation.DateComparaisons;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DateComparaisons({ @DateComparaison(dateProperties = { "dateDebutEvenement", "dateFinEvenement" },
		message = "dateDebutEvenement|dateFinEvenement : La date de fin de l'évènement doit être postérieure à la date de début de l'évènement."),
		@DateComparaison(dateProperties = { "dateDebutReservation", "dateFinReservation" },
		message = "dateDebutReservation|dateFinReservation : La date de fin réservation doit être postérieure à la date de début réservation."),
		@DateComparaison(dateProperties = { "dateOuverture", "dateFermeture" },
				message = "dateOuverture|dateFermeture : La date de fermeture doit être postérieure à la date d'ouverture.")})

public class ForumDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull(message = "numForum : Le numero de forum est obligatoire.")
	@Min(value = 1, message = "numForum : Le numéro forum doit être au minimum 1.")
	@Max(value = 99999, message = "numForum : Le numéro forum doit être inférieur ou égal à 99999.")
	private BigDecimal numForum;

	@NotEmpty(message = "libelle : Le libelle du forum est obligatoire.")
	@Length(min = 1, max = 255, message = "libelle : Le nombre de caractère du libelle du forum doit être inférieur ou égal à 255 et suppérieur ou égal 5.")
	private String libelle;

	private String reference;

	@NotEmpty(message = "typeForum : Le type de forum est obligatoire.")
	@Length(max = 10, message = "typeForum : Le nombre de caractère du type de forum doit être inférieur ou égal à 10.")
	private String typeForum;

	@Length(max = 255, message = "sloganForum :  Le nombre de caractère du slogan de forum doit être inférieur ou égal à 255.")
	private String sloganForum;

	@NotNull(message = "dateDebutEvenement : La date de début de l'évènement est obligatoire.")
	//@Future(message = "dateDebutEvenement : La date de début de l'évènement doit être postérieure à la date d'aujourd'hui.")
	private LocalDateTime dateDebutEvenement;

	//@Future(message = "dateFinEvenement : La date de fin de l'évènement doit être postérieure à la date d'aujourd'hui.")
	private LocalDateTime dateFinEvenement;

	//@Future(message = "dateDebutReservation : La date de début de réservation doit être postérieure à la date d'aujourd'hui.")
	private LocalDateTime dateDebutReservation;

	//@Future(message = "dateFinReservation : La date de fin de réservation doit être postérieure à la date d'aujourd'hui.")
	private LocalDateTime dateFinReservation;

	//@Future(message = "dateOuverture : La date d'ouverture doit être postérieure à la date d'aujourd'hui.")
	private LocalDate dateOuverture;

	//@Future(message = "dateFermeture : La date de fermeture doit être postérieure à la date d'aujourd'hui.")
	private LocalDate dateFermeture;

	private LocalTime heureOuverture;

	private LocalTime heureFermeture;

	@Length(max = 50, message = "lieuForum : Le nombre de caractère du lieu de forum doit être inférieur ou égal à 50.")
	private String lieuForum;

	@Length(max = 100, message = "lieuForum : Le nombre de caractère de l'adresse de forum doit être inférieur ou égal à 100.")
	private String adresseForum;

	@Length(max = 255, message = "complementAdresseForum : Le nombre de caractère du complément adresse forum doit être inférieur ou égal à 255.")
	private String complementAdresseForum;

	@Length(min = 5, max = 5, message = "codePostal : Le nombre de caractère du code postal doit impérativement être égal à 5.")
	private String codePostal;

	@Length(max = 255, message = "commune : Le nombre de caractère de la commune doit être inférieur ou égal à 255.")
	private String commune;

	@Length(max = 100, message = "descriptionConvention : Le nombre de caractère de la description convention doit être inférieur ou égal à 100.")
	private String descriptionConvention;

	@Length(max = 255, message = "conventionUrl : Le nombre de caractère de la convention URL doit être inférieur ou égal à 255.")
	private String conventionUrl;

	@Length(max = 1, message = "isValid : Le nombre de caractère de validation du forum doit être égal à 1.")
	private String isValid;

	@Length(max = 255, message = "planForumUrl : Le nombre de caractère du plan forum doit être inférieur ou égal à 255.")
	private String planForumUrl;

	@Length(max = 255, message = "planSalle : Le nombre de caractère de plan salle doit être inférieur ou égal à 255.")
	private String planSalle;

	@Length(max = 255, message = "commentaire : Le nombre de caractère de commentaire doit être inférieur ou égal à 255.")
	private String commentaire;


}