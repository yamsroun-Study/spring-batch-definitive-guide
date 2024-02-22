package yamsroun.batch04.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Map;

@Slf4j
public class GoodByeTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(
        StepContribution contribution,
        ChunkContext context
    ) {
        StepContext stepContext = context.getStepContext();
        Map<String, Object> jobParameters = stepContext.getJobParameters();
        String fileName = (String) jobParameters.get("fileName");
        log.info(">>> fileName = {}", fileName);

        return RepeatStatus.FINISHED;
    }
}
