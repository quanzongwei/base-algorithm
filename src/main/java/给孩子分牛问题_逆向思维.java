/**
 * Created by Administrator on 2019/3/26 0026.
 */
public class 给孩子分牛问题_逆向思维 {
    /**
     * 是否是正确的数据,默认是合法
     * true: 牛的个数合法
     * false: 牛的个数不合法
     */
    static Boolean isRight = true;

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE / 6; i++) {
            // 重置
            isRight = true;
            function(i, 0, i);
        }
    }
    /**
     * @param n        当前是第几个孩子
     * @param sum      下一个孩子中牛的总数(和正向思维的区别,这个参数的区别很关键)
     * @param childSum 孩子的总数, 递归调用不改变这个值,,主要用于输出
     */
    public static void function(int n, int sum, int childSum) {//引用复制
        if ((sum) % 6 != 0) {
            isRight = false;
            return;
        }
        if (n == 1) {
            System.out.println("孩子数: ".concat(String.valueOf(childSum)).concat(", 牛数: ".concat(String.valueOf(sum / 6 * 7 + 1))));
            return;
        }
        if (sum != 0) {
            sum = n + sum / 6 * 7;
        } else {
            sum = n;
        }
        function(n - 1, sum, childSum);
    }
}
