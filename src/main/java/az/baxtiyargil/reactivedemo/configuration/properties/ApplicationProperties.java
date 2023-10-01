package az.baxtiyargil.reactivedemo.configuration.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "application.executor")
public class ApplicationProperties {

    @NotNull
    Integer corePoolSize;
    @NotNull
    Integer maxPoolSize;
    @NotNull
    Integer keepAliveTime;

}
