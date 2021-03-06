package com.swust3.spring;

import org.springframework.core.convert.converter.Converter;

public class StringConverter implements Converter<String, String> {
    @Override
    public String convert(String source) {
        if (source != null) {
            source = source.trim();
        }
        if ("".equals(source)) {
            return null;
        } else {
            return source;
        }
    }
}
