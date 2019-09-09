package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.PlaybackStoppedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackStoppedRequest;

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
public class PlaybackStoppedHandler implements PlaybackStoppedRequestHandler {

    @Autowired
    private AttributesController attributesController;

    @Override
    public boolean canHandle(HandlerInput input, PlaybackStoppedRequest playbackStoppedRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput input, PlaybackStoppedRequest playbackStoppedRequest) {
        log.debug(playbackStoppedRequest.toString());

        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);
        playbackInfo.setToken(playbackStoppedRequest.getToken());
        playbackInfo.setOffsetInMilliseconds(playbackStoppedRequest.getOffsetInMilliseconds());
        attributesController.setPersistentAttribute(input, PLAYBACK_INFO, playbackInfo);

        return input.getResponseBuilder()
                .build();
    }
}
