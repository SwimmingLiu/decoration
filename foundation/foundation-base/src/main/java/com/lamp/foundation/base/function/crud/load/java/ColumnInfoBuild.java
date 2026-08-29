package com.lamp.foundation.base.function.crud.load.java;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.lamp.foundation.api.extension.databases.check.column.ColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.DigitsColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.RationalColumnCheck;
import com.lamp.foundation.api.extension.databases.check.column.SizeColumnCheck;
import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.api.extension.jdbc.type.JDBCTypeMapper;
import com.lamp.foundation.api.extension.persistence.Column;
import com.lamp.foundation.api.extension.persistence.Once;
import com.lamp.foundation.api.extension.persistence.type.Rational;
import com.lamp.foundation.api.extension.sort.Sort;
import com.lamp.foundation.api.extension.sort.Sort.SortType;
import com.lamp.foundation.api.extension.sort.SortWrapper;
import com.lamp.foundation.api.extension.validation.constraints.base.Null;
import com.lamp.foundation.api.extension.validation.constraints.base.Size;
import com.lamp.foundation.api.extension.validation.constraints.numerical.Digits;
import com.lamp.foundation.api.lang.util.Node;
import com.lamp.foundation.base.extension.jdbc.type.DefaultMapperData;

import lombok.Setter;

public class ColumnInfoBuild {


    private final Map<String, ColumnInfo> columnInfoMap = new HashMap<>();

    private final List<ClassSortInfo> classSortInfoList = new ArrayList<>();


    @Setter
    private Class<?> clazz;


    public List<ColumnInfo> handler() {
        List<Class<?>> classList = ClassUtils.getAllSuperclasses(clazz);
        Collections.reverse(classList);
        classList.add(clazz);
        classList.forEach(this::build);
        if (this.classSortInfoList.isEmpty()) {
            return null;
        }
        this.sort();
        return this.link();
    }

    public void build(Class<?> clazz) {
        if (Objects.equals(clazz, Object.class)) {
            return;
        }
        ClassSortInfo sortInfo = new ClassSortInfo();
        this.classSortInfoList.add(sortInfo);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ColumnInfo columnInfo = this.buildColumnInfo(field);
            if (Objects.isNull(columnInfo)) {
                continue;
            }
            this.sort(sortInfo, columnInfo);
            columnInfoMap.put(columnInfo.getColumnName(), columnInfo);
        }
    }

    private ColumnInfo buildColumnInfo(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (Objects.isNull(column)) {
            return null;
        }
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setField(field);
        if (StringUtils.isNotBlank(column.name())) {
            columnInfo.setName(column.name());
        } else {
            columnInfo.setName(field.getName());
        }
        Once once = field.getAnnotation(Once.class);
        if (Objects.nonNull(once)) {
            columnInfo.setOnceName(once.dbName());
        }
        columnInfo.setUnique(column.unique());
        columnInfo.setPrimaryKey(column.primaryKey());
        columnInfo.setOnUpdate(column.onUpdate());
        columnInfo.setDefaultValue(column.defaultValue());
        columnInfo.setComment(column.comment());
        columnInfo.setIncrement(column.increment());
        columnInfo.setMapper(column.jdbcType());

        if (Objects.equals(columnInfo.getMapper(), JDBCTypeMapper.NULL)) {
            JDBCTypeMapper mapper = DefaultMapperData.getMapper(field.getType());
            if (Objects.isNull(mapper)) {
                throw new RuntimeException("");
            }
            columnInfo.setMapper(mapper);
        }

        Sort sort = field.getAnnotation(Sort.class);
        SortWrapper sortWrapper = new SortWrapper();
        if (Objects.nonNull(sort)) {
            sortWrapper.setType(sort.type());
            sortWrapper.setField(sort.field());
        } else {
            sortWrapper.setType(SortType.UPWARD);
        }
        columnInfo.setSortWrapper(sortWrapper);
        this.isNotNull(field, columnInfo);
        this.columnCheck(field, columnInfo);
        return columnInfo;
    }


    private void isNotNull(Field field, ColumnInfo columnInfo) {
        Null n = field.getAnnotation(Null.class);
        columnInfo.setNullable(Objects.nonNull(n));
    }

    private void columnCheck(Field field, ColumnInfo columnInfo) {
        Map<Class<?>, ColumnCheck> columnCheckMap = new HashMap<>();
        columnInfo.setColumnCheckMap(columnCheckMap);

        Size size = field.getAnnotation(Size.class);
        if (Objects.nonNull(size)) {
            SizeColumnCheck sizeColumnCheck = new SizeColumnCheck();
            sizeColumnCheck.setMin(size.min());
            sizeColumnCheck.setMax(size.max());
            columnCheckMap.put(SizeColumnCheck.class, sizeColumnCheck);
        }
        Digits degits = field.getAnnotation(Digits.class);
        if (Objects.nonNull(degits)) {
            DigitsColumnCheck digitsColumnCheck = new DigitsColumnCheck();
            digitsColumnCheck.setInteger(degits.integer());
            digitsColumnCheck.setFraction(degits.fraction());
            columnCheckMap.put(DigitsColumnCheck.class, digitsColumnCheck);
        }

        Rational rational = field.getAnnotation(Rational.class);
        if (Objects.nonNull(rational)) {
            RationalColumnCheck rationalColumnCheck = new RationalColumnCheck();
            columnCheckMap.put(RationalColumnCheck.class, rationalColumnCheck);
        }
    }


    private void sort() {
        this.classSortInfoList.forEach(wrapper -> {
            wrapper.association.forEach(association -> {
                SortWrapper sortWrapper = association.getSortWrapper();
                ColumnInfo columnInfo = this.columnInfoMap.get(sortWrapper.getField());
                if (Objects.isNull(columnInfo)) {
                    String message = "";
                    throw new RuntimeException(message);
                }
                if (Objects.equals(sortWrapper.getType(), SortType.AFTER)) {
                    //  1 2 3 ， 1 2 4 3
                    ColumnInfo nextColumnInfo = columnInfo.getNextColumn();
                    columnInfo.setNextColumn(association);
                    association.setLastColumn(columnInfo);
                    association.setNextColumn(nextColumnInfo);
                    nextColumnInfo.setLastColumn(association);
                } else if (Objects.equals(sortWrapper.getType(), SortType.BEFORE)) {
                    // 1 2 3 , 1 4 2 3
                    ColumnInfo lastColumnInfo = columnInfo.getLastColumn();
                    lastColumnInfo.setNextColumn(association);
                    association.setLastColumn(lastColumnInfo);
                    association.setNextColumn(columnInfo);
                    columnInfo.setLastColumn(association);
                }
            });
        });
    }


    private void sort(ClassSortInfo sortInfo, ColumnInfo columnInfo) {
        SortWrapper sortWrapper = columnInfo.getSortWrapper();
        if (Objects.equals(sortWrapper.getType(), SortType.AFTER) || sortWrapper.getType().equals(SortType.BEFORE)) {
            sortInfo.association.add(columnInfo);
            return;
        }
        SortRelationship sortRelationship = sortInfo.sortRelationshipMap.get(sortWrapper.getType());
        sortRelationship.setColumnInfo(columnInfo);
    }

    private List<ColumnInfo> link() {
        Node<SortType> sortTypeNode = SortType.ROOT_FIRST.getNode();
        ClassSortInfo classSortInfo = new ClassSortInfo();
        for (; ; ) {
            SortRelationship aggregation = classSortInfo.sortRelationshipMap.get(sortTypeNode.getElement());
            for (ClassSortInfo sortInfo : this.classSortInfoList) {
                SortRelationship oldSortRelationship = sortInfo.sortRelationshipMap.get(sortTypeNode.getElement());
                aggregation.aggregation(oldSortRelationship);
            }
            if (Objects.isNull(sortTypeNode.getNext())) {
                break;
            }
            sortTypeNode = sortTypeNode.getNext();
        }
        List<ColumnInfo> columnInfoList = new ArrayList<>();
        SortType sortType = SortType.ROOT_FIRST;
        for (; ; ) {
            SortRelationship root = classSortInfo.sortRelationshipMap.get(sortType);
            if (Objects.isNull(root)) {
                break;
            }
            if (Objects.nonNull(root.head)) {
                ColumnInfo columnInfo = root.head;
                do {
                    columnInfoList.add(columnInfo);
                    columnInfo = columnInfo.getNextColumn();
                } while (!Objects.isNull(columnInfo));
            }
            sortType = sortType.getNext();

        }
        return columnInfoList;
    }


    static class ClassSortInfo {

        private final Map<SortType, SortRelationship> sortRelationshipMap = new HashMap<>();

        private final List<ColumnInfo> association = new ArrayList<>();

        {
            for (SortType sortType : SortType.values()) {
                sortRelationshipMap.put(sortType, new SortRelationship(sortType));
            }
        }


    }


    static class SortRelationship {

        private SortType sortType;

        private ColumnInfo head;

        private ColumnInfo tail;


        public SortRelationship(SortType sortType) {
            this.sortType = sortType;
        }


        public void setColumnInfo(ColumnInfo columnInfo) {
            if (Objects.isNull(head)) {
                this.head = columnInfo;
                this.tail = columnInfo;
            } else {
                this.tail.setNextColumn(columnInfo);
                this.tail = columnInfo;
            }
        }

        public void aggregation(SortRelationship sortRelationship) {
            if (Objects.isNull(sortRelationship)) {
                return;
            }
            if (Objects.isNull(sortRelationship.head)) {
                return;
            }
            if (Objects.isNull(head)) {
                this.head = sortRelationship.head;
                this.tail = sortRelationship.tail;
            } else {
                this.tail.setNextColumn(sortRelationship.head);
                this.tail = sortRelationship.tail;
            }
        }

    }

}
