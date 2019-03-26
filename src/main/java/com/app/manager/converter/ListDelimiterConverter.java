package com.app.manager.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang3.StringUtils;

public class ListDelimiterConverter implements AttributeConverter<List<String>,String> {

	@Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return StringUtils.join(attribute, ";");
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().length() == 0) {
            return new ArrayList<String>();
        }

        String[] data = dbData.split(",");
        return Arrays.asList(data);
    }

}
