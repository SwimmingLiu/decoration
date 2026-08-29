package com.lamp.foundation.base.extension.conversion.string;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.lamp.foundation.api.extension.conversion.BidirectionalConversion;
import com.lamp.foundation.api.extension.conversion.Conversion;
import com.lamp.foundation.api.extension.conversion.ConversionFactory;
import com.lamp.foundation.base.lang.time.LocalDateTimeUtils;

/**
 * @author hahaha
 */
public class StringToLocalDateTimeFactory implements ConversionFactory<String, LocalDateTime, DateTimeFormatter> {

    @Override
    public Map<DateTimeFormatter, BidirectionalConversion<String, LocalDateTime>> defaultOf() {
        return defaultOf(LocalDateTimeUtils.DEFAULT_FORMATTER);
    }

    @Override
    public BidirectionalConversion<String, LocalDateTime> of(DateTimeFormatter source) {
        return new BidirectionalConversion<>() {

            @Override
            public Conversion<String, LocalDateTime> to() {
                return (s) -> s.format(source);
            }

            @Override
            public Conversion<LocalDateTime, String> from() {
                return (s) -> LocalDateTime.parse(s, source);
            }
        };
    }
}
