package com.lamp.foundation.api.extension.conversion;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author hahaha
 */
public interface ConversionFactory<T, V, KV> {

    @SuppressWarnings("unchecked")
    default Map<KV, BidirectionalConversion<T, V>> defaultOf(KV defaultKey, KV... declaredKeys) {
        Map<KV, BidirectionalConversion<T, V>> map = new HashMap<>();
        if (Objects.nonNull(defaultKey)) {
            BidirectionalConversion<T, V> conversion = of(defaultKey);
            map.put(defaultKey, conversion);
            map.put(null, conversion);
        }
        if (Objects.nonNull(declaredKeys)) {
            for (KV declaredKey : declaredKeys) {
                map.put(declaredKey, of(declaredKey));
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    default Map<KV, BidirectionalConversion<T, V>> defaultOf(KV defaultKey, Collection<KV> declaredKeys) {
        Map<KV, BidirectionalConversion<T, V>> map = defaultOf(defaultKey);
        if (Objects.nonNull(declaredKeys) && !declaredKeys.isEmpty()) {
            declaredKeys.forEach(declaredKey -> map.put(declaredKey, of(declaredKey)));
        }
        return map;
    }

    default Map<KV, BidirectionalConversion<T, V>> defaultOf() {
        return Collections.emptyNavigableMap();
    }

    BidirectionalConversion<T, V> of(KV source);

}
