package com.eduproject.transferrequest.config.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "scheduler")
public class ThreadPoolTaskSchedulerProperties {
    private SchedulerProperties eventSendingProcessor = new SchedulerProperties();
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchedulerProperties {
        private int poolSize;
        private int shutDownAwait;
        private String executorThreadNamePrefix;

    }
}
