package com.qzw.demo.algorithm.基本数据结构.头条.链表和树;

import org.junit.Test;

/**
 * @author BG388892
 * @date 2019/11/26
 */
public class A2_反转链表 {
    @Test
    public void test() {
        ListNode n1 = new ListNode(0);
        ListNode n2 = new ListNode(1);
        ListNode n3 = new ListNode(2);
        ListNode n4 = new ListNode(3);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        //ListNode result = reverseList(n1);
        ListNode result = reverseListUsingRecursion(n1);
        ListNode.printNodeList(result);

    }

    /**
     * 迭代
     */
    public ListNode reverseList(ListNode input) {
        ListNode head = null;
        while (input != null) {
            ListNode tmp = input;
            input = input.next;// 用完马上往后移
            tmp.next = head;
            head = tmp;
        }
        return head;
    }

    /**
     * 递归, 这里优化了一个引用复制的问题
     */
    public ListNode reverseListUsingRecursion(ListNode input) {
        ListNode result = deal(null, input);
        return result;
    }

    /**
     * 递归, 这里优化了一个引用复制的问题
     */
    public ListNode reverseListUsingRecursionBeforeFixed(ListNode input) {
        ListNode result = null;
        //这里复制了result的引用,最终的result还是指向null, 当result不为null, 并在持续修改过程中, 都不为null,name这种传递是可以的
        deal(result, input);
        return result;
    }

    private ListNode deal(ListNode result, ListNode input) {
        if (input == null) {
            return result;
        }
        ListNode tmp = input;
        input = input.next;// 取值之后, 指针立即后移, 避免后续的影响

        tmp.next = result;
        result = tmp;

        return deal(result, input);
    }
}
