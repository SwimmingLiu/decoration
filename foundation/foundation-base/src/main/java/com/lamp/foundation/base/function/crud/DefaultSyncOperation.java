package com.lamp.foundation.base.function.crud;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

import com.lamp.foundation.api.extension.databases.check.column.ColumnCheck;
import com.lamp.foundation.api.extension.databases.metadata.ColumnInfo;
import com.lamp.foundation.api.extension.databases.metadata.KeyInfo;
import com.lamp.foundation.api.extension.databases.metadata.OperationData;
import com.lamp.foundation.api.extension.databases.metadata.TableInfo;
import com.lamp.foundation.api.function.channel.model.UpdateData;
import com.lamp.foundation.api.function.channel.operation.CollectionOperation;
import com.lamp.foundation.api.function.channel.operation.EqualsResult;
import com.lamp.foundation.api.function.crud.operation.FullOperationData;
import com.lamp.foundation.api.function.crud.operation.FullOperationDataWrapper;
import com.lamp.foundation.api.function.crud.operation.MetadataOperation;
import com.lamp.foundation.api.function.crud.operation.ReadOperation;
import com.lamp.foundation.base.function.channel.operation.FullUnionChannel;
import com.lamp.foundation.base.function.channel.operation.ListOperation;
import com.lamp.foundation.base.function.crud.load.file.DefaultSnapshotMetadata;
import com.lamp.foundation.base.lang.util.collections.Tuple.Triplet;

import lombok.Setter;

/**
 * db to entity
 * <p>
 * entity to db ， file
 * <p>
 * fiel to db entity
 * <pre>
 *     全量
 *     读取 file ，
 * </pre>
 *
 * @author hahaha
 */
public class DefaultSyncOperation implements SyncOperation {

    /**
     * db 不变，
     */
    @Setter
    private MetadataOperation databaseOperation;

    private MetadataOperation javaOperation;


    private final DefaultSnapshotMetadata defaultSnapshotMetadata = new DefaultSnapshotMetadata();

    @Setter
    private ReadOperation formMetadata;

    @Setter
    private MetadataOperation toMetadataOperation;

    private final ListOperation<TableInfo> tableInfoListOperation = new ListOperation<>();


    public void syncBySqlFile(String context) {
        this.formMetadata = databaseOperation;
        List<TableInfo> formList = this.formMetadata.readMetadataByDatabase();
        if (CollectionUtils.isEmpty(formList)) {
            throw new RuntimeException("空的 sql 文件");
        }
        List<TableInfo> tableInfoList = this.databaseOperation.readMetadataByDatabase();
        if (CollectionUtils.isEmpty(tableInfoList)) {
            throw new RuntimeException("只SQL文件，目前只支持全量处理");
        }
        List<String> list = new ArrayList<>();
        list.add(context);
        this.databaseOperation.write(list);
    }

    public void syncByClass(ReadOperation formMetadtata) {
        this.formMetadata = formMetadtata;
        this.toMetadataOperation = databaseOperation;
        this.syncAll(false, null);

    }

    public FullOperationDataWrapper checkByPsiClass(ReadOperation formMetadata, boolean immediately, boolean rebuild) {
        this.formMetadata = formMetadata;
        this.toMetadataOperation = databaseOperation;
        TableInfo source = this.formMetadata.readMetadataByDatabase().get(0);
        if (Objects.isNull(source)) {
            return null;
        }
        String tableName = source.getName();
        if (!Objects.equals(source.getName(), source.getOnecName())) {
            tableName = source.getOnecName();
        }
        TableInfo target = toMetadataOperation.readMetadataByTable(tableName);
        FullOperationData fullOperationData = new FullOperationData();
        if (rebuild) {
            String dropContent = toMetadataOperation.dropTable(target);
            String createContent = toMetadataOperation.createTable(source);
            fullOperationData.setDropContent(dropContent);
            fullOperationData.setCreateContent(createContent);
        } else {
            if (Objects.isNull(target)) {
                String createContent = toMetadataOperation.createTable(source);
                fullOperationData.setCreateContent(createContent);
            } else {
                TableFullUnionChannel tableFullUnionChannel = new TableFullUnionChannel();
                tableInfoListOperation.old(source).fresh(target).equal(this::tableOperation)
                    .operation(CollectionOperation.UPDATE | CollectionOperation.UNION).channel(tableFullUnionChannel).operation();
                fullOperationData = tableFullUnionChannel.fullOperationDataArrayList.get(0);
            }
        }
        FullOperationDataWrapper fullOperationDataWrapper = new FullOperationDataWrapper();
        fullOperationDataWrapper.setFullOperationData(fullOperationData);
        fullOperationDataWrapper.setWriteOperation(toMetadataOperation);
        fullOperationDataWrapper.setWriteOperation(toMetadataOperation);
        fullOperationDataWrapper.setSnapshotMetadata(defaultSnapshotMetadata);
        if (immediately) {
            fullOperationDataWrapper.write();
        }
        return fullOperationDataWrapper;
    }

    public void syncByDatabase() {
        List<TableInfo> tableInfoList = this.databaseOperation.readMetadataByDatabase();
        List<String> stringList = new ArrayList<>();
        this.buildContext(stringList, tableInfoList, javaOperation::createTable);
    }

    private List<String> buildContext(List<TableInfo> tableInfoList, Function<TableInfo, String> function) {
        List<String> contextList = new ArrayList<>();
        this.buildContext(contextList, tableInfoList, function);
        return contextList;
    }

    private void buildContext(List<String> contextList, List<TableInfo> tableInfoList, Function<TableInfo, String> function) {
        tableInfoList.forEach(tableInfo -> {
            contextList.add(function.apply(tableInfo));
        });
    }

    @Override
    public void syncAll(boolean rebuild, Object model) {

        List<TableInfo> source = formMetadata.readMetadataByDatabase();
        List<TableInfo> target = toMetadataOperation.readMetadataByDatabase();
        if (CollectionUtils.isEmpty(source)) {
            return;
        }
        if (rebuild) {
            List<String> stringList = this.buildContext(target, toMetadataOperation::dropTable);
            this.buildContext(stringList, source, toMetadataOperation::createTable);
            toMetadataOperation.write(stringList);
            return;
        }
        if (CollectionUtils.isEmpty(target)) {
            List<String> stringList = this.buildContext(source, toMetadataOperation::createTable);
            toMetadataOperation.write(stringList);
            return;
        }
        TableFullUnionChannel tableFullUnionChannel = new TableFullUnionChannel();
        tableInfoListOperation.old(source).fresh(target).equal(this::tableOperation)
            .operation(CollectionOperation.UPDATE | CollectionOperation.UNION).channel(tableFullUnionChannel).operation();
        // 立即执行
    }


    private EqualsResult tableOperation(TableInfo old, TableInfo newData) {
        if (Objects.equals(old.getName(), newData.getName())) {
            return EqualsResult.CONSISTENT;
        } else if (Objects.equals(old.getOnecName(), newData.getName())) {
            return EqualsResult.PRIMARY_KEY_CHANGE;
        }
        return EqualsResult.DIFFERENT;
    }

    @Override
    public void syncTable(String tableName) {

    }

    @Override
    public void syncTableMetadata(TableInfo tableInfo) {
        String tableInfoName = tableInfo.getName();
        if (!Objects.equals(tableInfo.getName(), tableInfo.getDbName())) {
            tableInfoName = tableInfo.getDbName();
            // 重命名表，然后再读取表内容
        }
        TableInfo oldTableInfo = toMetadataOperation.readMetadataByTable(tableInfoName);
        if (Objects.isNull(oldTableInfo)) {
            toMetadataOperation.createTable(tableInfo);
            return;
        }
        //
    }


    class TableFullUnionChannel implements FullUnionChannel<TableInfo> {

        private final List<FullOperationData> fullOperationDataArrayList = new ArrayList<>();

        @Override
        public void union(Triplet<TableInfo, TableInfo, EqualsResult> triplet) {

            FullOperationData fullOperationData = new FullOperationData();
            fullOperationDataArrayList.add(fullOperationData);
            KeyFullUnionChannel keyFullUnionChannel = new KeyFullUnionChannel();
            fullOperationData.setKey(keyFullUnionChannel.operationData);
            ColumnFullUnionChannel columnFullUnionChannel = new ColumnFullUnionChannel();
            fullOperationData.setColumn(columnFullUnionChannel.operationData);

            TableInfo formTableInfo = triplet.getUnit();
            TableInfo toTableInfo = triplet.getPair();

            ListOperation<ColumnInfo> listOperation = new ListOperation<>();
            listOperation.old(formTableInfo.getColumns()).fresh(toTableInfo.getColumns()).equal(this::columnOperation);
            listOperation.channel(columnFullUnionChannel).operation();

            ListOperation<KeyInfo> keyOperation = new ListOperation<>();
            keyOperation.old(formTableInfo.getKeys()).fresh(toTableInfo.getKeys()).equal(this::keyOperation);
            keyOperation.channel(keyFullUnionChannel).operation();
        }

        @Override
        public void insert(TableInfo data) {

        }

        @Override
        public void update(UpdateData<TableInfo> data) {

        }

        @Override
        public void delete(TableInfo data) {

        }

        private EqualsResult columnOperation(ColumnInfo formerColumn, ColumnInfo toColumn) {
            if (!Objects.equals(formerColumn.getName(), toColumn.getName()) && !Objects.equals(formerColumn.getOnceName(), toColumn.getName())) {
                return EqualsResult.DIFFERENT;
            }
            EqualsResult equalsResult = this.columnBaseInfo(formerColumn, toColumn);
            if (Objects.equals(equalsResult, EqualsResult.DIFFERENT)) {
                if (!Objects.equals(formerColumn.getOnceName(), toColumn.getName())) {
                    return EqualsResult.PRIMARY_KEY_CHANGE_AND_DIFFERENT;
                }
            } else if (Objects.equals(equalsResult, EqualsResult.CONSISTENT)) {
                if (Objects.equals(formerColumn.getName(), toColumn.getName())) {
                    return EqualsResult.CONSISTENT;
                } else {
                    return EqualsResult.PRIMARY_KEY_CHANGE;
                }
            }
            return equalsResult;

        }

        private EqualsResult columnBaseInfo(ColumnInfo formerColumn, ColumnInfo toColumn) {
            if (!Objects.equals(formerColumn.isNullable(), toColumn.isNullable())) {
                return EqualsResult.DIFFERENT;
            }

            if (!Objects.equals(formerColumn.isPrimaryKey(), toColumn.isPrimaryKey())) {
                return EqualsResult.DIFFERENT;
            }
            if (!Objects.equals(formerColumn.isUnique(), toColumn.isUnique())) {
                return EqualsResult.DIFFERENT;
            }
            if (!Objects.equals(formerColumn.getDefaultValue(), toColumn.getDefaultValue())) {
                return EqualsResult.DIFFERENT;
            }
            if (!Objects.equals(formerColumn.getOnUpdate(), toColumn.getOnUpdate())) {
                return EqualsResult.DIFFERENT;
            }
            if (!Objects.equals(formerColumn.getComment(), toColumn.getComment())) {
                return EqualsResult.DIFFERENT;
            }
            for (Entry<Class<?>, ColumnCheck> entry : formerColumn.getColumnCheckMap().entrySet()) {
                ColumnCheck check = toColumn.getColumnCheckMap().get(entry.getKey());
                if (Objects.isNull(check)) {
                    return EqualsResult.DIFFERENT;
                }
                if (!Objects.equals(entry.getValue(), check)) {
                    return EqualsResult.DIFFERENT;
                }
            }
            return EqualsResult.CONSISTENT;
        }

        private EqualsResult keyOperation(KeyInfo formKey, KeyInfo toKey) {
            return EqualsResult.PRIMARY_KEY;
        }
    }

    class ColumnFullUnionChannel implements FullUnionChannel<ColumnInfo> {


        private final OperationData<String> operationData = new OperationData<>();

        @Override
        public void union(Triplet<ColumnInfo, ColumnInfo, EqualsResult> data) {

        }

        @Override
        public void insert(ColumnInfo data) {
            toMetadataOperation.addColumn(data);
        }

        @Override
        public void update(UpdateData<ColumnInfo> data) {
            if (Objects.equals(data.getEqualsResult(), EqualsResult.PRIMARY_KEY_CHANGE)) {
                toMetadataOperation.renameColumn(data.getUpdate());
            } else if (Objects.equals(data.getEqualsResult(), EqualsResult.DIFFERENT)) {
                toMetadataOperation.updateColumn(data.getUpdate());
            } else if (Objects.equals(data.getEqualsResult(), EqualsResult.PRIMARY_KEY_CHANGE_AND_DIFFERENT)) {
                toMetadataOperation.updateColumn(data.getUpdate());
            }
        }

        @Override
        public void delete(ColumnInfo data) {
            toMetadataOperation.dropColumn(data);
        }
    }

    class KeyFullUnionChannel implements FullUnionChannel<KeyInfo> {

        private final OperationData<String> operationData = new OperationData<>();

        @Override
        public void union(Triplet<KeyInfo, KeyInfo, EqualsResult> data) {

        }

        @Override
        public void insert(KeyInfo data) {

        }

        @Override
        public void update(UpdateData<KeyInfo> data) {
            toMetadataOperation.createIndex(data.getUpdate());
        }

        @Override
        public void delete(KeyInfo data) {
            toMetadataOperation.dropIndex(data);
        }
    }
}
