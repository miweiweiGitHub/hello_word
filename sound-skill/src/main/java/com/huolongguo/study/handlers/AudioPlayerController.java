package com.huolongguo.study.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.*;
import com.amazon.ask.model.interfaces.display.Image;
import com.amazon.ask.model.interfaces.display.ImageInstance;
import com.amazon.ask.model.interfaces.display.ImageSize;
import com.amazon.ask.response.ResponseBuilder;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.display.PlayTemplateController;
import com.huolongguo.study.model.AudioSource;
import com.huolongguo.study.model.PlaybackInfo;
import com.huolongguo.study.model.PlaybackSetting;
import com.huolongguo.study.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.huolongguo.study.handlers.Flag.PLAYBACK_INFO;
import static com.huolongguo.study.handlers.Flag.PLAYBACK_SETTING;


/**
 * @author Caris W
 */
@Slf4j
@Component
public class AudioPlayerController {

    @Autowired
    private AttributesController attributesController;

    public synchronized Optional<Response> play(HandlerInput input, String speechText, PlaybackInfo playbackInfo, ClearBehavior clearBehavior) {
        PlaybackSetting playbackSetting = attributesController.getPersistentAttribute(input, PLAYBACK_SETTING, PlaybackSetting.class);
        ResponseBuilder responseBuilder = input.getResponseBuilder();

        AudioSource audioSource = playbackInfo.getPlayOrder().get(playbackInfo.getIndex());
        String expectedPreviousToken = playbackInfo.getToken();
        long offsetInMilliseconds = playbackInfo.getOffsetInMilliseconds();

        if (!TextUtils.isEmpty(speechText))
            responseBuilder.withSpeech(speechText);

        if (clearBehavior != null)
            responseBuilder.addAudioPlayerClearQueueDirective(clearBehavior);

        return responseBuilder
                .withSpeech(TextUtils.isEmpty(speechText) ?
                        "好的，为您播放" + audioSource.getTitle() : speechText)
                .addDirective(PlayTemplateController.getPlayTemplate(input,playbackInfo,playbackSetting,""))
                .addDirective(generatePlayDirective(audioSource, expectedPreviousToken, offsetInMilliseconds, PlayBehavior.REPLACE_ALL))
                .withShouldEndSession(true)
                .build();
    }

    public synchronized Optional<Response> stop(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech("好的，停止播放。")
                .addAudioPlayerStopDirective()
                .withShouldEndSession(true)
                .build();
    }

    public synchronized Optional<Response> pause(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech("好的，暂停播放。")
                .addDirective(PlayTemplateController.getPlayTemplate(input,null,null,"pause"))
                .addAudioPlayerStopDirective()
                .withShouldEndSession(true)
                .build();
    }

    public synchronized Optional<Response> playNext(HandlerInput input, PlaybackInfo playbackInfo, PlaybackSetting playbackSetting) {
        int nextIndex = (playbackInfo.getIndex() + 1) % playbackInfo.getPlayOrder().size();

        if (nextIndex == 0 && !playbackSetting.isLoop()) {
            return input.getResponseBuilder()
                    .withSpeech("已经到列表最后啦")
                    .addDirective(PlayTemplateController.getPlayTemplate(input,null,null,"stop"))
                    .withShouldEndSession(true)
                    .build();
        }

        playbackInfo.setIndex(nextIndex);
        playbackInfo.setOffsetInMilliseconds(0);

        attributesController.setPersistentAttribute(input, PLAYBACK_INFO, playbackInfo);

        return play(input, null, playbackInfo, null);
    }

    public synchronized Optional<Response> playPrevious(HandlerInput input, PlaybackInfo playbackInfo, PlaybackSetting playbackSetting) {
        int previousIndex = playbackInfo.getIndex() - 1;

        if (previousIndex == -1) {
            if (playbackSetting.isLoop()) {
                previousIndex += playbackInfo.getPlayOrder().size();
            } else {
                return input.getResponseBuilder()
                        .withSpeech("已经到列表顶端啦")
                        .withShouldEndSession(true)
                        .build();
            }
        }

        playbackInfo.setIndex(previousIndex);
        playbackInfo.setOffsetInMilliseconds(0);

        attributesController.setPersistentAttribute(input, PLAYBACK_INFO, playbackInfo);

        return play(input, null, playbackInfo, null);
    }

    private PlayDirective generatePlayDirective(AudioSource audioSource, String expectedPreviousToken, long offsetInMilliseconds, PlayBehavior playBehavior) {
        Stream stream = Stream.builder()
                .withOffsetInMilliseconds(offsetInMilliseconds)
                .withExpectedPreviousToken(expectedPreviousToken)
                .withToken(audioSource.getToken())
                .withUrl(audioSource.getUrl())
                .build();

        AudioItem audioItem = AudioItem.builder()
                .withStream(stream)
                .withMetadata(AudioItemMetadata.builder()
                        .withTitle(audioSource.getTitle())
                        .withSubtitle(audioSource.getSubTitle())
                        .withArt(Image.builder()
                                .addSourcesItem(ImageInstance.builder()
                                        .withUrl(TextUtils.isEmpty(audioSource.getArtImageUrl()) ?
                                                "https://img.wowoqq.com/allimg/190402/1-1Z402101642500.jpg" : audioSource.getArtImageUrl())
                                        .withSize(ImageSize.SMALL)
                                        .build())
                                .build())
                        .withBackgroundImage(Image.builder()
                                .addSourcesItem(ImageInstance.builder()
                                        .withUrl(TextUtils.isEmpty(audioSource.getBackgroundImageUrl()) ?
                                                "https://p.ssl.qhimg.com/dmfd/400_300_/t0120b2f23b554b8402.jpg" : audioSource.getArtImageUrl())
                                        .withSize(ImageSize.LARGE)
                                        .build())
                                .build())
                        .build())
                .build();

        return PlayDirective.builder()
                .withPlayBehavior(playBehavior)
                .withAudioItem(audioItem)
                .build();
    }
}
