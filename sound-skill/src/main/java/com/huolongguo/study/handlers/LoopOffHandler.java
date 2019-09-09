package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.model.PlaybackInfo;
import com.huolongguo.study.model.PlaybackSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.huolongguo.study.handlers.Flag.PLAYBACK_INFO;
import static com.huolongguo.study.handlers.Flag.PLAYBACK_SETTING;


/**
 * @author Caris W
 */
@Slf4j
@Component
public class LoopOffHandler implements IntentRequestHandler {

    @Autowired
    private AttributesController attributesController;

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);
        return playbackInfo.isInPlaybackSession()
                && input.matches(Predicates.intentName("AMAZON.LoopOffIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        log.debug(intentRequest.toString());

        PlaybackSetting playbackSetting = attributesController.getPersistentAttribute(input, PLAYBACK_SETTING, PlaybackSetting.class);
        playbackSetting.setLoop(false);
        attributesController.setPersistentAttribute(input, PLAYBACK_SETTING, playbackSetting);

        return input.getResponseBuilder()
                .withSpeech("退出循环播放")
                .withShouldEndSession(true)
                .build();
    }
}
