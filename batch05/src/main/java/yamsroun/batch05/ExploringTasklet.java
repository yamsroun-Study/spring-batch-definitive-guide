package yamsroun.batch05;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ExploringTasklet implements Tasklet {

    private final JobExplorer explorer;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        String jobName = chunkContext.getStepContext().getJobName();
        List<JobInstance> instances = explorer.getJobInstances(jobName, 0, Integer.MAX_VALUE);

        log.info(">>> There are {} job instances for the job {}", instances.size(), jobName);
        log.info(">>> They have had the following results");
        log.info(">>> -----------------------------------");

        for (JobInstance instance: instances) {
            List<JobExecution> jobExecutions = explorer.getJobExecutions(instance);
            log.info(">>> Instance {} had {} executions", instance.getInstanceId(), jobExecutions.size());

            for (JobExecution jobExecution: jobExecutions) {
                log.info(">>> \tExecution {} resulted in Exit Status {}",
                    jobExecution.getId(), jobExecution.getExitStatus());
            }
        }

        return RepeatStatus.FINISHED;
    }
}
