package org.mail.delegate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;


public class DefaultErrorHandler implements ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultErrorHandler.class);

    @Override
    public void handleError(Throwable throwable) {
        // TODO: whatever..
        log.error("error occurred..: ", throwable);
    }
}
