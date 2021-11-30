package com.exchange.exchangemanagement.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfiguration {

    @Bean("restTemplate")
    public RestTemplate restTemplateWithProxy () {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, messageConverter());
        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter messageConverter () {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JaxbAnnotationModule());
        converter.setObjectMapper(objectMapper);
        return converter;

    }
}
