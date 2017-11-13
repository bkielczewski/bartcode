package uk.co.bartcode.service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler

@Configuration
class TaskExecutorConfig {

    @Bean
    fun taskScheduler(): TaskScheduler = ConcurrentTaskScheduler()

}