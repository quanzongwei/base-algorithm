package com.qzw.demo.algorithm.多元线性回归;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2019/3/1 0001.
 */
public class LRM {
    public static List<List<Double>> allData = new ArrayList<List<Double>>();//原始数据
    public static List<Double> y = new ArrayList<Double>();//实际值数组
    public static List<List<Double>> X = new ArrayList<List<Double>>();//输入的行数
    public static List<Double> theta = new ArrayList<Double>();//最终有效的theta//*/
    public static int m;// 原始数据行数
    public static int numRuns = 1000;



    public static void main(String[] args) throws FileNotFoundException {
        try {
            try(BufferedReader br = new BufferedReader(new FileReader("C:/Users/Administrator/Desktop/x17.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    List<String> items = Arrays.asList(line.split("\\s+"));
                    List<Double> intItems = new ArrayList<Double>();
                    List<Double> addToX = new ArrayList<Double>();
                    for (int i = 0; i<items.size(); i++){
                        intItems.add(Double.parseDouble((items.get(i))));
                        if (i < items.size()-1){
                            addToX.add(Double.parseDouble((items.get(i))));
                        }
                    }
                    allData.add(intItems);//this is the list with all ze data
                    X.add(addToX);//this is the X list
                }

            } catch (IOException e) {
                System.out.println("Lool noob theres an error");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        m = X.size();
        for (int i = 0; i<allData.size(); i++){//this is the y list
            y.add(allData.get(i).get((allData.get(0)).size()-1));
        }
        System.out.println(X);

//        List<List<Double>> transposeX = transpose(X);
//        for (int i = 1; i<X.get(0).size(); i++){
//            Double sum = 0.0;
//            for (int h = 0; h<X.size(); h++){
//                //System.out.println(X.get(h).get(i));
//                sum = sum + X.get(h).get(i);
//            }
//            sum = sum/X.size();
//
//            for (int j = 0; j<X.size(); j++){
//                Double temp = X.get(j).get(i);
//                Double result = (temp - sum);
//                Double collection = (Collections.max(transposeX.get(i))-Collections.min(transposeX.get(i)));
//                Double value = result/collection;
//                X.get(j).set(i, value);
//            }
//        }
        System.out.println(X);
        for (int i = 0; i<X.get(0).size(); i++){
            theta.add(0.0);
        }
        double currCost = costFunction(theta, X, y);
        double newCost = 0.0;
        List<Double> besttheta = theta;
        List<Double> newtheta = theta;
        for (int i = 0; i<numRuns; i++){
            newtheta = GradientDescent(newtheta, X, y);
            newCost = costFunction(newtheta, X, y);
            if (newCost < currCost){
                currCost = newCost;
                besttheta = newtheta;
            }
        }
        System.out.println(besttheta);
        System.out.println(Hypothesis(besttheta, X.get(2)));

    }
    public static List<List<Double>> transpose(List<List<Double>> matrixIn) {
        List<List<Double>> matrixOut = new ArrayList<List<Double>>();
        if (!matrixIn.isEmpty()) {
            int noOfElementsInList = matrixIn.get(0).size();
            for (int i = 0; i < noOfElementsInList; i++) {
                List<Double> col = new ArrayList<Double>();
                for (List<Double> row : matrixIn) {
                    col.add(row.get(i));
                }
                matrixOut.add(col);
            }
        }
        return matrixOut;
    }

    //求预测值
    static double Hypothesis(List<Double> theta, List<Double> X){
        Double sum1 = 0.0;
        for (int i =0; i<theta.size(); i++){
            sum1 = sum1 + (theta.get(i)*X.get(i));
        }
        return sum1;
    }

    //获取均方误差
    static double costFunction(List<Double> theta, List<List<Double>> X, List<Double> y){
        Double sum1 = 0.0;
        for (int i = 0; i<m; i++){
            sum1 = sum1 + (Math.pow(((Hypothesis(theta, X.get(i))) - y.get(i)), 2.0));
        }
        Double result = ((1.0/(2.0 * m))*sum1);
        return result;
    }

    // 梯度下降求theta值
    static List<Double> GradientDescent(List<Double> theta, List<List<Double>> X, List<Double> y){
        double sum1 = 0.0;
        double alpha = 0.001;
        for (int j = 0; j<theta.size(); j++){
            for (int i = 0; i<m; i++){
                sum1 = sum1 + (((Hypothesis(theta, X.get(i))) - y.get(i)) * X.get(i).get(j));
            }
            //((Hypothesis(theta, X.get(i))) - y.get(i)) * X.get(i).get(j)? 这个为什么是斜率
            double temp = ((1.0/m) * sum1 *  alpha);//这个斜率平均斜率
            theta.set(j, (theta.get(j)-temp));
        }
        return theta;
    }
}
