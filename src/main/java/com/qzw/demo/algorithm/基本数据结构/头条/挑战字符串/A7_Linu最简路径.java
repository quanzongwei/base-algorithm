package com.qzw.demo.algorithm.基本数据结构.头条.挑战字符串;

import java.util.*;
import java.util.concurrent.DelayQueue;

/**
 * 栈和对列的结合使用
 *
 * last -> first
 * 队列从last进,first出来
 * 栈从fist进first出
 * <p/>
 * DEQUE包含了栈结构
 * queue包含了队列结构
 */
public class A7_Linu最简路径 {
    public static final String DOT = ".";
    public static final String DOTDOT = "src/main";
    public static final String FS = "/";//forward slash, back slash

    public static String simplifyPath(String path) {
        LinkedList<String> queue = new LinkedList<>();
        path = path.replaceAll("/+", "/");
        String[] split = path.split("/");
        for (String one : split) {
            if (one.equals(DOT) || one.equals("/") || one.equals("")) {

            } else if (one.equals(DOTDOT)) {
                if (!queue.isEmpty()) {//注意判空,否则会报错
                    queue.pop();
                }
            } else {
                queue.push(one);
            }
        }
        StringBuilder builder = new StringBuilder("/");
        while (!queue.isEmpty()) {
            builder.append(queue.removeLast());
            builder.append("/");
        }
        String str = builder.substring(0, builder.length() - 1);
        if (str.equals("")) {//处理只有一个斜杠的情况,严禁使用str==""
            str = "/";
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(simplifyPath("/home/./asd/"));
        //最终是system
        //origin,len,可能会返回空,len>origin.len的话
        //Arrays.copyOf();
        //src,srcposition,dest,destPosition,len,严格获取,不容错
        //System.arraycopy();
        // ;
        //解析结果是""和"home"这怎么解释,哪里来的空串,要特别注意
        //逗号也是这样的,可能只适合a,b这种字符串吧-
        String[] split = ",home,".split(",");
        System.out.println();
    }
}
