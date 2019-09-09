package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.RequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class CheckAudioInterfaceHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return RequestHelper.forHandlerInput(input).getSupportedInterfaces().getAudioPlayer() == null;
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.debug(input.getRequestEnvelope().getRequest().toString());

        return input.getResponseBuilder()
                .withSpeech("您的设备不支持此技能")
                .withShouldEndSession(true)
                .build();
    }
}
