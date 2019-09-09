package com.huolongguo.study;

import com.amazon.ask.servlet.ServletConstants;
import com.huolongguo.study.config.SoundConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ServletComponentScan
@SpringBootApplication
@ComponentScan
public class SoundApplication {



    public static void main(String[] args) {
        // 暂时忽略鉴权
        System.setProperty(ServletConstants.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY, "3600000");
        System.setProperty(ServletConstants.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY, "true");

        ApplicationContext run = SpringApplication.run(SoundApplication.class, args);
        for (String beanDefinitionName : run.getBeanDefinitionNames()) {
            System.out.println("run : "+beanDefinitionName);
            if (beanDefinitionName.endsWith("Config")){
                SoundConfig bean = (SoundConfig)run.getBean(beanDefinitionName);

                System.out.println("getBean: "+bean.getUrls());
            }

        }
    }
}


