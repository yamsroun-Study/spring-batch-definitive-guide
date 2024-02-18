package yamsroun.batch04;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Batch04Application {

    public static void main(String[] args) {
        SpringApplication.run(Batch04Application.class, args);
    }

}
