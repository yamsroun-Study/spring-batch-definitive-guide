package yamsroun.batch04.incrementer;

import org.springframework.batch.core.*;

import java.util.Date;

public class DailyJobTimestamper implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters) {
        return new JobParametersBuilder(parameters)
            .addDate("currentDate", new Date())
            .toJobParameters();
    }
}
