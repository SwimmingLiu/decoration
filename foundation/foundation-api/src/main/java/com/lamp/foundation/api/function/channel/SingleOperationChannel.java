/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.lamp.foundation.api.function.channel;

import java.util.List;

import com.lamp.foundation.api.function.channel.model.UpdateData;

/**
 *
 */
@SuppressWarnings("unchecked")
public interface SingleOperationChannel<T> {


    void insert(T data);

    default void insert(List<T> data) {
        if (data != null) {
            data.forEach(this::insert);
        }
    }

    default void insertObject(List<Object> data) {
        this.insert((List<T>) data);
    }

    default void replace(List<Object> data) {
        if (data != null) {
            delete((List<T>) data);
            insert((List<T>) data);
        }
    }

    void update(UpdateData<T> data);

    default void update(List<UpdateData<T>> data) {
        if (data != null) {
            data.forEach(this::update);
        }
    }

    void delete(T data);

    default void delete(List<T> data) {
        if (data != null) {
            data.forEach(this::delete);
        }
    }
}
