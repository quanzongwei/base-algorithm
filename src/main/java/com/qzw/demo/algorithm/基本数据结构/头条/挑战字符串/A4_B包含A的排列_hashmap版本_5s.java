package com.qzw.demo.algorithm.基本数据结构.头条.挑战字符串;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. 使用hashmap保存s1, key为元素值, value为元素的个数
 * 2. 对于每个起始位置, 对于hashMap进行处理,remove或者减一
 * 3. 根据hashMap的size是否为0,判断是否完全匹配
 */
public class A4_B包含A的排列_hashmap版本_5s {
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

        HashMap<Character, Integer> map = new HashMap();
        for (int i = 0; i < s1.length(); i++) {
            Integer amount = map.get(s1.charAt(i));
            if (amount == null) {
                map.put(s1.charAt(i), 1);
            } else {
                map.put(s1.charAt(i), ++amount);
            }
        }
        //
        for (int i = 0; i < s2.length(); i++) {//
            Map<Character, Integer> mapCopy = deepCopy(map, HashMap.class);
            for (int j = i; j < s2.length(); j++) {
                //此时肯定不匹配
                if (mapCopy.size() != 0 && mapCopy.get(s2.charAt(j)) == null) {
                    break;
                }
                //map数据处理
                Integer amount = mapCopy.get(s2.charAt(j));
                if (amount == 1) {
                    mapCopy.remove(s2.charAt(j));
                } else {
                    mapCopy.put(s2.charAt(j), --amount);// 数量减一
                }
                //判断空
                if (mapCopy.size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 深复制
     */
    public static <T> T deepCopy(T instance, Class<T> clazz) {
        T instanceCopy = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(instance);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            instanceCopy = (T) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return instanceCopy;
    }


    public static void main(String[] args) {
        System.out.println(checkInclusion("ab", "eidbaooo"));
    }
}
