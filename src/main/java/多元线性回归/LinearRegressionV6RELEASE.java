package 多元线性回归;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 多元线性回归预测房价, 要看文件输入版本的看前面
 * <p/>
 * 参考代码:https://www.cnblogs.com/wzm-xu/p/4062266.html
 * 参考代码:https://github.com/sagar448/LinearRegressionAndMultivariate/blob/master/LinearRegression.java
 * 数学原理:https://blog.csdn.net/qq_27008079/article/details/70527140
 * 参考公式:https://www.youtube.com/watch?v=W8UvAwb9gvA
 * 理解线性回归概念:https://zhuanlan.zhihu.com/p/45063103
 * Created by quanzongwei on 2019/3/1 0001.
 */
public class LinearRegressionV6RELEASE {
   /*=======================================================================================
   该类支持多元线性回归预测,以下为二元线性回归预测的输入和输出

   输入:
    2 7
    0.18 0.89 41000
    1.0 0.26 62000
    0.92 0.11 53000
    0.07 0.37 29500
    0.85 0.16 55600
    0.99 0.41 65000
    0.87 0.47 60400
    4
    0.49 0.18
    0.57 0.83
    0.56 0.64
    0.76 0.18

    *输入的注意最后一行有一个空格,最为结束标志

   输出预测值:
    44436.83695067001
    53016.65456508036
    50914.28027895309
    52618.211249970656

    对关键点的简要说明:
    1. 对于二元函数y=θ0*x0 +θ1*x1+θ2*x2, 所以在输入的时候,在第一列加上全为1的值,
       于是θ0*x0恒等于θ0,这就成了一个标准的二元线性函数
    2. 对于梯度递减, 理论依据参考: https://blog.csdn.net/qq_27008079/article/details/70527140
       重点关注cost function和Gradient descent algorithm
       对应到计算机代码中就是θ = θ-learningRate*(y拟合-y样本)*xi, 注意有一个求平均操作
    =======================================================================================*/
    /**
     * 学习率(工程上是大于0的)
     */
    private double learningRate = DEFAULT_LEARNING_RATE;
    /**
     * 迭代次数
     */
    private int times = DEFAULT_TIMES;
    /**
     * 保存原始输入行
     */
    private List<String> originLines = new ArrayList<>();
    /**
     * 总的变量的数量, 和theta的数量够保持一致
     */
    private int totalFeature = 0;
    /**
     * theta, 其长度等于totalFeature
     */
    private double[] theta;
    /**
     * 样本变量数组, 一维保存的是行索引
     */
    private double[][] rows;
    /**
     * 样本值数组
     */
    private double[] y;
    /**
     * 待预测的数据,一维保存的是行索引
     */
    private double[][] predictRows;

    public static final double DEFAULT_LEARNING_RATE = 0.001;
    public static final int DEFAULT_TIMES = 100000;

    /**
     * @param learningRate 学习率(工程上是大于0的)
     * @param times        迭代次数
     */
    public LinearRegressionV6RELEASE(double learningRate, int times) {
        this.learningRate = learningRate > 0.0 ? learningRate : DEFAULT_LEARNING_RATE;
        this.times = times > 0 ? times : DEFAULT_TIMES;
        //从标准输入中获取输入值, 并进行初始化工作
        init();
    }

    /**
     * 数据初始化
     */
    public void init() {
        Scanner bf = new Scanner(new InputStreamReader(System.in));
        String line;
        try {
            while ((line = bf.nextLine()) != null) {
                //批量行输入,以空格作为结束标志
                if (line.trim().equals("")) {
                    break;
                }
                originLines.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //第一行数据
        String line0 = originLines.get(0);
        String[] split = line0.split(" ");
        //设置特征的数量
        totalFeature = Integer.parseInt(split[0]) + 1;
        //设置总行数
        int totalRow = Integer.parseInt(split[1]);
        //构造样本变量数组
        rows = new double[totalRow][totalFeature];
        //构造y值数组
        y = new double[totalRow];
        //参数初始化
        theta = new double[totalFeature];
        for (int i = 0; i < theta.length; i++) {
            theta[i] = 1.0;
        }
        //第一行到1+totalTow为样本数据
        int lineIndex = 1;
        while (lineIndex < totalRow + 1) {
            String s = originLines.get(lineIndex);
            String[] sp = s.split(" ");
            rows[lineIndex - 1][0] = 1.0;//第一列设置为1.0, 对应theta0
            for (int i = 0; i < sp.length; i++) {
                if (i == sp.length - 1) {
                    //设置每行的y值
                    y[lineIndex - 1] = Double.parseDouble(sp[i]);
                    break;
                }
                //设置每行的变量
                rows[lineIndex - 1][i + 1] = Double.parseDouble(sp[i]);
            }
            lineIndex++;
        }
        //待预测数据处理
        predictRows = new double[Integer.parseInt(originLines.get(lineIndex))][totalFeature];
        lineIndex++;
        int tmp = lineIndex;
        while (lineIndex < originLines.size()) {
            String s = originLines.get(lineIndex);
            String[] sp = s.split(" ");
            predictRows[lineIndex - tmp][0] = 1.0;//第一列设置为1.0, 对应theta0
            for (int i = 0; i < sp.length; i++) {
                predictRows[lineIndex - tmp][i + 1] = Double.parseDouble(sp[i]);
            }
            lineIndex++;
        }
    }

    /**
     * 数据训练
     */
    public void train() {
        while (times-- != 0) {
            gradientDecent();
        }
    }

    /**
     * 数据预测
     */
    public void predict() {
        //预测结果
        double value = 0.0;
        if (predictRows != null) {
            for (int i = 0; i < predictRows.length; i++) {
                for (int j = 0; j < theta.length; j++) {
                    value = value + predictRows[i][j] * theta[j];
                }
                System.out.println(value);
                value = 0.0;
            }
        }
    }

    /**
     * 梯度递减算法
     */
    private void gradientDecent() {
        for (int i = 0; i < theta.length; i++) {
            double sum = 0.0;
            for (int j = 0; j < rows.length; j++) {
                sum = sum + (hypothesis(j) - y[j]) * rows[j][i];
            }
            theta[i] = theta[i] - learningRate * sum / rows.length;
        }
    }

    /**
     * 预测值(拟合值)
     *
     * @return
     */
    private double hypothesis(int rowIndex) {
        double value = 0.0;
        for (int i = 0; i < theta.length; i++) {
            value = value + theta[i] * rows[rowIndex][i];
        }
        return value;
    }

    public static void main(String[] args) {
        LinearRegressionV6RELEASE lr = new LinearRegressionV6RELEASE(0.001, 10000);
        lr.train();
        lr.predict();
    }
}
