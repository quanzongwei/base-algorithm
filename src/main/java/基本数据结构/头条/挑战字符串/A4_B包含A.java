package 基本数据结构.头条.挑战字符串;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2019/3/6 0006.
 */
public class A4_B包含A {
    //    s1 s2元素个数[1,1000]
    public static boolean checkInclusion(String s1, String s2) {
        // 合法性校验
        if (s1 == null && s1 == null) {
            return true;
        } else if (s1 == null || s2 == null) {
            return false;
        }
        if (s1.length() > s2.length()) {
            return false;
        }

        //
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            for (int j = 0; j < s1.length(); j++) {
                if (!Objects.equals(s1.charAt(j), s2.charAt(j))) {
                    break;
                }
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkInclusion("asd", "asdzxc"));
        Map<String,String> map = new HashMap();
        for (Map.Entry entry : map.entrySet()) {

        }
    }
}
