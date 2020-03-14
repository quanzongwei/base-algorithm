package com.qzw.demo.java.stock;

import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
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
 * https://blog.csdn.net/fangquan1980/article/details/80006762
 * <p>
 * http://hc.apache.org/httpcomponents-client-ga/quickstart.html
 *
 * @author BG388892
 * @date 2020/3/10
 */
public class StockTest {



    public static void main(String[] args) throws IOException {


        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://hq.sinajs.cn/list=sz002307,sh600928,sz518880");// 注意基金后面是sz和sh是必须要填写的
        HttpGet httpGet = new HttpGet("http://hq.sinajs.cn/list=sh601009,sh601166");// 注意基金后面是sz和sh是必须要填写的
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1;
            entity1 = response1.getEntity();
            InputStream content = entity1.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content, "GBK"));
            List<String> list = new
                    ArrayList<>();
            String s = null;
            while ((s = br.readLine() )!= null) {
                list.add(s);
            }


            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }
    }










//        HttpPost httpPost = new HttpPost("http://hq.sinajs.cn/list=sh601006");
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "secret"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//        CloseableHttpResponse response2 = httpclient.execute(httpPost);
//
//        try {
//            System.out.println(response2.getStatusLine());
//            HttpEntity entity2 = response2.getEntity();
//            // do something useful with the response body
//            // and ensure it is fully consumed
//            EntityUtils.consume(entity2);
//        } finally {
//            response2.close();
//        }

}
