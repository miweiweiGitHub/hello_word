package com.huolongguo.study.service.impl;

import com.huolongguo.study.config.SoundConfig;
import com.huolongguo.study.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by lw on 2019/9/6 10:08
 */
@Component
@Slf4j
public class BaseServiceImpl implements BaseService {

    @Autowired
    SoundConfig soundConfig;

    @Override
    public String[] getIntent() {

        return soundConfig.getUrls();
    }
}
