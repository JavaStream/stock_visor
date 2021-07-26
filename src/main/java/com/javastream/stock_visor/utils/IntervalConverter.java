package com.javastream.stock_visor.utils;

import com.javastream.stock_visor.model.Interval;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class IntervalConverter implements AttributeConverter<Interval, String> {

    @Override
    public String convertToDatabaseColumn(Interval interval) {
        if (interval == null) {
            return null;
        }
        return interval.getValue();
    }

    @Override
    public Interval convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Interval.getByValue(value);
    }
}
