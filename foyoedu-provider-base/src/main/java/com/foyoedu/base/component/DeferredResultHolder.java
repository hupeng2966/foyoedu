package com.foyoedu.base.component;

import com.foyoedu.common.pojo.FoyoResult;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeferredResultHolder {
    private Map<String, DeferredResult<FoyoResult>> map = new HashMap<String, DeferredResult<FoyoResult>>();

    public Map<String, DeferredResult<FoyoResult>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<FoyoResult>> map) {
        this.map = map;
    }
}
