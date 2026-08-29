package com.lamp.foundation.base.function.crud.entity.update.column;

import com.lamp.foundation.api.extension.persistence.Entity;
import com.lamp.foundation.api.extension.persistence.Once;

@Entity(comment = "测试表",value="test_key")
@Once(onecName = "TestKey", dbName = "test_key")
public class DeleteColumnEntity {

}
