package com.fdlv.gmd.api.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fdlv.gmd.api.validators.DateComparaisonValidator;

@Documented
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Constraint(validatedBy = DateComparaisonValidator.class)
@Retention(RUNTIME)
public @interface DateComparaison {

	String message() default "la date de fin doit être postérieure à la date de début";

	/**
	 * @return un tableau de 2 LocalDateTime avec 1er élement : Date de début et
	 *         2eme élément : Date de fin
	 */
	String[] dateProperties();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
