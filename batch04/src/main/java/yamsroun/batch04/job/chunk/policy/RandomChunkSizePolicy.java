package yamsroun.batch04.job.chunk.policy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.repeat.*;

import java.util.Random;

@Slf4j
public class RandomChunkSizePolicy implements CompletionPolicy {

    private int chunkSize;
    private int totalProcessed;
    private Random random = new Random();

    @Override
    public boolean isComplete(RepeatContext context, RepeatStatus result) {
        if (RepeatStatus.FINISHED == result) {
            return true;
        }
        return isComplete(context);
    }

    @Override
    public boolean isComplete(RepeatContext context) {
        return totalProcessed >= chunkSize;
    }

    @Override
    public RepeatContext start(RepeatContext parent) {
        chunkSize = random.nextInt(20);
        totalProcessed = 0;
        log.info(">>> The chunk size has been set to {}", chunkSize);
        return parent;
    }

    @Override
    public void update(RepeatContext context) {
        totalProcessed++;
    }
}
