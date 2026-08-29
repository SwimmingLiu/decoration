package com.lamp.foundation.base.function.crud.load.file;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;

import com.lamp.foundation.api.function.crud.SnapshotMetadata;
import com.lamp.foundation.api.function.crud.operation.FullOperationData;
import com.lamp.foundation.base.io.system.FileDataLoad;
import com.lamp.foundation.base.io.system.VerificationCode;
import com.lamp.foundation.base.lang.util.string.NetUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Setter
public class DefaultSnapshotMetadata implements SnapshotMetadata {

    private String version;

    private String project;

    @Override
    public String fullSnapshot(String sql) throws IOException {
        YearMonth now = YearMonth.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        String path = project + "/.config/sql/full/" + now + "/full_" + localDateTime + ".sql";
        FileDataLoad.ofString(sql, path);
        String main = project + "/.config/sql/full/main.sql";
        FileDataLoad.ofString(sql, main);
        return path;
    }


    @Override
    public String incrementSnapshot(FullOperationData fullOperationData) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String path = project + "/.config/sql/snapshot/" + version + "/increment_" + now + ".yaml";

        MetadataInfo metadataInfo = new MetadataInfo();
        metadataInfo.address = NetUtil.getLocalAddressByCache();
        metadataInfo.createTime = now;
        metadataInfo.sql = fullOperationData;

        FileDataLoad<MetadataInfo> fileDataLoad = new FileDataLoad<>(path, MetadataInfo.class);
        fileDataLoad.persist(metadataInfo);

        return path;
    }


    @Data
    @EqualsAndHashCode(callSuper = true)
    static class MetadataInfo extends VerificationCode {

        private String address;

        private LocalDateTime createTime;

        private FullOperationData sql;
    }

}
