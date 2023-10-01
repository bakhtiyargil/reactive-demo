package az.baxtiyargill.reactivedemo.configuration;

import az.baxtiyargill.reactivedemo.configuration.properties.ApplicationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {
}
