package com.huolongguo.study.helloworld.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
@Component
public class CancelandStopIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Goodbye";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("HelloWorld", speechText)
                .build();
    }
}
