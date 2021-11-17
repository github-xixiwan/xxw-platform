package com.xxw.platform.module.util.context;

import java.util.Map;

public interface RequestContext {

    RequestContext add(String key, String value);

    String get(String key);

    RequestContext remove(String key);

    Map<String, String> getAttributes();

}
