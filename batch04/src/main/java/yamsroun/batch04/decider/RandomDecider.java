package yamsroun.batch04.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.Random;

public class RandomDecider implements JobExecutionDecider {

    private static final Random RANDOM = new Random();

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if (RANDOM.nextBoolean()) {
            return new FlowExecutionStatus(FlowExecutionStatus.COMPLETED.getName());
        }
        return new FlowExecutionStatus(FlowExecutionStatus.FAILED.getName());
    }
}
