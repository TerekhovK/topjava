package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by User on 003 03.09.17.
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public LocalDateTime convert(String s) {
        if(s==null||s==""){
            return null;
        }
        return LocalDateTime.from(formatter.parse(s));
    }
}
