package com.huolongguo.study.utils;

/**
 * @author Caris W
 */
public class TextUtils {

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() < 1;
    }
}
