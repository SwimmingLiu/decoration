package com.lamp.foundation.api;

import java.util.List;
import java.util.Map;

public interface StringReplace {


    String replace(Map<String, String> values);

    String replace(List<String> values);
}
