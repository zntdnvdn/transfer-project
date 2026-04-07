package com.eduproject.transferrequest.config.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@RequiredArgsConstructor
public class SchedulerInitializationConfig {
    private final ThreadPoolTaskSchedulerProperties threadPoolTaskSchedulerProperties;

    @Bean(name = "eventSendingScheduler")
    public ThreadPoolTaskScheduler eventSendingScheduler() {
        return buildThreadPoolTaskScheduler(threadPoolTaskSchedulerProperties.getEventSendingProcessor());
    }

    private ThreadPoolTaskScheduler buildThreadPoolTaskScheduler(ThreadPoolTaskSchedulerProperties.SchedulerProperties props) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(props.getPoolSize());
        taskScheduler.setThreadNamePrefix(props.getExecutorThreadNamePrefix());
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setAwaitTerminationMillis(props.getShutDownAwait());
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        taskScheduler.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        return taskScheduler;
    }
    }
