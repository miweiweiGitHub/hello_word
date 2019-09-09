package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

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
public class StartOverHandler implements IntentRequestHandler {

    @Autowired
    private AttributesController attributesController;
    @Autowired
    private AudioPlayerController audioPlayerController;

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, Flag.PLAYBACK_INFO, PlaybackInfo.class);
        return playbackInfo.isInPlaybackSession()
                && input.matches(Predicates.intentName("AMAZON.StartOverIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        log.debug(intentRequest.toString());

        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, Flag.PLAYBACK_INFO, PlaybackInfo.class);
        playbackInfo.setIndex(0);
        playbackInfo.setOffsetInMilliseconds(0);
        playbackInfo.setNextStreamEnqueued(false);
        playbackInfo.setToken(null);
        attributesController.setPersistentAttribute(input, Flag.PLAYBACK_INFO, playbackInfo);

        return audioPlayerController.play(input, "好的，重新播放列表内容", playbackInfo, null);
    }
}
