package com.lamp.foundation.api.function.crud.operation;

import java.util.List;

public interface WriteOperation {

    void init();

    void write(FullOperationData fullOperationData);

    void write(List<String> content);
}
