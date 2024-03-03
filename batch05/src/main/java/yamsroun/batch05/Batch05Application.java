package yamsroun.batch05;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class Batch05Application {

    public static void main(String[] args) {
        SpringApplication.run(Batch05Application.class, args);
    }

}
