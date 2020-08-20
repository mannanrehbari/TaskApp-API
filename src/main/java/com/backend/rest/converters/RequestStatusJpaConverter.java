package com.backend.rest.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.backend.rest.enums.RequestStatus;

@Converter
public class RequestStatusJpaConverter implements AttributeConverter<RequestStatus, String>{

	@Override
	public String convertToDatabaseColumn(RequestStatus attribute) {
		if (attribute == null) {
			return null;			
		}
		return attribute.toString();
	}

	@Override
	public RequestStatus convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;			
		}
		try {
			return RequestStatus.valueOf(dbData);
			
		} catch(IllegalArgumentException e) {
			return null;
		}
	}

}
