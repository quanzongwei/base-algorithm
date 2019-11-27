package com.qzw.demo.algorithm.基本数据结构.头条.链表和树;

/**
 * @author BG388892
 * @date 2019/11/26
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }


    public static void printNodeList(ListNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val);
        System.out.print(" ");
        printNodeList(node.next);
    }
}
