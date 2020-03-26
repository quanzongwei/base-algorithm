package com.qzw.demo.java.stock.train;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author BG388892
 * @date 2020/3/25
 */
@Data
public class StackNode {

    BigDecimal lastBuyPrice;
    BigDecimal expectPositionPrice;
    // 未使用
    BigDecimal buyPrice;

    int amount;


    public StackNode(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
        // 设置总价 10000
        if (GlobalValue.initMoney == null) {
            GlobalValue.initMoney = buyPrice.multiply(BigDecimal.valueOf(100L));
        }
        System.out.println(GlobalValue.initMoney);
        System.out.println(buyPrice);
        //触发一定要
        int i = GlobalValue.initMoney.divide(buyPrice,BigDecimal.ROUND_FLOOR).divide(BigDecimal.valueOf(100L),BigDecimal.ROUND_FLOOR).setScale(0, BigDecimal.ROUND_FLOOR).intValue();
        if (i<=0) {
            amount = 100;
            return;
        }
        amount = i * 100;
    }

    public StackNode(BigDecimal buyPrice, BigDecimal lastLevelBuyPrice) {
        this.buyPrice = buyPrice;
        this.lastBuyPrice = lastLevelBuyPrice;
    }

    public static void main(String[] args) {
        BigDecimal.valueOf(7399D).divide(BigDecimal.valueOf(79D));
//        int i = BigDecimal.valueOf(7399D).divide(BigDecimal.valueOf(79D)).divide(BigDecimal.valueOf(100L)).setScale(0, BigDecimal.ROUND_FLOOR).intValue();

    }

}
