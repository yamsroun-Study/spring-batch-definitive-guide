package yamsroun.batch04.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import yamsroun.batch04.incrementer.DailyJobTimestamper;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AdvancedSystemCommandJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job advancedSystemCommandJob() {
        return jobBuilderFactory.get("advancedSystemCommandJob")
            .start(advancedSystemCommandStep())
            .incrementer(new DailyJobTimestamper())
            .build();
    }

    @Bean
    public Step advancedSystemCommandStep() {
        return stepBuilderFactory.get("advancedSystemCommandStep")
            .tasklet(advancedSystemCommandTasklet())
            .build();
    }

    @Bean
    public SystemCommandTasklet advancedSystemCommandTasklet() {
        log.info(">>> advancedSystemCommandTasklet()");

        SystemCommandTasklet tasklet = new SystemCommandTasklet();

        tasklet.setWorkingDirectory("/Users/yamsroun");
        tasklet.setCommand("touch tmp.txt");
        tasklet.setTimeout(5000);
        tasklet.setInterruptOnCancel(true);

        tasklet.setSystemProcessExitCodeMapper(touchCodeMapper());
        tasklet.setTerminationCheckInterval(1000);
        tasklet.setTaskExecutor(new SimpleAsyncTaskExecutor());
        tasklet.setEnvironmentParams(new String[] {"JAVA_HOME2=/java", "BATCH_HOME2=/Users/yamsroun"});

        return tasklet;
    }

    public SystemProcessExitCodeMapper touchCodeMapper() {
        return new SimpleSystemProcessExitCodeMapper();
    }

}
