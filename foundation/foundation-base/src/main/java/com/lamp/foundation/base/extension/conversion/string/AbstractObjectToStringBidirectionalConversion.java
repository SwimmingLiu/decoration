package com.lamp.foundation.base.extension.conversion.string;

import com.lamp.foundation.api.extension.conversion.BidirectionalConversion;
import com.lamp.foundation.api.extension.conversion.Conversion;

public abstract class AbstractObjectToStringBidirectionalConversion<V>
    implements BidirectionalConversion<String, V> {



    @Override
    public Conversion<String, V> to() {
        return Object::toString;
    }




}
