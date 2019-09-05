package com.huolongguo.study.helloworld;


import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;
import com.huolongguo.study.helloworld.handlers.*;
import com.huolongguo.study.helloworld.interceptor.HelloWorldRequestInterceptor;
import com.huolongguo.study.helloworld.interceptor.HelloWorldResponseInterceptor;

import javax.servlet.annotation.WebServlet;

@WebServlet("/v1/helloworld")
public class HelloWorldSkillServlet extends SkillServlet {

    public HelloWorldSkillServlet() {
        super(getSkill());
    }

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new HelloWorldIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler())
                .addRequestInterceptor(new HelloWorldRequestInterceptor())
                .addResponseInterceptor(new HelloWorldResponseInterceptor())
                // Add your skill id below
//                .withSkillId("")
                .build();
    }

}
