package com.zhenghuiyan.todaything.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhenghuiyan on 2015/3/4.
 */
public class StringUtil {
    private StringUtil() {}

    /**
     * 检测邮箱地址是否合法
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
