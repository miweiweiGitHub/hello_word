package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.PlaybackNearlyFinishedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackNearlyFinishedRequest;
import com.amazon.ask.request.Predicates;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.display.PlayTemplateController;
import com.huolongguo.study.model.AudioSource;
import com.huolongguo.study.model.PlaybackInfo;
import com.huolongguo.study.model.PlaybackSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class PlaybackNearlyFinishedHandler implements PlaybackNearlyFinishedRequestHandler {

    @Autowired
    private AttributesController attributesController;

    @Override
    public boolean canHandle(HandlerInput input, PlaybackNearlyFinishedRequest playbackNearlyFinishedRequest) {
        return input.matches(Predicates.requestType(PlaybackNearlyFinishedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, PlaybackNearlyFinishedRequest playbackNearlyFinishedRequest) {
        log.debug(playbackNearlyFinishedRequest.toString());

        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, Flag.PLAYBACK_INFO, PlaybackInfo.class);
        PlaybackSetting playbackSetting = attributesController.getPersistentAttribute(input, Flag.PLAYBACK_SETTING, PlaybackSetting.class);

        if (!playbackInfo.isNextStreamEnqueued()) {
            int enqueueIndex = (playbackInfo.getIndex() + 1) % playbackInfo.getPlayOrder().size();
            log.debug("enqueueIndex: " + enqueueIndex);
            if (enqueueIndex != 0 || playbackSetting.isLoop()) {
                playbackInfo.setNextStreamEnqueued(true);

                AudioSource audioSource = playbackInfo.getPlayOrder().get(enqueueIndex);
                String enqueueToken = String.valueOf(audioSource.hashCode());
                String expectedPreviousToken = playbackInfo.getToken();
                long offsetInMilliseconds = 0;

                playbackInfo.setOffsetInMilliseconds(offsetInMilliseconds);

                attributesController.setPersistentAttribute(input, Flag.PLAYBACK_INFO, playbackInfo);
                attributesController.setPersistentAttribute(input, Flag.PLAYBACK_SETTING, playbackSetting);

                return input
                        .getResponseBuilder()
                        .addDirective(PlayTemplateController.getPlayTemplate(input,playbackInfo,playbackSetting,""))
                        .addAudioPlayerPlayDirective(PlayBehavior.ENQUEUE, offsetInMilliseconds, expectedPreviousToken, enqueueToken, audioSource.getUrl())
                        .withShouldEndSession(true)
                        .build();
            }
        }

        log.error("next stream enqueued: false");
        return input.getResponseBuilder()
                .build();
    }
}
