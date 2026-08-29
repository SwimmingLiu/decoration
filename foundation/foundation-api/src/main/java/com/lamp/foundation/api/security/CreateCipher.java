package com.lamp.foundation.api.security;

import com.lamp.foundation.api.security.model.CreateObject;
import com.lamp.foundation.api.security.model.KeyWrapper;

public interface CreateCipher<T extends CreateObject> {

    KeyWrapper create(T object) throws Exception;
}
