package com.huolongguo.study.attribute;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * @author Caris W
 */
@Data
@Document(collection = "attribute")
public class AttributeValue {

    @Field("device_key")
    private String deviceKey;
    @Field("attributes")
    private Map<String, Object> attributes;
}
