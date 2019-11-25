package com.qzw.demo.algorithm.多元线性回归;


public class LRMB {

    public static void main(String[] args) {
        double[] results = new double[]{41000,62000,53000,29500,55600,65000,60400};
        double[][] features = new double[7][2];
        features[0][0]=0.18;
        features[0][1]=0.89;
        features[1][0]=1.0;
        features[1][1]=0.26;
        features[2][0]=0.92;
        features[2][1]=0.11;
        features[3][0]=0.07;
        features[3][1]=0.37;
        features[4][0]=0.85;
        features[4][1]=0.16;
        features[5][0]=0.99;
        features[5][1]=0.41;
        features[6][0]=0.87;
        features[6][1]=0.41;

        double[] parameters = new double[]{1.0, 1.0, 1.0};
        double learningRate = 0.01;
        SGD(features, results, learningRate, parameters);
        for (double parameter : parameters) {
            System.out.println(parameter);
        }
        double[][] input = new double[4][2];
        input[0][0] =0.49;
        input[0][1] =0.18;
        input[1][0] =0.57;
        input[1][1] =0.83;
        input[2][0] =0.56;
        input[2][1] =0.64;
//        input[3][0] =0.76;
//        input[3][1] =0.18;
        input[3][0] =0.87;
        input[3][1] =0.41;
        for (double[] doubles : input) {
            System.out.println(doubles[0] * parameters[0] + doubles[1] * parameters[1] + parameters[2]);
        }
        for (int i = 0; i < input.length; i++) {
            System.out.println(input[i][0] * parameters[0] + input[i][1] * parameters[1] + parameters[2]);

        }

    }

    private static void SGD(double[][] features, double[] results, double learningRate, double[] parameters) {
        for (int j = 0; j < results.length; j++) {
            double gradient = (parameters[0] * features[j][0] + parameters[1] * features[j][1] + parameters[2] - results[j]) * features[j][0];
            parameters[0] = parameters[0] - 2 * learningRate * gradient;

            gradient = (parameters[0] * features[j][0] + parameters[1] * features[j][1] + parameters[2] - results[j]) * features[j][1];
            parameters[1] = parameters[1] - 2 * learningRate * gradient;


            gradient = (parameters[0] * features[j][0] + parameters[1] * features[j][1] + parameters[2] - results[j]);
            parameters[2] = parameters[2] - 2 * learningRate * gradient;
        }
    }
    private static void BGD(double[][] features, double[] results, double learningRate, double[] parameters) {
        double sum = 0;
        for (int j = 0; j < results.length; j++) {
            sum = sum + (parameters[0] * features[j][0] + parameters[1] * features[j][1]
                     + parameters[2] - results[j]) * features[j][0];
        }
        double updateValue = 2 * learningRate * sum / results.length;
        parameters[0] = parameters[0] - updateValue;

        sum = 0;
        for (int j = 0; j < results.length; j++) {
            sum = sum + (parameters[0] * features[j][0] + parameters[1] * features[j][1]
                     + parameters[2] - results[j]) * features[j][1];
        }
        updateValue = 2 * learningRate * sum / results.length;
        parameters[1] = parameters[1] - updateValue;

        sum = 0;
        for (int j = 0; j < results.length; j++) {
            sum = sum + (parameters[0] * features[j][0] + parameters[1] * features[j][1]
                     + parameters[2] - results[j]);
        }
        updateValue = 2 * learningRate * sum / results.length;
        parameters[2] = parameters[2] - updateValue;
    }

}
