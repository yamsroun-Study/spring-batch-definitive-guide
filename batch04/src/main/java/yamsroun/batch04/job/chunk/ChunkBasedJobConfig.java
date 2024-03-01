package yamsroun.batch04.job.chunk;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yamsroun.batch04.incrementer.DailyJobTimestamper;
import yamsroun.batch04.job.chunk.policy.RandomChunkSizePolicy;
import yamsroun.batch04.listener.LoggingStepStartStopListener;

import java.util.*;

@RequiredArgsConstructor
@Configuration
public class ChunkBasedJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkJob() {
        return jobBuilderFactory.get("chunkBasedJob")
            .start(chunkStep())
            .incrementer(new DailyJobTimestamper())
            .build();
    }

    @Bean
    public Step chunkStep() {
        return stepBuilderFactory.get("chunkStep")
            .<String, String>chunk(new RandomChunkSizePolicy())
            .reader(itemReader())
            .writer(itemWriter())
            .listener(new LoggingStepStartStopListener())
            .build();
    }

    @Bean
    public ListItemReader<String> itemReader() {
        int size = 10;
        List<String> items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            items.add(UUID.randomUUID().toString());
        }
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> {
            for (String item: items) {
                System.out.println("current item = " + item);
            }
        };
    }

    @Bean
    public CompletionPolicy completionPolicy() {
        CompositeCompletionPolicy policy = new CompositeCompletionPolicy();
        policy.setPolicies(new CompletionPolicy[] {
            new TimeoutTerminationPolicy(3),
            new SimpleCompletionPolicy(1000)
        });
        return policy;
    }
}
