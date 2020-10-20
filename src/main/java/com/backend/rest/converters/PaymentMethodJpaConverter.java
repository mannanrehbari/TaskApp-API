package com.backend.rest.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.backend.rest.enums.PaymentMethod;

@Converter
public class PaymentMethodJpaConverter implements AttributeConverter<PaymentMethod, String>{

	@Override
	public String convertToDatabaseColumn(PaymentMethod attribute) {
		if (attribute == null) {
			return null;			
		}
		return attribute.toString();
	}

	@Override
	public PaymentMethod convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;			
		}
		try {
			return PaymentMethod.valueOf(dbData);
			
		} catch(IllegalArgumentException e) {
			return null;
		}
	}

}
