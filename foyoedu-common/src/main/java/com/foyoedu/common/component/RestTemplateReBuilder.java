package com.foyoedu.common.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

//@Component
public class RestTemplateReBuilder {
    @Autowired
    private RestTemplateBuilder builder;
    @Autowired
    private ObjectMapper objectMapper;
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = builder.build();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        MediaType[] mediaTypes = new MediaType[]{
                MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
        };
        converter.setSupportedMediaTypes(Arrays.asList(mediaTypes));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
