package com.qzw.demo.algorithm.多元线性回归;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 蚂蚁专家出的题目
 * <p/>
 * 原文:https://www.cnblogs.com/wzm-xu/p/4062266.html
 * 公式重要:https://www.youtube.com/watch?v=W8UvAwb9gvA
 * Created by Administrator on 2019/3/1 0001.
 */
public class LRMN1ReleaseProblemMyVersion {
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
    //特征值(变量)数组
    private double[][] rows;
    //样本值
    private double[] y;

    //输入原文
    private List<String> lines = new ArrayList<>();
    // theta
    private double[] theta;


    /**
     * 数据初始化
     *
     * @param filename     样本文件名称
     * @param learningRate 学习率
     * @param times        迭代次数, 必须大于0
     */
    public LRMN1ReleaseProblemMyVersion(String filename, double learningRate, int times) {
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
            while ((line = bf.readLine() )!= null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //totalFeature
        String s = lines.get(0);
        String[] split = s.split(" ");
        totalFeature = split.length-1;
        //rows
        rows = new double[lines.size()][totalFeature];
        //y
        y = new double[lines.size()];
        //构造feature和y
        for (int i = 0; i < lines.size(); i++) {
            String[] sp = lines.get(i).split(" ");
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
        double value =0.0;
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
    }


    public void printInput() {
        for (String line : lines) {
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

        LRMN1ReleaseProblemMyVersion lr = new LRMN1ReleaseProblemMyVersion("C:/Users/Administrator/Desktop/n2.txt",0.001,10000);
        lr.printInput();
        lr.train();
        lr.predict();
    }

}
