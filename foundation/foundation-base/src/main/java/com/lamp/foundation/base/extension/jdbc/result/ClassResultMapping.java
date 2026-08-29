package com.lamp.foundation.base.extension.jdbc.result;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lamp.foundation.base.extension.jdbc.type.TypeHandlerService;
import com.lamp.foundation.base.lang.lock.ConciseLock;
import com.lamp.foundation.base.lang.util.string.NamingUtils;
import com.lamp.foundation.base.lang.util.WrapperConverter;

import lombok.Setter;


/**
 * <pre>
 *  自动识别，是否有变量名，如果有变量名就用，每个类识别一次还是识别一次
 *  两个模式，按需加载
 * </pre>
 */
public class ClassResultMapping {

    private final ConciseLock<Map<String, FieldMapping>> fieldMappingConciseLock = ConciseLock.of(this::analysisFieldMappings);

    private List<ConstructionMapping> constructionMappings;

    private ConstructionMapping constructionMapping;


    private boolean recognizeParameterName = false;

    private boolean isConstructor = false;


    @Setter
    private Class<?> clazz;

    @Setter
    private TypeHandlerService typeHandlerService;

    private Map<String, FieldMapping> analysisFieldMappings() {
        Map<String, FieldMapping> fieldMappings = new HashMap<>();
        Field[] fields = FieldUtils.getAllFields(clazz);
        try {
            for (Field field : fields) {

                String methodName = NamingUtils.buildSetMethodName(field.getName());
                Method method = clazz.getMethod(methodName, field.getType());

                FieldMapping fieldMapping = new FieldMapping();
                fieldMapping.setMethod(method);
                fieldMapping.setType(field.getType());
                fieldMapping.setFieldName(field.getName());
                fieldMapping.setColumnName(NamingUtils.camelCaseToLowerCamelCase(field.getName()));
                if (WrapperConverter.isBooleanWrapperAndPrimitive(field.getType())) {
                    fieldMapping.setColumnName("is_" + fieldMapping.getColumnName());
                }
                fieldMappings.put(fieldMapping.getColumnName(), fieldMapping);
                fieldMapping.setTypeHandler(this.typeHandlerService.getTypeHandler(field.getType()));
            }
            return fieldMappings;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    public Map<String, FieldMapping> getStringFieldMappingMap() {
        return this.fieldMappingConciseLock.get();
    }
}
