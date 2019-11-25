package com.qzw.demo.algorithm.多元线性回归;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 蚂蚁专家出的题目
 * <p/>
 * 原文:https://www.cnblogs.com/wzm-xu/p/4062266.html
 * 公式重要:https://www.youtube.com/watch?v=W8UvAwb9gvA
 * Created by Administrator on 2019/3/1 0001.
 */
public class LinearRegression {
   /*
   精简版:体现核心思想, 约定输入数据没有问题
   输入(默认测试二元线性回归,第一列默认都是1,theta0*1=theta0):
   x1 x2 x3 y
   1  2  10  110
   1 1.2 13  130
   ....


    */

    private double learningRate = 0.001;
    private int times = 0;
    private int totalRow = 0;
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
     * 数据初始化
     *
     * @param filename     样本文件名称
     * @param learningRate 学习率
     * @param times        迭代次数, 必须大于0
     */
    public LinearRegression(String filename, double learningRate, int times) {
        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在");
        }
        this.learningRate = learningRate;
        this.times = times;
        // 数据初始化
        try {
            String line;
            while ((line = bf.readLine()) != null) {
                originLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //totalFeature
        String s = originLines.get(0);
        String[] split = s.split(" ");
        totalFeature = split.length - 1;
        //rows
        rows = new double[originLines.size()][totalFeature];
        //y
        y = new double[originLines.size()];
        //构造feature和y
        for (int i = 0; i < originLines.size(); i++) {
            String[] sp = originLines.get(i).split(" ");
            for (int j = 0; j < sp.length - 1; j++) {
                rows[i][j] = Double.parseDouble(sp[j]);
            }
            y[i] = Double.parseDouble(sp[sp.length - 1]);
        }
        //初始化theta值
        theta = new double[totalFeature];
        for (int i = 0; i < theta.length; i++) {
            theta[i] = 1.0;
        }
    }

    public LinearRegression(double learningRate, int times) {
        this.learningRate = learningRate;
        this.times = times;
        Scanner bf = new Scanner(new InputStreamReader(System.in));
        String line = null;
        try {

            while ((line = bf.nextLine())!=null) {
                if (line.trim().equals("")) {
                    break;
                }
                originLines.add(line);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String line0 = originLines.get(0);
        String[] split = line0.split(" ");
        totalFeature = Integer.parseInt(split[0]) + 1;
        int totalRow = Integer.parseInt(split[1]);

        rows = new double[totalRow][totalFeature];
        y = new double[totalRow];
        theta = new double[totalFeature];
        for (int i = 0; i < theta.length; i++) {
            theta[i] = 1.0;
        }

        int lineIndex = 1;
        while (lineIndex < totalRow + 1) {
            String s = originLines.get(lineIndex);
            String[] sp = s.split(" ");
            rows[lineIndex - 1][0] = 1.0;//第一列设置为1.0
            for (int i = 0; i < sp.length; i++) {
                if (i == sp.length - 1) {
                    y[lineIndex - 1] = Double.parseDouble(sp[i]);
                    break;
                }
                rows[lineIndex - 1][i + 1] = Double.parseDouble(sp[i]);
            }
            lineIndex++;
        }
        //
        predictRows = new double[Integer.parseInt(originLines.get(lineIndex))][totalFeature];
        // 待预测数据处理
        lineIndex++;
        int tmp = lineIndex;
        while (lineIndex < originLines.size()) {
            String s = originLines.get(lineIndex);
            String[] sp = s.split(" ");
            predictRows[lineIndex-tmp][0] = 1.0;//第一列设置为1.0
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
     * 预测值
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
//        多元线性回归.LRMA lr = new 多元线性回归.LRMA("C:/Users/Administrator/Desktop/n2.txt", 0.001, 10000);
        LinearRegression lr = new LinearRegression(0.001, 10000);
        lr.printInput();
        lr.train();
        lr.predict();
    }

}
