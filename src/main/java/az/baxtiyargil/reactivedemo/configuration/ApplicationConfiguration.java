package az.baxtiyargil.reactivedemo.configuration;

import az.baxtiyargil.reactivedemo.configuration.properties.ApplicationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableFeignClients(basePackages = "az.baxtiyargil.reactivedemo.client.**")
public class ApplicationConfiguration {
}
