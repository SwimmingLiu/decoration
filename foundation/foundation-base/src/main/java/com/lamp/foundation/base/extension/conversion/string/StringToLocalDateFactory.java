package com.lamp.foundation.base.extension.conversion.string;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.lamp.foundation.api.extension.conversion.BidirectionalConversion;
import com.lamp.foundation.api.extension.conversion.Conversion;
import com.lamp.foundation.api.extension.conversion.ConversionFactory;
import com.lamp.foundation.base.lang.time.LocalDateTimeUtils;

/**
 * @author hahaha
 */
public class StringToLocalDateFactory implements ConversionFactory<String, LocalDate, DateTimeFormatter> {

    @Override
    public Map<DateTimeFormatter, BidirectionalConversion<String, LocalDate>> defaultOf() {
        return defaultOf(LocalDateTimeUtils.DEFAULT_DATE_FORMATTER);
    }

    @Override
    public BidirectionalConversion<String, LocalDate> of(DateTimeFormatter source) {
        return new BidirectionalConversion<>() {

            @Override
            public Conversion<String, LocalDate> to() {
                return (s) -> s.format(source);
            }

            @Override
            public Conversion<LocalDate, String> from() {
                return (s) -> LocalDate.parse(s, source);
            }
        };
    }

}
