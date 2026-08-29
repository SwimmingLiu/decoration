package com.lamp.foundation.base.extension.conversion.string;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.lamp.foundation.api.extension.conversion.BidirectionalConversion;
import com.lamp.foundation.api.extension.conversion.Conversion;
import com.lamp.foundation.api.extension.conversion.ConversionFactory;
import com.lamp.foundation.base.lang.time.LocalDateTimeUtils;

/**
 * @author hahaha
 */
public class StringToLocalTimeFactory implements ConversionFactory<String, LocalTime, DateTimeFormatter> {


    @Override
    public Map<DateTimeFormatter, BidirectionalConversion<String, LocalTime>> defaultOf() {
        return defaultOf(LocalDateTimeUtils.DEFAULT_FORMATTER);
    }

    @Override
    public BidirectionalConversion<String, LocalTime> of(DateTimeFormatter source) {
        return new BidirectionalConversion<>() {

            @Override
            public Conversion<String, LocalTime> to() {
                return (s) -> s.format(source);
            }

            @Override
            public Conversion<LocalTime, String> from() {
                return (s) -> LocalTime.parse(s, source);
            }
        };
    }
}