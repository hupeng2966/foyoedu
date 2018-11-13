package com.foyoedu.common.service.hystrix;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.service.DeptClientService;
import com.foyoedu.common.utils.FoyoUtils;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class DeptClientServiceFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        final Throwable t = throwable;
        return new DeptClientService() {

            @Override
            public String addDept(Dept dept) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public String getDept(Long id) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public String listDeptTest(MultiValueMap<String, String> paramMap) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public String deleteDeptById(Long id) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public String delete(Dept dept) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public String updateDept(Dept dept) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public String hello() {
                return FoyoUtils.errorMessage(t);
            }
        };

    }
}
