package com.huolongguo.study;

import com.amazon.ask.servlet.ServletConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
        // 暂时忽略鉴权
        System.setProperty(ServletConstants.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY, "3600000");
        System.setProperty(ServletConstants.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY, "true");

        SpringApplication.run(SampleApplication.class, args);
    }
}


