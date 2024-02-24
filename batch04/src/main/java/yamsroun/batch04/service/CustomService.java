package yamsroun.batch04.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomService {

    public void serviceMethod() {
        log.info(">>> Service method was called");
    }

    public void serviceMethod2(String message) {
        log.info(">>> {}", message);
    }
}
