package com.qzw.demo.java.stock;

import lombok.Data;

/**
 * @author BG388892
 * @date 2020/3/10
 */
@Data
public class Rule {




    String name;
    double buyPrice;
    int totalAmount;
    double upPercent;

    public Rule(String name, double buyPrice, int totalAmount, double upPercent) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.totalAmount = totalAmount;
        this.upPercent = upPercent;
    }
}
