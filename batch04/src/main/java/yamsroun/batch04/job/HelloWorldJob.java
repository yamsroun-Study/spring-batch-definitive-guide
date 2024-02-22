package yamsroun.batch04.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yamsroun.batch04.incrementer.DailyJobTimestamper;
import yamsroun.batch04.listener.JobLoggerListener;
import yamsroun.batch04.tasklet.HelloWorldTasklet;
import yamsroun.batch04.validator.CustomParameterValidator;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class HelloWorldJob {

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final HelloWorldTasklet helloWorldTasklet;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("basicJob")
            .start(step1())
            .validator(validator())
            .incrementer(new DailyJobTimestamper())
            .listener(new JobLoggerListener())
            //.listener(new JobLogger2Listener()) //JobListenerFactoryBean을 사용하지 않아도 동작함
            //.listener(JobListenerFactoryBean.getListener(new JobLogger2Listener()))
            .listener(promotionListener())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            //.tasklet(helloWorldTasklet(null, null))
            .tasklet(helloWorldTasklet)
            .build();
    }

    //@StepScope
    //@Bean
    public Tasklet helloWorldTasklet(
        @Value("#{jobParameters['name']}") String name,
        @Value("#{jobParameters['fileName']}") String fileName
    ) {
        return (contribution, chunkContext) -> {
            log.info(">>> Hello, {}!", name);
            log.info(">>> fileName = {}", fileName);
            return RepeatStatus.FINISHED;
        };
    }

    //@Bean
    //public JobParametersValidator validator() {
    //    var validator = new DefaultJobParametersValidator();
    //    validator.setRequiredKeys(new String[] {"fileName"});
    //    validator.setOptionalKeys(new String[] {"name"});
    //    return validator;
    //}

    @Bean
    public CompositeJobParametersValidator validator() {
        var compositeValidator = new CompositeJobParametersValidator();
        var defaultValidator = new DefaultJobParametersValidator(
            new String[] {"fileName"},
            new String[] {"name", "run.id", "currentDate"});
        defaultValidator.afterPropertiesSet();
        compositeValidator.setValidators(List.of(
            new CustomParameterValidator(),
            defaultValidator
        ));
        return compositeValidator;
    }

    @Bean
    public StepExecutionListener promotionListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[] {"name"});
        return listener;
    }
}
