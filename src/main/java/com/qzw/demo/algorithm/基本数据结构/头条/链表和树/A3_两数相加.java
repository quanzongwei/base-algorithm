package com.qzw.demo.algorithm.基本数据结构.头条.链表和树;

import org.junit.Test;

/**
 * @author BG388892
 * @date 2019/11/28
 */
public class A3_两数相加 {
    @Test
    public void test() {
        ListNode n1 = new ListNode(2);
        ListNode n2 = new ListNode(4);
        ListNode n3 = new ListNode(3);

        ListNode n4 = new ListNode(5);
        ListNode n5 = new ListNode(6);
        ListNode n6 = new ListNode(4);

        n1.next = n2;
        n2.next = n3;

        n4.next = n5;
        n5.next = n6;

        ListNode result = addTwoNumbers(n1, n4);
        ListNode.printNodeList(result);

    }

    @Test
    public void test2() {
        ListNode n1 = new ListNode(1);

        ListNode n4 = new ListNode(9);
        ListNode n5 = new ListNode(9);

        n4.next = n5;

        ListNode result = addTwoNumbers(n1, n4);
        ListNode.printNodeList(result);

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode rst = null;
        ListNode tail = null;
        int shang = 0;

        // l1不为空, l2不为空
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + shang;
            shang = sum / 10;
            int yu = sum % 10;
            if (rst == null) {
                rst = new ListNode(yu);
                tail = rst;
            } else {
                ListNode node = new ListNode(yu);
                tail.next = node;
                tail = node;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        //l1不为空 l2为空
        while (l1 != null && l2 == null) {
            int sum = l1.val + shang;
            shang = sum / 10;
            int yu = sum % 10;
            if (rst == null) {
                rst = new ListNode(yu);
                tail = rst;
            } else {
                ListNode node = new ListNode(yu);
                tail.next = node;
                tail = node;
            }
            l1 = l1.next;
        }
        // l1为空 l2不为空
        while (l1 == null && l2 != null) {
            int sum = l2.val + shang;
            shang = sum / 10;
            int yu = sum % 10;
            if (rst == null) {
                rst = new ListNode(yu);
                tail = rst;
            } else {
                ListNode node = new ListNode(yu);
                tail.next = node;
                tail = node;
            }
            l2 = l2.next;
        }

        //都为空
        if (shang > 0) {
            tail.next = new ListNode(shang);
        }

        return rst;
    }

    /**
     * 迭代, 本题不需要反转链表
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
}
