package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import com.amazon.ask.model.interfaces.audioplayer.ClearBehavior;
import com.amazon.ask.request.Predicates;

import com.huolongguo.study.display.PlayTemplateController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class SessionEndedHandler implements SessionEndedRequestHandler {

    @Override
    public boolean canHandle(HandlerInput input, SessionEndedRequest sessionEndedRequest) {
        return input.matches(Predicates.requestType(SessionEndedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, SessionEndedRequest sessionEndedRequest) {
        log.debug(sessionEndedRequest.toString());
        return input.getResponseBuilder()
                .addDirective(PlayTemplateController.getPlayTemplate(input,null,null,"stop"))
                .addAudioPlayerClearQueueDirective(ClearBehavior.CLEAR_ALL)
                .build();
    }
}
