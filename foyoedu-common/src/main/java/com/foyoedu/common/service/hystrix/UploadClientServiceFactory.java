package com.foyoedu.common.service.hystrix;

import com.foyoedu.common.service.UploadClientService;
import com.foyoedu.common.utils.FoyoUtils;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadClientServiceFactory implements FallbackFactory<UploadClientService> {

    @Override
    public UploadClientService create(Throwable throwable) {
        final Throwable t = throwable;
        return new UploadClientService() {
            @Override
            public String uploadFile(MultipartFile file) {
                return FoyoUtils.errorMessage(t);
            }
        };
    }
}
