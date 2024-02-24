package yamsroun.batch04.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomService {

    public void serviceMethod() {
        log.info(">>> Service method was called");
    }
}
