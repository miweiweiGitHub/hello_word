//package com.huolongguo.study.demo;
//
//import com.amazon.ask.Skill;
//import com.amazon.ask.Skills;
//import com.amazon.ask.servlet.SkillServlet;
//import com.soundai.skill.handlers.*;
//import com.soundai.skill.module.ifengnews.handlers.GetAlbumIntentHandler;
//import com.soundai.skill.interceptor.LoadPersistentAttributesRequestInterceptor;
//import com.soundai.skill.interceptor.SavePersistentAttributesResponseInterceptor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.annotation.WebServlet;
//
///**
// * @author Caris W
// */
//@WebServlet("/skill/IFengFM")
//public class ifengnewsSkillServlet extends SkillServlet {
//
//    @Autowired
//    public ifengnewsSkillServlet(CheckAudioInterfaceHandler checkAudioInterfaceHandler,
//                          SystemExceptionHandler systemExceptionHandler,
//                          SessionEndedHandler sessionEndedHandler,
//                          PlaybackStartHandler playbackStartHandler,
//                          PlaybackNextHandler playbackNextHandler,
//                          PlaybackPreviousHandler playbackPreviousHandler,
//                          PlaybackPauseHandler playbackPauseHandler,
//                          PlaybackStopHandler playbackStopHandler,
//                          LoopOnHandler loopOnHandler,
//                          LoopOffHandler loopOffHandler,
//                          ShuffleOnHandler shuffleOnHandler,
//                          ShuffleOffHandler shuffleOffHandler,
//                          StartOverHandler startOverHandler,
//                          PlaybackStartedHandler playbackStartedHandler,
//                          PlaybackNearlyFinishedHandler playbackNearlyFinishedHandler,
//                          PlaybackStoppedHandler playbackStoppedHandler,
//                          PlaybackFinishedHandler playbackFinishedHandler,
//                          PlaybackFailedHandler playbackFailedHandler,
//                                 GetAlbumIntentHandler getAlbumIntentHandler,
//                          LoadPersistentAttributesRequestInterceptor loadPersistentAttributesRequestInterceptor,
//                          SavePersistentAttributesResponseInterceptor savePersistentAttributesResponseInterceptor,
//                          ErrorHandler errorHandler) {
//        super(Skills.standard()
//                .addRequestHandlers(checkAudioInterfaceHandler,
//                        systemExceptionHandler,
//                        sessionEndedHandler,
//                        playbackStartHandler,
//                        playbackNextHandler,
//                        playbackPreviousHandler,
//                        playbackPauseHandler,
//                        playbackStopHandler,
//                        loopOnHandler,
//                        loopOffHandler,
//                        shuffleOnHandler,
//                        shuffleOffHandler,
//                        startOverHandler,
//                        playbackStartedHandler,
//                        playbackNearlyFinishedHandler,
//                        playbackStoppedHandler,
//                        playbackFinishedHandler,
//                        playbackFailedHandler,
//                        getAlbumIntentHandler)
//                .addRequestInterceptor(loadPersistentAttributesRequestInterceptor)
//                .addResponseInterceptor(savePersistentAttributesResponseInterceptor)
//                .addExceptionHandler(errorHandler)
//                // Add your skill id below
//                .withSkillId("9ba765f4957cd0b8dda613a0a0d0187a")
//                .build());
//    }
//
//}
