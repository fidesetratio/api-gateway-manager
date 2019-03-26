package com.app.manager.converter;

import javax.persistence.AttributeConverter;

public class BooleanStringConverter implements AttributeConverter<Boolean,String> {

	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		// TODO Auto-generated method stub
		return (attribute!= null && attribute) ?"Y":"N";
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		// TODO Auto-generated method stub
		return "Y".equals(value);
	}
	

}
