package com.huolongguo.study.interceptor;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.amazon.ask.request.Predicates;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.handlers.Flag;
import com.huolongguo.study.model.PlaybackInfo;
import com.huolongguo.study.model.PlaybackSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Caris W
 */
@Slf4j
@Component
public class LoadPersistentAttributesRequestInterceptor implements RequestInterceptor {

    @Autowired
    private AttributesController attributesController;

    @Override
    public void process(HandlerInput handlerInput) {
        log.debug(handlerInput.getRequestEnvelope().getRequest().toString());
        log.debug(handlerInput.getRequestEnvelope().getContext().toString());

        if (handlerInput.matches(Predicates.intentName("audio"))
                || handlerInput.matches(Predicates.intentName("music"))
                || handlerInput.matches(Predicates.intentName("iFengFM"))
                || handlerInput.matches(Predicates.intentName("pushIntent"))) {
            attributesController.deletePersistentAttribute(handlerInput, Flag.PLAYBACK_INFO);
            attributesController.deletePersistentAttribute(handlerInput, Flag.PLAYBACK_SETTING);

            attributesController.setPersistentAttribute(handlerInput, Flag.PLAYBACK_INFO, new PlaybackInfo());
            attributesController.setPersistentAttribute(handlerInput, Flag.PLAYBACK_SETTING, new PlaybackSetting());
        }
    }
}

