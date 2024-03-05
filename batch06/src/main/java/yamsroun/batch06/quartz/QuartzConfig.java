package yamsroun.batch06.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yamsroun.batch06.scheduled.BatchScheduledJob;

@Configuration
public class QuartzConfig {

    @Bean
    public Trigger jobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(5)
            .withRepeatCount(4);
        return TriggerBuilder.newTrigger()
            .forJob(quartzJobDetail())
            .withSchedule(scheduleBuilder)
            .build();
    }

    @Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder.newJob(BatchScheduledJob.class)
            .storeDurably()
            .build();
    }
}
