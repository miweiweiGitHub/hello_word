package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.PlaybackFailedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.ClearBehavior;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFailedRequest;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.display.PlayTemplateController;
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
public class PlaybackFailedHandler implements PlaybackFailedRequestHandler {

    @Autowired
    private AttributesController attributesController;

    @Override
    public boolean canHandle(HandlerInput input, PlaybackFailedRequest playbackFailedRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput input, PlaybackFailedRequest playbackFailedRequest) {
        log.error(playbackFailedRequest.toString());

        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);
        playbackInfo.setInPlaybackSession(false);
        attributesController.setPersistentAttribute(input, PLAYBACK_INFO, playbackInfo);

        return input.getResponseBuilder()
                .withSpeech("播放错误")
                .addDirective(PlayTemplateController.getPlayTemplate(input,null,null,"stop"))
                .addAudioPlayerClearQueueDirective(ClearBehavior.CLEAR_ALL)
                .withShouldEndSession(true)
                .build();
    }
}
