package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.ExceptionEncounteredRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.system.ExceptionEncounteredRequest;
import com.amazon.ask.request.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class SystemExceptionHandler implements ExceptionEncounteredRequestHandler {

    @Override
    public boolean canHandle(HandlerInput input, ExceptionEncounteredRequest exceptionEncounteredRequest) {
        return input.matches(Predicates.requestType(ExceptionEncounteredRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, ExceptionEncounteredRequest exceptionEncounteredRequest) {
        log.error("System exception encountered: " + exceptionEncounteredRequest.getError().toString());
        return input.getResponseBuilder()
                .build();
    }
}
