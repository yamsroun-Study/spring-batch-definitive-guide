package yamsroun.batch06.controller;

import lombok.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RequiredArgsConstructor
@RestController
public class BatchController {

    private final ApplicationContext context;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @PostMapping(path = "/run")
    public ExitStatus runJob(
        @RequestBody JobLaunceRequest request
    ) throws Exception {
        Job job = context.getBean(request.getName(), Job.class);
        JobParameters jobParameters =
            new JobParametersBuilder(request.getJobParameters(), jobExplorer)
                .getNextJobParameters(job)
                .toJobParameters();
        return jobLauncher.run(job, jobParameters)
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
