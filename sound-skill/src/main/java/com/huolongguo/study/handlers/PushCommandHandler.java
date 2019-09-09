package com.huolongguo.study.handlers;

import com.alibaba.fastjson.JSONObject;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.interfaces.audioplayer.ClearBehavior;
import com.amazon.ask.request.Predicates;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.model.AudioSource;
import com.huolongguo.study.model.PlaybackInfo;
import com.huolongguo.study.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class PushCommandHandler implements IntentRequestHandler {

    @Autowired
    private AttributesController attributesController;
    @Autowired
    private AudioPlayerController audioPlayerController;

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(Predicates.intentName("pushIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, Flag.PLAYBACK_INFO, PlaybackInfo.class);

        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        Slot slot = slots.get("SlotName");
        String value = slot.getValue();
        JSONObject data = JSONObject.parseObject(value);

        String speechText = data.getString("answer");
        String audioUrl = data.getString("audio_url");
        if (!TextUtils.isEmpty(speechText) && !TextUtils.isEmpty(audioUrl)) {
            AudioSource audioSource = new AudioSource();
            audioSource.setTitle(speechText);
            audioSource.setUrl(audioUrl);
            playbackInfo.getPlayOrder().add(audioSource);
            attributesController.setPersistentAttribute(input, Flag.PLAYBACK_INFO, playbackInfo);

            return audioPlayerController.play(input, speechText, playbackInfo, ClearBehavior.CLEAR_ALL);
        }

        return input.getResponseBuilder()
                .withSpeech("暂时没有该资源")
                .build();
    }
}
