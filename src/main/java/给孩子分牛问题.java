/**
 * Created by Administrator on 2019/3/26 0026.
 */
public class 给孩子分牛问题 {
    /**
     * 是否是正确的数据,默认是合法
     * true: 牛的个数合法
     * false: 牛的个数不合法
     */
    static Boolean isRight = true;

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE / 7; i++) {
            // 重置
            isRight = true;
            function(1, i * 7 + 1, i * 7 + 1);
            if (isRight == true) {
                System.out.println(i * 7 + 1);
            }
        }
    }

    /**
     * @param n      当前是第几个孩子
     * @param sum    当前剩余的牛的总数
     * @param cowSum 牛的总数,整个递归过程中保持不变
     */
    public static void function(int n, int sum, int cowSum) {//引用复制
        if (sum == n) {
            System.out.println("孩子个数:".concat(String.valueOf(n)).concat(", 牛的总数:").concat(String.valueOf(cowSum)));
            return;
        }
        if ((sum - n) % 7 != 0) {//最重要的一个判断
            isRight = false;//只要不满足条件, 就设置为false
            return;
        }
        function(n + 1, (sum - n) / 7 * 6, cowSum);
    }
}
