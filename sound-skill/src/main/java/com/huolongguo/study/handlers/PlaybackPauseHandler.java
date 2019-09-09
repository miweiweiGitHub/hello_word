package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.playbackcontroller.PauseCommandIssuedRequest;
import com.amazon.ask.request.Predicates;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.model.PlaybackInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.huolongguo.study.handlers.Flag.PLAYBACK_INFO;


/**
 * @author Caris W
 */
@Slf4j
@Component
public class PlaybackPauseHandler implements RequestHandler {

    @Autowired
    private AttributesController attributesController;
    @Autowired
    private AudioPlayerController audioPlayerController;

    @Override
    public boolean canHandle(HandlerInput input) {
        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);

        return playbackInfo.isInPlaybackSession() &&
                (input.matches(Predicates.requestType(PauseCommandIssuedRequest.class))
                        || input.matches(Predicates.intentName("AMAZON.PauseIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.debug(input.getRequestEnvelope().getRequest().toString());
        return audioPlayerController.pause(input);
    }
}
