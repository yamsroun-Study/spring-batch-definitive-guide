package yamsroun.batch04.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class HelloWorldTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(
        StepContribution contribution,
        ChunkContext context
    ) {
        StepContext stepContext = context.getStepContext();
        Map<String, Object> jobParameters = stepContext
            .getJobParameters();
        String name = (String) jobParameters.get("name");

        ExecutionContext jobContext = stepContext.getStepExecution()
            .getJobExecution()
            .getExecutionContext();
        jobContext.put("user.name", name);
        log.info(">>> Hello, {}", name);

        return RepeatStatus.FINISHED;
    }
}
