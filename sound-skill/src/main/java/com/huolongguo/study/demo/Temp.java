package com.huolongguo.study.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lw on 2019/9/5 11:19
 */
public class Temp {


    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) -8);//当前时间的前一小时举例
        //   "yyyy-MM-dd'T'HH:mm:ss'Z'"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" );

        String end=dateFormat.format(calendar.getTime());//将本地日期格式化为UTC格式的 日期字符串

        System.out.println(end);
        //2019-09-23T03:32:03.684Z

    }
}
