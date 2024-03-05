package yamsroun.batch06.controller;

import lombok.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RequiredArgsConstructor
@RestController
public class BatchController {

    private final JobLauncher jobLauncher;
    private final ApplicationContext context;

    @PostMapping(path = "/run")
    public ExitStatus runJob(
        @RequestBody JobLaunceRequest request
    ) throws Exception {
        Job job = context.getBean(request.getName(), Job.class);
        return jobLauncher.run(job, request.getJobParameters())
            .getExitStatus();
    }

    @Getter @Setter
    @RequiredArgsConstructor
    public static final class JobLaunceRequest {

        private final String name;
        private final Properties jobParameters;

        public JobParameters getJobParameters() {
            Properties properties = new Properties();
            properties.putAll(jobParameters);
            return new JobParametersBuilder(properties)
                .toJobParameters();
        }
    }
}
