package com.foyoedu.common.service.hystrix;

import com.foyoedu.common.service.TeacherClientService;
import com.foyoedu.common.utils.FoyoUtils;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TeacherClientServiceFactory implements FallbackFactory<TeacherClientService> {
    @Override
    public TeacherClientService create(Throwable throwable) {
        final Throwable t = throwable;
        return new TeacherClientService() {
            public String addTeacherData(@RequestPart MultipartFile file) {
                return FoyoUtils.errorMessage(t);
            }

            @Override
            public String findTeacherData() {
                return FoyoUtils.errorMessage(t);
            }
        };
    }
}
