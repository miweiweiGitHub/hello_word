package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.playbackcontroller.NextCommandIssuedRequest;
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
public class PlaybackNextHandler implements RequestHandler {

    @Autowired
    private AttributesController attributesController;
    @Autowired
    private AudioPlayerController audioPlayerController;

    @Override
    public boolean canHandle(HandlerInput input) {
        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);
        return playbackInfo.isInPlaybackSession() &&
                (input.matches(Predicates.requestType(NextCommandIssuedRequest.class))
                        || input.matches(Predicates.intentName("AMAZON.NextIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        log.debug(input.getRequestEnvelope().getRequest().toString());
        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);
        PlaybackSetting playbackSetting = attributesController.getPersistentAttribute(input, PLAYBACK_SETTING, PlaybackSetting.class);

        return audioPlayerController.playNext(input, playbackInfo, playbackSetting);
    }
}
