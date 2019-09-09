package com.huolongguo.study.module.ifengnews.interceptor;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class LoadPersistentAttributesRequestInterceptorifengnews implements RequestInterceptor {

    @Override
    public void process(HandlerInput handlerInput) {
        log.debug(handlerInput.getRequestEnvelope().getRequest().getType());
        log.debug(handlerInput.getRequestEnvelope().toString());

    }

}

