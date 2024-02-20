package yamsroun.batch04;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class HelloWorldJob {

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("basicJob")
            .start(step1())
            .validator(validator())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet(helloWorldTasklet(null, null))
            .build();
    }

    @StepScope
    @Bean
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
            new String[] {"name", "run.id"});
        defaultValidator.afterPropertiesSet();
        compositeValidator.setValidators(List.of(
            new CustomParameterValidator(),
            defaultValidator
        ));
        return compositeValidator;
    }
}
