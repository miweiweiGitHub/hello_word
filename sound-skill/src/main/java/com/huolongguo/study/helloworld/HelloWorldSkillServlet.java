package com.huolongguo.study.helloworld;


import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;
import com.huolongguo.study.config.SoundConfig;
import com.huolongguo.study.helloworld.handlers.*;
import com.huolongguo.study.helloworld.interceptor.HelloWorldRequestInterceptor;
import com.huolongguo.study.helloworld.interceptor.HelloWorldResponseInterceptor;
import com.huolongguo.study.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@Component
@WebServlet("/soundai/test")
public class HelloWorldSkillServlet extends SkillServlet {

    //静态 new 出来的拿到的数据是 null

    @Autowired
    public HelloWorldSkillServlet(
            CancelandStopIntentHandler canceland,
            HelloWorldIntentHandler helloWorld,
            HelpIntentHandler helpIntent,
            LaunchRequestHandler launchRequest,
            SessionEndedRequestHandler sessionEndedRequest,
            HelloWorldRequestInterceptor helloWorldRequestInterceptor,
            HelloWorldResponseInterceptor helloWorldResponseInterceptor

    ) {
        super(getSkill(
                 canceland,
                 helloWorld,
                 helpIntent,
                 launchRequest,
                 sessionEndedRequest,
                 helloWorldRequestInterceptor,
                 helloWorldResponseInterceptor
        ));
    }

    private static Skill getSkill( CancelandStopIntentHandler canceland,
                                   HelloWorldIntentHandler helloWorld,
                                   HelpIntentHandler helpIntent,
                                   LaunchRequestHandler launchRequest,
                                   SessionEndedRequestHandler sessionEndedRequest,
                                   HelloWorldRequestInterceptor helloWorldRequestInterceptor,
                                   HelloWorldResponseInterceptor helloWorldResponseInterceptor) {
        return Skills.standard()
                .addRequestHandlers(
                        canceland,
                        helloWorld,
                        helpIntent,
                        launchRequest,
                        sessionEndedRequest)
                .addRequestInterceptor(helloWorldRequestInterceptor)
                .addResponseInterceptor(helloWorldResponseInterceptor)
                // Add your skill id below
//                .withSkillId("")
                .build();
    }



}
