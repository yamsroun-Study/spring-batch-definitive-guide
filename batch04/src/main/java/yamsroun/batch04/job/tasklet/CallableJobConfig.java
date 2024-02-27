package yamsroun.batch04.job.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import yamsroun.batch04.incrementer.DailyJobTimestamper;

import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
//@Configuration
public class CallableJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job callableJob() {
        return jobBuilderFactory.get("callableJob")
            .start(callableStep())
            .incrementer(new DailyJobTimestamper())
            .build();
    }

    @Bean
    public Step callableStep() {
        return stepBuilderFactory.get("callableStep")
            .tasklet(tasklet())
            .build();
    }

    @Bean
    public CallableTaskletAdapter tasklet() {
        CallableTaskletAdapter adaptor = new CallableTaskletAdapter();
        adaptor.setCallable(callableObject());
        return adaptor;
    }

    @Bean
    public Callable<RepeatStatus> callableObject() {
        return () -> {
            log.info(">>> This was executed in another thread");
            return RepeatStatus.FINISHED;
        };
    }
}
