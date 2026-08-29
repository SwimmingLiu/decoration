package com.lamp.foundation.base.function.crud;

import com.lamp.foundation.api.extension.databases.metadata.TableInfo;

/**
 * sql 文件同步行为，直接把内容写入数据库，让后从数据库同步？ 不同步数据，只同步 entity ，怎么办？ ，sql 里面有更新语句怎么办？让用户直接同步 db，lamp在从db 同步entity
 */
public interface SyncOperation {


    void syncAll(boolean rebuild, Object model);

    void syncTable(String tableName);

    void syncTableMetadata(TableInfo tableInfo);


}
