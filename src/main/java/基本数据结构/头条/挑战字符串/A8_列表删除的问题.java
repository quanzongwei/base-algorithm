package 基本数据结构.头条.挑战字符串;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Administrator on 2019/3/7 0007.
 */

public class A8_列表删除的问题 {
    /**
     * 队列头部在左边
     * @param args
     */
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.addFirst("1");
        list.addFirst("2");
        list.addFirst("3");
        System.out.println(list);
        list.addLast(0);
        System.out.println(list);
        list.add(-1);//尾部加入
        System.out.println(list);
        list.remove();//头部删除
        System.out.println(list);
    }
}
