package com.fdlv.gmd.api.validators;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fdlv.gmd.api.annotation.DateComparaison;

public class DateComparaisonValidator implements ConstraintValidator<DateComparaison, Object> {

	private String[] datePropertiers;

	private final String LOCALDATETIME = "LocalDateTime";
	private final String LOCALTIME = "LocalTime";

	@Override
	public void initialize(DateComparaison constraintAnnotation) {
		this.datePropertiers = constraintAnnotation.dateProperties();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		if (value == null) {
			return true; // Permettre les valeurs nulles ou vides
		}

		final LocalDateTime[] date = new LocalDateTime[this.datePropertiers.length];
		final LocalTime[] time = new LocalTime[this.datePropertiers.length];

		try {

			int compteur = 0;
			String typeAComparer = "";

			for (final String propertyName : this.datePropertiers) {
				final Object dateValue = this.getPropertValue(value, propertyName);

				// Lorsque les valeurs sont des dates
				if (dateValue instanceof LocalDateTime) {
					date[compteur] = (LocalDateTime) dateValue;
					typeAComparer = this.LOCALDATETIME;
				}

				// Lorsque les valeurs sont des heures
				if (dateValue instanceof LocalTime) {
					time[compteur] = (LocalTime) dateValue;
					typeAComparer = this.LOCALTIME;
				}
				compteur++;
			}

			switch (typeAComparer) {
			case LOCALDATETIME:
				final LocalDateTime dateDebut = date[0];
				final LocalDateTime dateFin = date[1];
				return this.comparerDeuxDates(dateDebut, dateFin);
			case LOCALTIME:
				final LocalTime heureDebut = time[0];
				final LocalTime heureFin = time[1];
				return this.comparerDeuxHeures(heureDebut, heureFin);
			default:
				return true;
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| IntrospectionException e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean comparerDeuxDates(final LocalDateTime dateDebut, final LocalDateTime dateFin) {
		if (dateDebut == null || dateFin == null) {
			return true;
		} else {
			return dateFin.isAfter(dateDebut);
		}
	}

	private boolean comparerDeuxHeures(final LocalTime heureDebut, final LocalTime heureFin) {
		if (heureDebut == null || heureFin == null) {
			return true;
		} else {
			return heureFin.isAfter(heureDebut);
		}
	}

	private PropertyDescriptor getPropertyDescriptor(BeanInfo beanInfo, String propertyName) {
		final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		final Stream<PropertyDescriptor> streamPropertyDescriptors = Stream.of(propertyDescriptors);
		return streamPropertyDescriptors.filter(propertyDescriptor -> propertyDescriptor.getName().equals(propertyName))
				.findFirst().orElse(null);
	}

	private Object getPropertValue(Object value, String propertyName)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final BeanInfo beanInfo = Introspector.getBeanInfo(value.getClass());
		final PropertyDescriptor propertyDescriptor = this.getPropertyDescriptor(beanInfo, propertyName);
		if (propertyDescriptor == null) {
			throw new IllegalArgumentException("Invalid property name: " + propertyName);
		}

		return propertyDescriptor.getReadMethod().invoke(value, new Object[0]);
	}

}
