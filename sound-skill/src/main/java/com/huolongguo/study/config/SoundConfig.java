package com.huolongguo.study.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by lw on 2019/9/6 9:53
 */

@Data
@Component
@ConfigurationProperties(prefix = "test")
public class SoundConfig {
    private String[] urls;
}
