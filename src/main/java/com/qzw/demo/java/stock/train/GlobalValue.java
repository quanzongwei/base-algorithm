package com.qzw.demo.java.stock.train;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author BG388892
 * @date 2020/3/25
 */
@Data
public class GlobalValue {
    public static BigDecimal initMoney;
    public static BigDecimal totalGetMoney = BigDecimal.valueOf(0.00D);

    public static double earnRate = 0.1;
    //平安
//    public static String url = "http://q.stock.sohu.com/hisHq?code=cn_601318&start=20170324&end=20200325";

    public static String url = "http://q.stock.sohu.com/hisHq?code=cn_000100&start=20170324&end=20200325";


}
