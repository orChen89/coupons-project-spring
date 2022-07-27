package com.or.couponsproject.couponsproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    //Returning a spring bean of rest template object
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    //Returning a spring bean of Http Headers object
    @Bean
    public HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }
}
