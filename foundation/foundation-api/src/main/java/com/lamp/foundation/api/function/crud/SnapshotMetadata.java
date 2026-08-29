package com.lamp.foundation.api.function.crud;

import java.io.IOException;

import com.lamp.foundation.api.function.crud.operation.FullOperationData;

public interface SnapshotMetadata {

    String fullSnapshot(String sql) throws IOException;

    String incrementSnapshot(FullOperationData sqlList) throws IOException;


}
