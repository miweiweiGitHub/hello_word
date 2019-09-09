package com.huolongguo.study.display;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Directive;
import com.amazon.ask.model.interfaces.display.Image;
import com.huolongguo.study.model.AudioSource;
import com.huolongguo.study.model.PlaybackInfo;
import com.huolongguo.study.model.PlaybackSetting;


public class PlayTemplateController {

    public static Directive getPlayTemplate(HandlerInput input, PlaybackInfo playbackInfo, PlaybackSetting playbackSetting, String action) {
        RenderPlayerInfo playTemplate;
        if (action == null || action.equals("")) {
            playTemplate = generatePlayTemplate(input, playbackInfo, playbackSetting);
        } else {
            playTemplate = handlePlayTemplate(action);
        }

        return com.amazon.ask.model.interfaces.display.RenderPlayerInfoDirective.builder()
                .withTemplate(playTemplate)
                .build();
    }

    private static RenderPlayerInfo generatePlayTemplate(HandlerInput input, PlaybackInfo playbackInfo, PlaybackSetting playbackSetting) {
        boolean isFirst = false;
        boolean isLast = false;
        String tittle = playbackInfo.getPlayOrder().get(playbackInfo.getIndex()).getTitle();

        int nextIndex = (playbackInfo.getIndex() + 1) % playbackInfo.getPlayOrder().size();
        if (nextIndex == 0 && !playbackSetting.isLoop()) {
            isLast = true;
        }
        int previousIndex = playbackInfo.getIndex() - 1;
        if (previousIndex == -1 && !playbackSetting.isLoop()) {
            isFirst = true;
        }

        AudioSource audioSource = playbackInfo.getPlayOrder().get(playbackInfo.getIndex());
        String token = audioSource.getToken();
        String expectedPreviousToken = playbackInfo.getToken();
        long offsetInMilliseconds = playbackInfo.getOffsetInMilliseconds();
        String iconImageURL="http://img2.3png.com/556689cb126e3b080e90e224fb3cac6dac1d.png";
        try{
            iconImageURL=audioSource.getArtImageUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RenderPlayerInfo.Builder builder = RenderPlayerInfo.builder()
                //.withType("PlaybackControllerTemplate")
                //.withTemplate(TemplateBean.builder()
                .withAudioItemId("anonymous")
                .withContent(Content.builder()
                        .withTitle(tittle)
                        //.withTitleSubtext1(musList.get(0))
                        //.withTitleSubtext2("未註明來源")
                        .withHeader("凤凰FM")
                        //.withHeaderSubtext1("未註明來源")
                        .withMediaLengthInMilliseconds(String.valueOf(offsetInMilliseconds))
                        .withArt(Image.builder()
                                .withContentDescription("Art")
                                .addSourcesItem(com.amazon.ask.model.interfaces.display.ImageInstance.builder()
                                        //下面的是艺术家图片
                                        .withUrl(iconImageURL).build()).build())
                        .withProvider(Provider.builder()
                                .withName(audioSource.getSubTitle())
                                .withLogo((Image.builder()
                                        .withContentDescription("Art")
                                        .addSourcesItem(com.amazon.ask.model.interfaces.display.ImageInstance.builder()
                                                //下面的是提供者图片
                                                .withUrl("http://diantai.ifeng.com/web/static/index/images/logo.png").build()).build())).build())
                        .build())
                .addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("PLAY_PAUSE")
                        .withEnabled(true)
                        .withSelected(true)
                        .build())
                .addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("NEXT")
                        .withEnabled(!isLast)
                        .withSelected(isLast)
                        .build())
                .addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("PREVIOUS")
                        .withEnabled(!isFirst)
                        .withSelected(isFirst)
                        .build())
                .addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("SKIP_FORWARD")
                        .withEnabled(true)
                        .withSelected(false)
                        .build())
                .addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("SKIP_BACKWARD")
                        .withEnabled(true)
                        .withSelected(false)
                        .build())
                .addControls(Control.builder()
                        .withType("TOGGLE")
                        .withName("SHUFFLE")
                        .withEnabled(true)
                        .withSelected(playbackSetting.isShuffle())
                        .build())
                .addControls(Control.builder()
                        .withType("TOGGLE")
                        .withName("LOOP")
                        .withEnabled(true)
                        .withSelected(playbackSetting.isLoop())
                        .build());
//                .addControls(Control.builder()
//                        .withType("TOGGLE")
//                        .withName("THUMBS_UP")
//                        .withEnabled(true)
//                        .withSelected(false)
//                        .build())
//                .addControls(Control.builder()
//                        .withType("TOGGLE")
//                        .withName("THUMBS_DOWN")
//                        .withEnabled(true)
//                        .withSelected(false)
//                        .build());


        return builder.build();
    }

    private static RenderPlayerInfo handlePlayTemplate(String action) {
        RenderPlayerInfo.Builder builder = RenderPlayerInfo.builder();
        builder.withAudioItemId("anonymous");

        if (action.equals("stop") || action.equals("pause")) {
            builder.addControls(Control.builder()
                    .withType("BUTTON")
                    .withName("PLAY_PAUSE")
                    .withEnabled(true)
                    .withSelected(true)
                    .build());
        }

/*        builder.addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("NEXT")
                        .withEnabled(!isLast)
                        .withSelected(isLast)
                        .build());
        builder.addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("PREVIOUS")
                        .withEnabled(!isFirst)
                        .withSelected(isFirst)
                        .build());
        builder.addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("SKIP_FORWARD")
                        .withEnabled(true)
                        .withSelected(false)
                        .build());
        builder.addControls(Control.builder()
                        .withType("BUTTON")
                        .withName("SKIP_BACKWARD")
                        .withEnabled(true)
                        .withSelected(false)
                        .build());*/
        if (action.equals("SHUFFLE_OFF")) {
            builder.addControls(Control.builder()
                    .withType("TOGGLE")
                    .withName("SHUFFLE")
                    .withEnabled(true)
                    .withSelected(false)
                    .build());
        }
        if (action.equals("SHUFFLE_ON")) {
            builder.addControls(Control.builder()
                    .withType("TOGGLE")
                    .withName("SHUFFLE")
                    .withEnabled(true)
                    .withSelected(true)
                    .build());
        }

        if (action.equals("LOOP_OFF")) {
            builder.addControls(Control.builder()
                    .withType("TOGGLE")
                    .withName("LOOP")
                    .withEnabled(true)
                    .withSelected(false)
                    .build());
        }
        if (action.equals("LOOP_ON")) {
            builder.addControls(Control.builder()
                    .withType("TOGGLE")
                    .withName("LOOP")
                    .withEnabled(true)
                    .withSelected(true)
                    .build());
        }

        return builder.build();
    }
}
