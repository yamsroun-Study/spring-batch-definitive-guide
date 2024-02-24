package yamsroun.batch04.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yamsroun.batch04.incrementer.DailyJobTimestamper;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SystemCommandJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job systemCommandJob() {
        return jobBuilderFactory.get("systemCommandJob")
            .start(systemCommandStep())
            .incrementer(new DailyJobTimestamper())
            .build();
    }

    @Bean
    public Step systemCommandStep() {
        return stepBuilderFactory.get("systemCommandStep")
            .tasklet(systemCommandTasklet())
            .build();
    }

    @Bean
    public SystemCommandTasklet systemCommandTasklet() {
        log.info(">>> systemCommandTasklet()");
        SystemCommandTasklet tasklet = new SystemCommandTasklet();
        tasklet.setCommand("rm /Users/yamsroun/tmp.txt");
        tasklet.setTimeout(5000);
        tasklet.setInterruptOnCancel(true);
        return tasklet;
    }

}
