package com.lamp.foundation.base.function.crud.architecture;

import java.util.Map;

import com.lamp.foundation.api.function.crud.architecture.model.LayerInfo;
import com.lamp.foundation.api.function.crud.architecture.model.LayerInfo.LayerNode;

public class CodeCache<T> {

    private Map<String, T> cache;


    public static class LayerferenceRelationship<T> {

        private T origin;

        /**
         *  <pre>
         *      Entity 修改，
         *          1. 关联 Mapper
         *          2. 关联 O
         *          3. 关联 E ，JoinTable
         *      Method 修改，
         *          1. 关联 Layer
         *  </pre>
         */
        private Map<String, LayerferenceRelationship<T>> relationships;

        private LayerInfo layerInfo;

        private LayerNode layerNode;


    }

}
