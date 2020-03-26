package com.qzw.demo.java.stock.train;

import com.qzw.demo.utils.JacksonUtil;
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
import java.math.BigDecimal;
import java.util.*;

/**
 * //最大站深度
 *
 * todo 连续上涨优化
 *
 * @author BG388892
 * @date 2020/3/25
 */

public class TrainingMain {
    public static void main(String[] args) throws IOException {

        BigDecimal total = GlobalValue.totalGetMoney;

        int earnTimes = 0;
        int buyTimes = 0;
        int maxStack = 0;

        List<DayItem> items = getDayItemOrderByDate();

        LinkedList<StackNode> nodes = new LinkedList<>();

        Iterator<DayItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            DayItem item = iterator.next();
            if (nodes.isEmpty()) {
                nodes.add(new StackNode(item.getHigh()));
                continue;
            }
            StackNode last = nodes.getLast();

            BigDecimal buyPrice = last.getBuyPrice();
            BigDecimal lastBuyPrice = last.getLastBuyPrice();


            BigDecimal high = item.getHigh();
            BigDecimal low = item.getLow();
            BigDecimal up = buyPrice.multiply(BigDecimal.valueOf(1.00D+GlobalValue.earnRate));
            BigDecimal down = buyPrice.multiply(BigDecimal.valueOf(1.00D-GlobalValue.earnRate));

            int amount = last.getAmount();
            if (nodes.size()>maxStack) {
                maxStack = nodes.size();
            }
            // buy in
            if (low.compareTo(down) <= 0) {
                buyTimes++;
                nodes.add(new StackNode(down));
                continue;
            }
            // sale out
            if (high.compareTo(up) > 0) {
                total = total.add(buyPrice.multiply(BigDecimal.valueOf(GlobalValue.earnRate)).multiply(BigDecimal.valueOf(amount)));
                earnTimes++;
                nodes.removeLast();
            }
        }

        System.out.println("总天数:" + items.size());
        System.out.println("总盈利:" + total);
        System.out.println("总购买次数:" + buyTimes);
        System.out.println("总盈利次数:" + earnTimes);
        System.out.println("栈最高级别:" + maxStack);
        System.out.println("栈总数:" + nodes.size());
        for (StackNode node : nodes) {
            System.out.println("  总价:" + node.getBuyPrice().multiply(BigDecimal.valueOf(100L)) + ", 单价:" + node.getBuyPrice() + ", 数量:" + node.getAmount());
        }

    }

    public static List<DayItem> getDayItemOrderByDate() throws IOException {

        //todo: http get 180
        return doStockGet();
    }

    private static List<DayItem> doStockGet() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(GlobalValue.url);// 注意基金后面是sz和sh是必须要填写的
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        String s = null;
        List<String> list = new
                ArrayList<>();
        List<DayItem> itemList = new ArrayList<>();
        try {
            HttpEntity entity1;
            entity1 = response1.getEntity();
            InputStream content = entity1.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content, "GBK"));

            String str = "";
            while ((s = br.readLine()) != null) {
                list.add(s);
                str += s;
            }
            System.out.println(str);

            List<SohuStockDTO> dtoList = JacksonUtil.formListJson(SohuStockDTO.class, str);
            List<List<String>> hq = dtoList.get(0).getHq();
            for (List<String> oneDay : hq) {
                String dateStr = oneDay.get(0);
                String kaipanjia = oneDay.get(1);
                String shoupanjia = oneDay.get(2);
                String xxx = oneDay.get(3);
                String zhangfu = oneDay.get(4);
                String zuidi = oneDay.get(5);
                String zuigao = oneDay.get(6);
                String liang = oneDay.get(7);
                String e = oneDay.get(8);
                String xxx2 = oneDay.get(9);
                itemList.add(new DayItem(new BigDecimal(zuigao), new BigDecimal(zuidi), Integer.valueOf(dateStr.replaceAll("-", ""))));
            }


            EntityUtils.consume(entity1);
        } finally {
            s = null;
            response1.close();
        }

        Collections.reverse(itemList);
        // todo
        return itemList;
    }


}
