package com.kunlun.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.zipkin.ZipkinProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author by fk
 * @version <0.1>
 * @created on 2018-01-08.
 */
@Configuration
@RefreshScope
public class ZipKinConfig {

    @Value("${zip.base-url}")
    private String baseUrl;


    @Bean
    ZipkinProperties zipkinProperties() {
        ZipkinProperties zipkinProperties = new ZipkinProperties();
        zipkinProperties.setBaseUrl(baseUrl);
        zipkinProperties.setEnabled(true);

        return zipkinProperties;
    }
}
