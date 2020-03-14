package com.qzw.demo.java.stock;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BG388892
 * @date 2020/3/10
 */
public class StockMain {

    public static List<Rule> rulelist = new ArrayList<>();

    static {
        rulelist.add(new Rule("南京银行", 7.86, 1700, 2));
        rulelist.add(new Rule("兴业银行", 17.63, 400, 2));

//        rulelist.add(new Rule("南京银行", 7.80, 1700, 0));
//        rulelist.add(new Rule("兴业银行", 17.5, 400, 0));
    }

    public static void main(String[] args) throws IOException, InterruptedException {


        while (true) {
            try {
                doStockListen();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000 * 10);
        }
    }

    private static void doStockListen() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://hq.sinajs.cn/list=sz002307,sh600928,sz518880");// 注意基金后面是sz和sh是必须要填写的
        HttpGet httpGet = new HttpGet("http://hq.sinajs.cn/list=sh601009,sh601166");// 注意基金后面是sz和sh是必须要填写的
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        String s = null;
        List<String> list = new
                ArrayList<>();
        try {
            HttpEntity entity1;
            entity1 = response1.getEntity();
            InputStream content = entity1.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content, "GBK"));

            while ((s = br.readLine()) != null) {
                list.add(s);
            }
            EntityUtils.consume(entity1);
        } finally {
            s = null;
            response1.close();
        }
        matchRule(list);
    }

    public static void matchRule(List<String> list) {
//        System.out.println(list);
        for (String s : list) {
            String[] split = s.split("=");
            String[] data = split[1].split(",");
            for (Rule rule : rulelist) {
                if (data[0].contains(rule.getName())) {
                    if (Double.valueOf(data[3]).compareTo(rule.getBuyPrice() * (100 + rule.getUpPercent()) / 100) >= 0) {
                        try {
                            MailUtil.sendMessage(rule.getName(), "上涨超过" + rule.getUpPercent() + "% " + "原价:" + rule.getBuyPrice() + " 现价:" + data[3] + " 总股数:" + rule.getTotalAmount());
                        } catch (Exception e) {
                            System.out.println("邮件发送异常");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    //https://www.jianshu.com/p/108b8110a98c
    /*
0：”大秦铁路”，股票名字；
1：”27.55″，今日开盘价；
2：”27.25″，昨日收盘价；
3：”26.91″，当前价格；
4：”27.55″，今日最高价；
5：”26.20″，今日最低价；
6：”26.91″，竞买价，即“买一”报价；
7：”26.92″，竞卖价，即“卖一”报价；
8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
10：”4695″，“买一”申请4695股，即47手；
11：”26.91″，“买一”报价；
12：”57590″，“买二”
13：”26.90″，“买二”
14：”14700″，“买三”
15：”26.89″，“买三”
16：”14300″，“买四”
17：”26.88″，“买四”
18：”15100″，“买五”
19：”26.87″，“买五”
20：”3100″，“卖一”申报3100股，即31手；
21：”26.92″，“卖一”报价
(22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
30：”2008-01-11″，日期；
31：”15:05:32″，时间；



     */
}
