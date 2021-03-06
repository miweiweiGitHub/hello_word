package com.huolongguo.study.interceptor;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class SavePersistentAttributesResponseInterceptor implements ResponseInterceptor {

    @Override
    public void process(HandlerInput handlerInput, Optional<Response> response) {
        response.ifPresent(value -> log.debug(value.toString()));
    }
}
