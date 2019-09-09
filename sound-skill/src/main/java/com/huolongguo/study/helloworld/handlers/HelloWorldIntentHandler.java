package com.huolongguo.study.helloworld.handlers;

import com.alibaba.fastjson.JSONObject;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.huolongguo.study.config.SoundConfig;
import com.huolongguo.study.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

@Slf4j
@Component
public class HelloWorldIntentHandler implements IntentRequestHandler {

    @Autowired
    private SoundConfig soundConfig;
    @Autowired
    private BaseService baseService;

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest  intentRequest) {
//        Map<String, Object> persistentAttributes = input.getAttributesManager().getPersistentAttributes();
//        Map<String, Object> requestAttributes = input.getAttributesManager().getRequestAttributes();
//        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
//        log.info("persistentAttributes:{}",persistentAttributes);
//        log.info("requestAttributes:{}",requestAttributes);
//        log.info("sessionAttributes:{}",sessionAttributes);
       String[] intent = baseService.getIntent();
        log.info("input:{},intent:{}",input,intentRequest);
        String slotName = intentRequest.getIntent().getSlots().get("SlotName").getValue();
        JSONObject jsonObject = JSONObject.parseObject(slotName);
        //这里拿到text
        String query = jsonObject.getString("query");

        return input.matches(intentName("HelloWorldIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest  intentRequest) {


        String version = input.getRequestEnvelope().getVersion();

        RequestEnvelope requestEnvelope = input.getRequestEnvelope();
        SystemState system = requestEnvelope.getContext().getSystem();
        Request request = requestEnvelope.getRequest();

        String speechText = "Hello world";
       return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("HelloWorld", speechText)
                .build();
    }


//    @Override
//    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
//        System.out.print(TAG + ": canhandle");
//        log.debug("canhandle!!!!!");
////        return handlerInput.matches(Predicates.intentName("photography_show"));
//        return "photograthy_show".equals(intentRequest.getIntent().getName());
//
//    }
//
//    @Override
//    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
//
//        log.debug("handle!!!!!");
//        Slot slot = intentRequest.getIntent().getSlots().get("SlotName");
//        String value = slot.getValue();
//        String speechText;
//
//        log.debug("LevyDateHandler value = " + value);
//
//        JSONObject jsonObject = JSON.parseObject(value);
//        JSONObject parameterObject = jsonObject.getJSONObject("parameters");    // 配置的词槽都在此字段里面
//
//        String author = parameterObject.getString("作者");
//        String next = parameterObject.getString("下一幅");
//        PhotoBean photoInfo;
//        if (author == null && next != null) {
//            index ++;
//            photoInfo = getPhotoInfo(tmp_author,index);
//        }else if(next == null){
//            tmp_author = author;
//            photoInfo = getPhotoInfo(author,index);
//        }else {
//            tmp_author = "热门";
//            photoInfo = getPhotoInfo("热门",index);
//        }
//
//        speechText = getSpeech(photoInfo);
//
//
//        return handlerInput.getResponseBuilder()
//                .withSpeech(speechText)
//                .withSimpleCard("Photography", speechText)
//                .addDirective(getTemplate(photoInfo))
//                .build();
//    }

}
