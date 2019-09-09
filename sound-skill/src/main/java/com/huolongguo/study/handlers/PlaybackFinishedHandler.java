package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.PlaybackFinishedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFinishedRequest;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.model.PlaybackInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class PlaybackFinishedHandler implements PlaybackFinishedRequestHandler {

    @Autowired
    private AttributesController attributesController;

    @Override
    public boolean canHandle(HandlerInput input, PlaybackFinishedRequest playbackFinishedRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput input, PlaybackFinishedRequest playbackFinishedRequest) {
        log.debug(playbackFinishedRequest.toString());

        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, Flag.PLAYBACK_INFO, PlaybackInfo.class);
        playbackInfo.setInPlaybackSession(false);
        playbackInfo.setHasPreviousPlaybackSession(false);
        playbackInfo.setNextStreamEnqueued(false);
        attributesController.setPersistentAttribute(input, Flag.PLAYBACK_INFO, playbackInfo);

        return input.getResponseBuilder()
                .build();
    }
}
