package yamsroun.batch04.job.stepflow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yamsroun.batch04.decider.RandomDecider;
import yamsroun.batch04.incrementer.DailyJobTimestamper;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ConditionalJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Tasklet passTasklet() {
        return (contribution, chunkContext) -> {
            //return RepeatStatus.FINISHED;
            throw new RuntimeException("This is a failure");
        };
    }

    @Bean
    public Tasklet successTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>> Success!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet failTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>> Failure!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("conditionalJob")
            .start(firstStep())
            .on("FAILED").end()
            .from(firstStep()).on("*").to(successStep())
            .end()
            .incrementer(new DailyJobTimestamper())
            .build();
    }

    @Bean
    public Step firstStep() {
        return stepBuilderFactory.get("firstStep")
            .tasklet(passTasklet())
            .build();
    }

    @Bean
    public Step successStep() {
        return stepBuilderFactory.get("successStep")
            .tasklet(successTasklet())
            .build();
    }

    @Bean
    public Step failureStep() {
        return stepBuilderFactory.get("failureStep")
            .tasklet(failTasklet())
            .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new RandomDecider();
    }
}
