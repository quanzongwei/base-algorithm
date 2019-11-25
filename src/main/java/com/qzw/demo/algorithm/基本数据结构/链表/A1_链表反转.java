package com.qzw.demo.algorithm.基本数据结构.链表;

import java.util.ArrayDeque;

/**
 * 链表反转一共四种解法
 *
 * 1. while递归,反转链表后输出
 * 2. recursion递归, 反转链表后输出, 这里需要用静态类变量
 * 3. 使用递归输出, 类似回溯法和后序遍历
 * 4. 使用辅助栈构造新的列表
 */
public class A1_链表反转 {
    /**
     * node结构
     */
    public static class Node {
        public int value;
        public Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }


    /**
     * 递归反转链表,while模式
     * 图像描述,建模思想
     * while类型的递归, 没有新生成的复杂的状态, 那么递归可以用while来表示
     */
    public static Node reverseLinkedListUseWhile(Node head) {
        Node p = head;//这个p就是未来的尾结点
        Node q;//p的下一个,一般会移到头部

        while (p.next != null) {//p下个节点不为空
            q = p.next;//q是p的下个节点
            p.next = q.next;//p指向下下个节点,其中下个节点(q)不为null
            q.next = head;//p的下个节点(q)指向头部节点
            head = q;//头部节点设置为q
        }
        return head;
    }


    /**
     * 递归反转链表,recursion模式
     * 需要改变引用的递归是不行的, 除非设置全局的变量
     */
    static Node headGlobal;
    static Node pGlobal;

    public static void reverseLinkedListUseRecursion() {
        if (pGlobal.next != null) {
            Node q;
            q = pGlobal.next;//q是必须的,swap必须要一个暂时数据
            pGlobal.next = q.next;
            q.next = headGlobal;
            headGlobal = q;
            reverseLinkedListUseRecursion();
        }
        return;//强调一下
    }


    /**
     * 递归输出模式, 空间复杂度没有减少
     */
    public static void printLinkedListNodeUseRecursion(Node head) {
        if (head == null) {
            return;
        }
        if (head.next != null) {
            printLinkedListNodeUseRecursion(head.next);
        }
        System.out.print(head.value + " ");
    }

    /**
     * 最简单的使用栈结构
     */
    public static Node getNewNodeUseStack(Node head) {
        if (head == null) {
            return null;
        }
        ArrayDeque<Node> deque = new ArrayDeque();
        while (head != null) {
            Node node = new Node(0, null);
            node.value = head.value;
            deque.push(node);
            head = head.next;
        }
        Node newHead = null;
        Node p = null;//最后一个节点
        while (!deque.isEmpty()) {
            Node node = deque.pop();
            if (newHead == null) {
                newHead = node;
                p = node;
            } else {
                p.next = node;
                p = p.next;
            }
        }
        return newHead;
    }

    public static void main(String[] args) {
        Node head = generateNodes();
        printNode(head);
        printNode(head);//验证引用传递的复制问题
        //很典型的引用传递问题, 传递的是引用的副本, 但是c语言中的指针不是副本,但是map的put操作是没问题的
        Node newHead = reverseLinkedListUseWhile(head);
        //递归的实现
        headGlobal = generateNodes();
        pGlobal = headGlobal;
        reverseLinkedListUseRecursion();
        printNode(newHead);
        printNode(headGlobal);

        printNode(head);//此时原来的头指针已经变到尾部了
        //
        System.out.println("递归输出节点列表,类似后序遍历,类似回溯法,都有点像哦");
        printLinkedListNodeUseRecursion(generateNodes());
        System.out.println();
        //
        System.out.println("使用栈结构构造反转列表");
        Node newNodeUseStack = getNewNodeUseStack(generateNodes());
        printNode(newNodeUseStack);

    }

    private static void printNode(Node head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    private static Node generateNodes() {
        Node n5 = new Node(5, null);
        Node n4 = new Node(4, n5);
        Node n3 = new Node(3, n4);
        Node n2 = new Node(2, n3);
        Node n1 = new Node(1, n2);
        return n1;//51 2 3 4 5为原链表的顺序
    }
}
