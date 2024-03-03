package yamsroun.batch05;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ExplorerJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobExplorer jobExplorer;

    @Bean
    public Tasklet explorerTasklet() {
        return new ExploringTasklet(jobExplorer);
    }

    @Bean
    public Step explorerStep() {
        return stepBuilderFactory.get("explorerStep")
            .tasklet(explorerTasklet())
            .build();
    }

    @Bean
    public Job explorerJob() {
        return jobBuilderFactory.get("explorerJob")
            .start(explorerStep())
            .incrementer(new RunIdIncrementer())
            .build();
    }
}
