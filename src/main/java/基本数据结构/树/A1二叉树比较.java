package 基本数据结构.树;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Objects;

/**
 * Created by Administrator on 2019/3/5 0005.
 */
public class A1二叉树比较 {
    /**
     * 先序遍历是最优选择, 一旦这两个node的value不同,直接返回,避免进行更深层次的递归调用
     */
    public static boolean preOrderTraversal(Node tr1, Node tr2) {
        if (tr1 == null && tr2 == null) {//都为空直接返回true
            return true;
        } else if (tr1 == null || tr2 == null) {//一个空一不空直接返回false
            return false;
        }
        if (!Objects.equals(tr1.value, tr2.value)) {// 都不空则比较值
            return false;
        }
        //如果两个值都相等, 则递归比较
        boolean b1 = preOrderTraversal(tr1.left, tr2.left);
        boolean b2 = preOrderTraversal(tr1.right, tr2.right);
        if (b1 == true && b2 == true) {//只有都返回true,才返回true
            return true;
        } else {
            return false;//只要有一个返回false, 就是false,不等的
        }
    }

    public static void main(String[] args) {
        Node tr1 = new Node();
        tr1.value = 0;
        Node tr2 = new Node();
        tr2.value = 0;
        //
        Node n11 = new Node();
        n11.value = 1;
        Node n12 = new Node();
        n12.value = 2;
        Node n21 = new Node();
        n21.value = 1;
        Node n22 = new Node();
        n22.value = 2;

        Node n23 = new Node();
        n23.value = 3;

        tr1.left = n12;
        tr1.right = n12;
        tr2.left = n22;
        tr2.right = n22;
        //tr2.right.left = n23;//加上这个结果为false
        System.out.println(preOrderTraversal(tr1, tr2));
    }
}
