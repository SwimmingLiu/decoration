package com.lamp.foundation.api.function.crud.operation;

import java.io.File;

import com.lamp.foundation.api.function.crud.SnapshotMetadata;

import lombok.Data;

@Data
public class FullOperationDataWrapper {

    private FullOperationData fullOperationData;

    private WriteOperation writeOperation;

    private ReadOperation readOperation;

    private SnapshotMetadata snapshotMetadata;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void write() {
        String path = null;
        try {
            path = snapshotMetadata.incrementSnapshot(fullOperationData);
            writeOperation.write(fullOperationData);
            String fullContent = readOperation.readFullContent();
            snapshotMetadata.fullSnapshot(fullContent);
        } catch (Exception e) {
            if (path != null) {
                File file = new File(path);
                file.delete();
            }
            throw new RuntimeException(e);
        }


    }

}
