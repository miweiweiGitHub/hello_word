package com.huolongguo.study.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Caris W
 */
@Data
public class PlaybackInfo {

    private List<AudioSource> playOrder = new ArrayList<>();
    private int index = 0;
    private long offsetInMilliseconds = 0L;
    private String token;
    private boolean nextStreamEnqueued = false;
    private boolean inPlaybackSession = false;
    private boolean hasPreviousPlaybackSession = false;
}
