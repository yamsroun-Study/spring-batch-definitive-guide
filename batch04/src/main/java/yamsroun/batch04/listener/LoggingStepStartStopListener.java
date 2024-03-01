package yamsroun.batch04.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

@Slf4j
public class LoggingStepStartStopListener {

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info(">>> beforeStep={}", stepExecution.getStepName());
    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info(">>> afterStep={}", stepExecution.getStepName());
        return stepExecution.getExitStatus();
    }
}
