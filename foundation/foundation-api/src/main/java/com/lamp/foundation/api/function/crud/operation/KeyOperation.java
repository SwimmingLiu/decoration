package com.lamp.foundation.api.function.crud.operation;

import com.lamp.foundation.api.extension.databases.metadata.KeyInfo;

public interface KeyOperation {

    String createIndex(KeyInfo keyInfo);

    String dropIndex(KeyInfo keyInfo);

}
