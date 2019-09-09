package com.huolongguo.study.attribute;


import com.amazon.ask.attributes.persistence.impl.PartitionKeyGenerators;
import com.amazon.ask.model.RequestEnvelope;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Caris W
 */
@Slf4j
@Repository("PersistentAttributesClient")
public class PersistentAttributesRepository {

    private static final Function<RequestEnvelope, String> DEFAULT_PARTITION_KEY_GENERATOR = PartitionKeyGenerators.deviceId();

    private final Function<RequestEnvelope, String> partitionKeyGenerator;

    @Autowired
    private MongoTemplate mongoTemplate;

    public PersistentAttributesRepository() {
        partitionKeyGenerator = DEFAULT_PARTITION_KEY_GENERATOR;
    }

    public Map<String, Object> getPersistentAttributes(RequestEnvelope envelope) {
        if (envelope == null)
            throw new NullPointerException("RequestEnvelope is null.");

        String partitionKey = partitionKeyGenerator.apply(envelope);
        AttributeValue attributeValue = mongoTemplate.findOne(Query.query(Criteria.where("device_key").is(partitionKey)), AttributeValue.class);
        if (attributeValue != null)
            return attributeValue.getAttributes();
        else
            return new HashMap<>();
    }

    public void setPersistentAttributes(RequestEnvelope envelope, Map<String, Object> persistentAttributes) {
        if (envelope == null)
            throw new NullPointerException("RequestEnvelope is null.");

        if (persistentAttributes == null)
            throw new NullPointerException("PersistentAttributes is null.");

        String partitionKey = partitionKeyGenerator.apply(envelope);
        UpdateResult save = mongoTemplate.upsert(Query.query(Criteria.where("device_key").is(partitionKey)), Update.update("attributes", persistentAttributes), AttributeValue.class);
        if (!save.wasAcknowledged())
            log.error("Save PersistentAttributes error.");
    }

    public void deletePersistentAttributes(RequestEnvelope envelope) {
        if (envelope == null)
            throw new NullPointerException("RequestEnvelope is null.");

        String partitionKey = partitionKeyGenerator.apply(envelope);
        DeleteResult deleteResult = mongoTemplate.remove(Query.query(Criteria.where("device_key").is(partitionKey)), AttributeValue.class);
        log.debug("Delete attributes count: " + deleteResult.getDeletedCount());
    }
}
