package com.qzw.demo.algorithm.多元线性回归;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 蚂蚁专家出的题目
 * <p/>
 * 参考代码:https://www.cnblogs.com/wzm-xu/p/4062266.html
 * 参考代码:https://github.com/sagar448/LinearRegressionAndMultivariate/blob/master/LinearRegression.java
 * 参考公式:https://www.youtube.com/watch?v=W8UvAwb9gvA
 * Created by quanzongwei on 2019/3/1 0001.
 */
public class LinearRegressionV2 {
   /*
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

    输入的注意最后一行有一个空格,最为结束标志

   输出预测值:
    44436.83695067001
    53016.65456508036
    50914.28027895309
    52618.211249970656
    */

    private double learningRate = 0.001;
    private int times = 0;
    //总的变量的数量, 和theta的数量够保持一致
    private int totalFeature = 0;
    //样本变量数组
    private double[][] rows;
    //样本值
    private double[] y;

    //输入原文
    private List<String> originLines = new ArrayList<>();

    //待预测的数据
    private double[][] predictRows;
    // theta
    private double[] theta;

    /**
     * @param learningRate
     * @param times
     */
    public LinearRegressionV2(double learningRate, int times) {
        this.learningRate = learningRate;
        this.times = times;
        //从标准输入中获取输入值, 并进行初始化工作
        init();
    }

    public void init() {
        Scanner bf = new Scanner(new InputStreamReader(System.in));
        String line;
        try {
            while ((line = bf.nextLine()) != null) {
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
        System.out.println("theta");
        for (int i = 0; i < theta.length; i++) {
            System.out.print(theta[i]);
            System.out.println(" ");
        }
        System.out.println();
        double value = 0.0;
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < theta.length; j++) {
                value = value + rows[i][j] * theta[j];
                System.out.print(rows[i][j]);
                System.out.print(" ");
            }
            System.out.print(" ");
            System.out.print(value);
            System.out.println();
            value = 0.0;
        }
        //预测结果
        System.out.println("预测结果");
        if (predictRows != null) {
            for (int i = 0; i < predictRows.length; i++) {
                for (int j = 0; j < theta.length; j++) {
                    value = value + predictRows[i][j] * theta[j];
                    System.out.print(predictRows[i][j]);
                    System.out.print(" ");
                }
                System.out.print(" ");
                System.out.print(value);
                System.out.println();
                value = 0.0;
            }
        }
    }


    /**
     * 打印原始输入值
     */
    public void printInput() {
        for (String line : originLines) {
            System.out.println(line);
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
        LinearRegressionV2 lr = new LinearRegressionV2(0.001, 10000);
        lr.printInput();
        lr.train();
        lr.predict();
    }

}
