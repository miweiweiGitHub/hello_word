package com.huolongguo.study.attribute;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Caris W
 */
@Component
public class AttributesController {

    @Autowired
    private PersistentAttributesRepository repository;

    public synchronized <T> T getPersistentAttribute(HandlerInput input, String key, Class<T> tClass) {
        return new ObjectMapper().convertValue(repository.getPersistentAttributes(input.getRequestEnvelope()).get(key), tClass);
    }

    public synchronized <T> void setPersistentAttribute(HandlerInput input, String key, T t) {
        RequestEnvelope requestEnvelope = input.getRequestEnvelope();
        Map<String, Object> persistentAttributes = repository.getPersistentAttributes(requestEnvelope);
        if (persistentAttributes != null) {
            persistentAttributes.put(key, t);
            repository.setPersistentAttributes(requestEnvelope, persistentAttributes);
        }
    }

    public synchronized <T> void deletePersistentAttribute(HandlerInput input, String key) {
        RequestEnvelope requestEnvelope = input.getRequestEnvelope();
        Map<String, Object> persistentAttributes = repository.getPersistentAttributes(requestEnvelope);
        persistentAttributes.remove(key);
        repository.setPersistentAttributes(requestEnvelope, persistentAttributes);
    }

    public synchronized Map<String, Object> getPersistentAttributes(HandlerInput input) {
        return repository.getPersistentAttributes(input.getRequestEnvelope());
    }

    public synchronized void setPersistentAttributes(HandlerInput input, Map<String, Object> persistentAttributes) {
        RequestEnvelope requestEnvelope = input.getRequestEnvelope();
        repository.setPersistentAttributes(requestEnvelope, persistentAttributes);
    }

    public synchronized void deletePersistentAttributes(HandlerInput input) {
        repository.deletePersistentAttributes(input.getRequestEnvelope());
    }
}
