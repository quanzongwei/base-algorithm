package com.qzw.demo.java;

import com.qzw.demo.algorithm.基本数据结构.头条.链表和树.ListNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author BG388892
 * @date 2019/11/27
 */
public class 引用复制传递 {
    @Test
    public void testReference() {
        ListNode node = new ListNode(0);

        function(node);
        // 引用传递对应的是两个引用, 所以这里的node的引用不会在foo中被重新指向新的内存区域
        // 如果想达到这种效果, 可以加一个容器, 然后修改容器中的值
        // 值传递对应的是两个值
        Assert.assertEquals(node.val, 0);
    }

    private void function(ListNode node) {
        node = new ListNode(1);
        System.out.println();
    }
}
