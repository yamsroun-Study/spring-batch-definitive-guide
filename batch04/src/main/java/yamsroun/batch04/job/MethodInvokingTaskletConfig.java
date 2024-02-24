package yamsroun.batch04.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yamsroun.batch04.incrementer.DailyJobTimestamper;
import yamsroun.batch04.service.CustomService;

@RequiredArgsConstructor
@Configuration
public class MethodInvokingTaskletConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomService customService;

    @Bean
    public Job methodInvokingJob() {
        return jobBuilderFactory.get("methodInvokingJob")
            .start(methodInvokingStep())
            .incrementer(new DailyJobTimestamper())
            .build();
    }

    @Bean
    public Step methodInvokingStep() {
        return stepBuilderFactory.get("methodInvokingStep")
            .tasklet(methodInvokingTasklet())
            .build();
    }

    @Bean
    public Tasklet methodInvokingTasklet() {
        MethodInvokingTaskletAdapter adaptor = new MethodInvokingTaskletAdapter();
        adaptor.setTargetObject(customService);
        adaptor.setTargetMethod("serviceMethod");
        return adaptor;
    }
}
