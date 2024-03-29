package yamsroun.batch06;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Batch06Application {

    public static void main(String[] args) {
        SpringApplication.run(Batch06Application.class, args);
    }

}
