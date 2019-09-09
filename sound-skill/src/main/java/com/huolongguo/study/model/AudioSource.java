package com.huolongguo.study.model;

import com.soundai.skill.utils.EncryptUtil;
import com.soundai.skill.utils.TextUtils;
import lombok.Data;

/**
 * @author Caris W
 */
@Data
public class AudioSource {

    private String title;
    private String subTitle;
    private String artImageUrl;
    private String backgroundImageUrl;
    private String url;
    private String token;

    public void setUrl(String url) {
        this.url = url;
        if (!TextUtils.isEmpty(url)) {
            this.token = EncryptUtil.string2MD5(url);
        }
    }
}
