package com.lamp.foundation.base.extension.jdbc.type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.extension.jdbc.TypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.BigDecimalTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.BigIntegerTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.BooleanTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.ByteTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.CharacterTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.DoubleTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.FloatTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.IntegerTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.LongTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.base.ShortTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.stream.BlobTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.stream.ByteArrayTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.stream.ByteObjectArrayTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.string.ClobReaderTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.string.ClobTypeHandler;
import com.lamp.foundation.base.extension.jdbc.type.string.StringTypeHandler;
import com.lamp.foundation.base.lang.util.WrapperConverter;

public class TypeHandlerService {


    private final Map<Class<?>, TypeHandler<?>> typeHandlerMap = new HashMap<>();

    private final Map<Class<?>, Map<Integer, TypeHandler<?>>> classTypeHandler = new HashMap<>();

    private final Map<Integer, TypeHandler<?>> typesTypeHandler = new HashMap<>();

    public TypeHandlerService() {
        this.init();
    }

    private void init() {
        this.register(BigDecimalTypeHandler.class);
        this.register(BigIntegerTypeHandler.class);
        this.register(BooleanTypeHandler.class);
        this.register(ByteTypeHandler.class);
        this.register(DoubleTypeHandler.class);
        this.register(FloatTypeHandler.class);
        this.register(IntegerTypeHandler.class);
        this.register(LongTypeHandler.class);
        this.register(ShortTypeHandler.class);
        this.register(CharacterTypeHandler.class);

        this.register(BlobTypeHandler.class);
        this.register(ByteArrayTypeHandler.class);
        this.register(ByteObjectArrayTypeHandler.class);
        this.register(IntegerTypeHandler.class);

        this.register(ClobReaderTypeHandler.class);
        this.register(ClobTypeHandler.class);
        this.register(StringTypeHandler.class);

        this.register(new ClassTypeHandler<LocalDateTime>() {
        });
        this.register(new ClassTypeHandler<LocalDate>() {
        });
        this.register(new ClassTypeHandler<LocalTime>() {
        });
        this.register(new ClassTypeHandler<OffsetDateTime>() {
        });
        this.register(new ClassTypeHandler<OffsetTime>() {
        });
        this.register(new ClassTypeHandler<ZonedDateTime>() {
        });
        this.register(new ClassTypeHandler<Month>() {
        });
        this.register(new ClassTypeHandler<Year>() {
        });
        this.register(new ClassTypeHandler<YearMonth>() {
        });

    }

    public void register(Class<?> clazz) {
        try {
            this.register((BaseTypeHandler<?>) clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(BaseTypeHandler<?> typeHandler) {
        if (typeHandler.useType()) {
            typeHandlerMap.put(typeHandler.getClazz(), typeHandler);
            for (int type : typeHandler.types()) {
                this.classTypeHandler.computeIfAbsent(typeHandler.getClazz(), k -> new HashMap<>()).put(type, typeHandler);
            }
            if(WrapperConverter.isWrapper(typeHandler.getClazz())){
                Class<?> primitiveClass = WrapperConverter.getPrimitiveType(typeHandler.getClazz());
                typeHandlerMap.put(primitiveClass, typeHandler);
                for (int type : typeHandler.types()) {
                    this.classTypeHandler.computeIfAbsent(primitiveClass, k -> new HashMap<>()).put(type, typeHandler);
                }
            }
        }

        for (int type : typeHandler.independent()) {
            this.typesTypeHandler.put(type, typeHandler);
        }
    }


    public TypeHandler<?> getTypeHandler(Class<?> clazz) {
        TypeHandler<?> typeHandler = this.typeHandlerMap.get(clazz);
        if (Objects.nonNull(typeHandler)) {
            return typeHandler;
        }
        throw new RuntimeException("No TypeHandler found for " + clazz);
    }

}
