package com.qzw.demo.algorithm.基本数据结构.头条.链表和树;

import org.junit.Test;

/**
 * @author BG388892
 * @date 2019/11/26
 */
public class A1_合并两个有序链表 {
    @Test
    public void test() {
        ListNode l1 = new ListNode(1);
        ListNode l11 = new ListNode(2);
        ListNode l12 = new ListNode(4);

        l1.next = l11;
        l11.next = l12;

        ListNode l2 = new ListNode(1);
        ListNode l21 = new ListNode(3);
        ListNode l22 = new ListNode(4);

        l2.next = l21;
        l21.next = l22;
        ListNode listNode = mergeTwoLists(l1, l2);
        System.out.println();
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 使用两个辅助Node, 主要是为了避免null的情况, 最后ln.next.next就拿到了最终的值了
        ListNode ln = new ListNode(0);
        ListNode lnTail = new ListNode(0);
        ln.next = lnTail;
        // 1 都不为null
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                ListNode one = new ListNode(l1.val);
                lnTail.next = one;
                lnTail = one;
                l1 = l1.next;
            } else {
                ListNode one = new ListNode(l2.val);
                lnTail.next = one;
                lnTail = one;
                l2 = l2.next;
            }
        }
        //2 l1不为null
        if (l1 != null && l2 == null) {
            lnTail.next = l1;

        }
        //3 l2不为null
        else if (l1 == null && l2 != null) {
            lnTail.next = l2;
        }

        // 都为null以及执行完2和3步骤后
        return ln.next.next;
    }
}

