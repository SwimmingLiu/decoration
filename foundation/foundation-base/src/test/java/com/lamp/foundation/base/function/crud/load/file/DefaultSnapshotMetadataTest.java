package com.lamp.foundation.base.function.crud.load.file;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DefaultSnapshotMetadataTest {

    private final DefaultSnapshotMetadata defaultSnapshotMetadata = new DefaultSnapshotMetadata();


    @Before
    public void init() {
        defaultSnapshotMetadata.setVersion("1.0.1");
        defaultSnapshotMetadata.setProject("/leaning/space-work/china-lamp/decoration");
    }

    @Test
    public void test_incrementSnapshot() throws IOException {
        defaultSnapshotMetadata.incrementSnapshot(null);
    }
}
