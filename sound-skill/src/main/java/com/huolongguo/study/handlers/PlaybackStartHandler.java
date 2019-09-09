package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.playbackcontroller.PlayCommandIssuedRequest;
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
public class PlaybackStartHandler implements RequestHandler {

    @Autowired
    private AttributesController attributesController;
    @Autowired
    private AudioPlayerController audioPlayerController;

    @Override
    public boolean canHandle(HandlerInput input) {
        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);

        Request request = input.getRequestEnvelope().getRequest();
        if (!playbackInfo.isInPlaybackSession() && request instanceof IntentRequest) {
            return ((IntentRequest) request).getIntent().getName().contains("PlayAudio");
        }

        return input.matches(Predicates.requestType(PlayCommandIssuedRequest.class))
                || input.matches(Predicates.intentName("PlayAudio"))
                || input.matches(Predicates.intentName("AMAZON.ResumeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.debug(input.getRequestEnvelope().getRequest().toString());

        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);

        return audioPlayerController.play(input, null, playbackInfo, null);
    }
}
