package yamsroun.batch04.job.stepflow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yamsroun.batch04.incrementer.DailyJobTimestamper;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobStepJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Tasklet loadStockFile() {
        return (contribution, chunkContext) -> {
            log.info(">>> The stock file has been loaded");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet loadCustomerFile() {
        return (contribution, chunkContext) -> {
            log.info(">>>Ths customer file has been loaded");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet updateStart() {
        return (contribution, chunkContext) -> {
            log.info(">>>The start has been updated");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet runBatchTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>>The batch has been run");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job preProcessingJob() {
        return jobBuilderFactory.get("preProcessingJob")
            .start(loadFileStep())
            .next(loadCustomerStep())
            .next(updateStartStep())
            .build();
    }

    @Bean
    public Job conditionalStepLogicJob() {
        return jobBuilderFactory.get("conditionalStepLogicJob")
            .start(initializeBatch())
            .next(runBatch())
            .incrementer(new DailyJobTimestamper())
            .build();
    }

    @Bean
    public Step initializeBatch() {
        return stepBuilderFactory.get("initializeBatch")
            .job(preProcessingJob())
            .parametersExtractor(new DefaultJobParametersExtractor())
            .build();
    }

    @Bean
    public Step loadFileStep() {
        return stepBuilderFactory.get("loadFileStep")
            .tasklet(loadStockFile())
            .build();
    }

    @Bean
    public Step loadCustomerStep() {
        return stepBuilderFactory.get("loadCustomerStep")
            .tasklet(loadCustomerFile())
            .build();
    }

    @Bean
    public Step updateStartStep() {
        return stepBuilderFactory.get("updateStartStep")
            .tasklet(updateStart())
            .build();
    }

    @Bean
    public Step runBatch() {
        return stepBuilderFactory.get("runBatch")
            .tasklet(runBatchTasklet())
            .build();
    }
}
