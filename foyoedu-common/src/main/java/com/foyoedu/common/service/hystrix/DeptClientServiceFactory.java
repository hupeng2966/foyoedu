package com.foyoedu.common.service.hystrix;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.DeptClientService;
import com.foyoedu.common.utils.FoyoUtils;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeptClientServiceFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        final Throwable t = throwable;
        return new DeptClientService() {

            @Override
            public FoyoResult addDept(Dept dept) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public FoyoResult getDept(Long id) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public FoyoResult listDeptTest(Map<String, String> map) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public FoyoResult deleteDeptById(Long id) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public FoyoResult delete(Dept dept) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public FoyoResult updateDept(Dept dept) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public FoyoResult hello() {
                return FoyoUtils.errorMessage(t);
            }
        };

    }
}
