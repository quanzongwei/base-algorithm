package com.qzw.demo.java.stock.train;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author BG388892
 * @date 2020/3/25
 */
@Data
public class DayItem {




    public DayItem(BigDecimal high, BigDecimal low, int date) {
        this.high = high;
        this.low = low;
        this.date = date;
    }

    BigDecimal high;
    BigDecimal low;
    int date;
}
