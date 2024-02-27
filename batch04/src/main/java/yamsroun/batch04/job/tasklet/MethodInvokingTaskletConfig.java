package yamsroun.batch04.job.tasklet;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import yamsroun.batch04.incrementer.DailyJobTimestamper;
import yamsroun.batch04.service.CustomService;

@RequiredArgsConstructor
//@Configuration
public class MethodInvokingTaskletConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomService customService;

    @Bean
    public Job methodInvokingJob() {
        return jobBuilderFactory.get("methodInvokingJob")
            .start(methodInvokingStep())
            .next(methodInvokingStep2())
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
    public Step methodInvokingStep2() {
        return stepBuilderFactory.get("methodInvokingStep2")
            .tasklet(methodInvokingTasklet2(null))
            .build();
    }

    @Bean
    public Tasklet methodInvokingTasklet() {
        MethodInvokingTaskletAdapter adaptor = new MethodInvokingTaskletAdapter();
        adaptor.setTargetObject(customService);
        adaptor.setTargetMethod("serviceMethod");
        return adaptor;
    }

    @StepScope
    @Bean
    public Tasklet methodInvokingTasklet2(
        @Value("#{jobParameters['fileName']}") String message
    ) {
        MethodInvokingTaskletAdapter adaptor = new MethodInvokingTaskletAdapter();
        adaptor.setTargetObject(customService);
        adaptor.setTargetMethod("serviceMethod2");
        adaptor.setArguments(new String[] {message});
        return adaptor;
    }
}
