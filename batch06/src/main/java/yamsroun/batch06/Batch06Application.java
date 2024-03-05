package yamsroun.batch06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

//@EnableBatchProcessing
@SpringBootApplication
public class Batch06Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Batch06Application.class);

        Properties properties = new Properties();
        properties.put("spring.batch.job.enabled", false);
        application.setDefaultProperties(properties);

        application.run(args);

        //SpringApplication.run(Batch06Application.class, args);
    }

}
