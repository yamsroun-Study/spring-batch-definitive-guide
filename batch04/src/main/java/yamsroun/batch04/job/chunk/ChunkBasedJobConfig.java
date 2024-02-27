package yamsroun.batch04.job.chunk;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            //.incrementer(new DailyJobTimestamper())
            .build();
    }

    @Bean
    public Step chunkStep() {
        return stepBuilderFactory.get("chunkStep")
            .<String, String>chunk(1000)
            .reader(itemReader())
            .writer(itemWriter())
            .build();
    }

    @Bean
    public ListItemReader<String> itemReader() {
        int size = 100000;
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
}
