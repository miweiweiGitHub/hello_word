/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.huolongguo.study.module.ifengnews.handlers;

import com.alibaba.fastjson.JSONObject;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.model.interfaces.audioplayer.ClearBehavior;
import com.amazon.ask.request.Predicates;

import com.huolongguo.study.attribute.AttributesController;
import com.huolongguo.study.handlers.AudioPlayerController;
import com.huolongguo.study.model.AudioSource;
import com.huolongguo.study.model.PlaybackInfo;
import com.huolongguo.study.module.ifengnews.model.ListBean;
import com.huolongguo.study.module.ifengnews.model.iFengFMResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.*;

import static com.huolongguo.study.handlers.Flag.PLAYBACK_INFO;


@Slf4j
@Component
public class GetAlbumIntentHandler implements IntentRequestHandler {
    private List<Integer> numlist = Arrays.asList(193187, 194169, 194168, 92394, 192803, 92300, 161554, 194641, 194144, 92395, 194648, 194798, 194642, 92378, 161552, 76301, 194422, 194435, 194477, 194443, 194429, 194430, 194437, 91624, 161553, 91147, 88260, 82557, 83183, 79497, 194147, 196445, 211701, 211712, 211691, 211697, 211676, 211705, 211673, 211675, 211706, 211702, 211699, 211692, 211666, 211665, 211664, 211663, 211651, 211711, 211704, 211686, 211684, 211689, 211710, 211703, 211700, 211693, 211682, 211674, 211680, 211679, 211677, 211678, 211662, 211661, 211659, 211657, 211655, 211653, 211652, 211687, 211695, 211690, 212098, 212109, 212029, 212034, 212030, 212035, 212031, 212032, 212036, 212037, 212038, 212039, 211688, 211448);
    private List<String> namelist = Arrays.asList("资讯一点通", "财经一点通", "体育一点通", "小报告", "科技一点通", "凤凰评论家", "睡前故事会－熊猫天天", "夜话情感", "科技快讯", "凤凰财知道", "资讯早报", "管理一点通", "夜听美文", "凤凰财经", "小朋友大百科－熊猫天天", "宝妈么么哒", "情商培养—熊猫天天睡前故事会", "成语故事—国学小课堂", "哄睡故事—熊猫天天睡前故事会", "善良故事—熊猫天天睡前故事会", "格林童话系列—熊猫天天睡前故事会", "中国民间故事—熊猫天天睡前故事会", "百科小故事—熊猫天天睡前故事会", "凤凰夜读", "国学小课堂－熊猫天天", "命运转转转", "书语人", "大咖来了", "好奇动物园", "大演说家", "绿野仙踪－熊猫天天睡前故事会", "爱丽丝梦游仙境—熊猫天天睡前故事会", "性格培养—熊猫天天睡前故事会", "心灵养成—熊猫天天睡前故事会", "思维养成—熊猫天天睡前故事会", "习惯养成—熊猫天天睡前故事会", "传统知识培养—熊猫天天睡前故事会", "艺术修养—熊猫天天睡前故事会", "安全教育—熊猫天天睡前故事会", "语言表达—熊猫天天睡前故事会", "外国民间童话故事—熊猫天天睡前故事会", "安徒生童话系列—熊猫天天睡前故事会", "日本民间童话故事—熊猫天天睡前故事会", "寓言故事—熊猫天天睡前故事会", "名人讲故事—熊猫天天睡前故事会", "一千零一夜—熊猫天天睡前故事会", "希腊童话故事—熊猫天天睡前故事会", "伊索寓言—熊猫天天睡前故事会", "王尔德童话系列—熊猫天天睡前故事会", "智慧故事—熊猫天天睡前故事会", "勇敢故事—熊猫天天睡前故事会", "亲情故事—熊猫天天睡前故事会", "谦虚故事—熊猫天天睡前故事会", "乐观故事—熊猫天天睡前故事会", "勤劳故事—熊猫天天睡前故事会", "自信故事—熊猫天天睡前故事会", "爱心故事—熊猫天天睡前故事会", "想象力故事—熊猫天天睡前故事会", "感恩故事—熊猫天天睡前故事会", "坚强故事—熊猫天天睡前故事会", "知足故事—熊猫天天睡前故事会", "机智故事—熊猫天天睡前故事会", "诚信故事—熊猫天天睡前故事会", "分享故事—熊猫天天睡前故事会", "自制力故事—熊猫天天睡前故事会", "责任故事—熊猫天天睡前故事会", "成长故事—熊猫天天睡前故事会", "乐于助人故事—熊猫天天睡前故事会", "原谅故事—熊猫天天睡前故事会", "珍惜故事—熊猫天天睡前故事会", "忠诚故事—熊猫天天睡前故事会", "诚实故事—熊猫天天睡前故事会", "冒险故事—熊猫天天睡前故事会", "坚持故事—熊猫天天睡前故事会", "海的女儿—熊猫天天睡前故事会", "讲给小朋友听的西方世界史—小朋友大百科", "一年级必背古诗文—国学小课堂", "二年级必背古诗文—国学小课堂", "三年级必背古诗文—国学小课堂", "四年级必背古诗文—国学小课堂", "五年级必背古诗文—国学小课堂", "六年级必背古诗文—国学小课堂", "七年级必背古诗文—国学小课堂", "八年级必背古诗文—国学小课堂", "九年级必背古诗文—国学小课堂", "推荐背诵篇目—国学小课堂", "友爱故事—熊猫天天睡前故事会", "耳畔书香");
    private String Appkey = "8Bwa8b3jtFdcGnPf";
    private String Token = "7E6Ed5nWHxny2XnZ";
    @Autowired
    private AttributesController attributesController;
    @Autowired
    private AudioPlayerController audioPlayerController;
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {

        return input.matches(Predicates.intentName("iFengFM"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        PlaybackInfo playbackInfo = attributesController.getPersistentAttribute(input, PLAYBACK_INFO, PlaybackInfo.class);
        Request request = input.getRequestEnvelope().getRequest();
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        String slotinfo = slots.get("SlotName").getValue();
        JSONObject data = JSONObject.parseObject(slotinfo);
        String A1s = data.getString("answer");
        JSONObject parameters = data.getJSONObject("parameters");
        String album = "";
        if (parameters != null) {
            album = parameters.getString("album");
        }
        int albumnum = getAlbumNum(album);
        int pagenum = 1;
        String appid = "TZNXF";
        String sign = com.huolongguo.study.utils.EncryptUtil.string2MD5(
                "appid=" + appid + "&pagenum=" + pagenum + "&pid=" + albumnum + "&token=" + Token + "&" + Appkey
        );
        MultiValueMap<String, Object> parames = new LinkedMultiValueMap<String, Object>();
        parames.add("appid", appid);
        parames.add("token", Token);
        parames.add("sign", sign);
        parames.add("pagenum", String.valueOf(pagenum));
        parames.add("pid", String.valueOf(albumnum));
        //JSONObject newsdata = JSONObject.parseObject(HttpUtil.http("https://www.ifeng.com/fm/read/fmd/api/getResourceByPid_210.html", parames));
        iFengFMResponse newsdata = restTemplate.postForObject("https://www.ifeng.com/fm/read/fmd/api/getResourceByPid_210.html", parames, iFengFMResponse.class);
        if (!newsdata.getCode().equals("0")) {
            //code 不为0 接口出现问题
            return input.getResponseBuilder()
                    .withSpeech("请检查网络连接或新闻接口")
                    .withSimpleCard("凤凰FM", "404ing,请检查网络连接或新闻接口")
                    .build();
        }
        int fmlistsize = newsdata.getData().getList().size();
        for (int i = 0; i < fmlistsize; i++) {
            ListBean singleFMdata = newsdata.getData().getList().get(i);
            if (singleFMdata.getAudiolist().size() == 1) {
                for (int j = 0; j < singleFMdata.getAudiolist().size(); j++) {
                    AudioSource audioSource = new AudioSource();
                    audioSource.setTitle(singleFMdata.getTitle());
                    audioSource.setSubTitle(singleFMdata.getProgramName());
                    audioSource.setUrl(singleFMdata.getAudiolist().get(j).getFilePath());
                    audioSource.setArtImageUrl(singleFMdata.getImg370_370());
                    playbackInfo.getPlayOrder().add(audioSource);
                }
            }
        }
        attributesController.setPersistentAttribute(input, PLAYBACK_INFO, playbackInfo);
        return audioPlayerController.play(input, "", playbackInfo, ClearBehavior.CLEAR_ALL);
    }

    private int getAlbumNum(String album) {
        Map<Integer, String> albums = new HashMap();
        for (int i = 0; (i < numlist.size() && i < namelist.size()); i++) {
            albums.put(numlist.get(i), namelist.get(i));
        }
        if (album == null || album.equals("")) {
            return numlist.get(1 + (int) (Math.random() * (88)));
        } else {
            for (Integer key : albums.keySet()) {
                String value = albums.get(key);
                if (value.equals(album)) {
                    return key;
                }
            }
        }
        return getAlbumNum("");
    }

}
