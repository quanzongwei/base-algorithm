package com.qzw.demo.java.stock.train;

import lombok.Data;

import java.util.List;

/**
 * @author BG388892
 * @date 2020/3/25
 */

@Data
public class SohuStockDTO {
    Integer status;
    String code;
    List<List<String>> hq;
}
