package az.baxtiyargill.reactivedemo.utility;

import az.baxtiyargill.reactivedemo.configuration.properties.ApplicationProperties;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class ExecutorServiceUtility {

    private final static ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    public static ScheduledThreadPoolExecutor customizeAndGetThreadPoolExecutor(ApplicationProperties properties,
                                                                                int itemsSize) {
        int corePoolSize = itemsSize / 3;
        if ((corePoolSize < properties.getCorePoolSize()) && (corePoolSize < properties.getMaxPoolSize())) {
            threadPoolExecutor.setCorePoolSize(corePoolSize);
        } else {
            threadPoolExecutor.setCorePoolSize(properties.getCorePoolSize());
        }

        threadPoolExecutor.setMaximumPoolSize(properties.getMaxPoolSize());
        threadPoolExecutor.setKeepAliveTime(properties.getKeepAliveTime(), TimeUnit.SECONDS);
        return threadPoolExecutor;
    }

}
