package apps.commons.util.tool_util;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


/**
 * HTTP模拟请求类(POST)
 *
 * @author yangxianghua, 2018年7月9日 上午10:52:06
 */
public class MockHttpPost {

    /**
     * 模拟发送post请求
     *
     * @param url
     * @param info
     * @author yangxianghua, 2018年7月9日 下午2:19:40
     */
    public static String sendPost(String url, Map<String, String> params, String appKey, String appSecret) {
        String nonce = generateUID();
        String timestamp = String.valueOf(new Date().getTime());
        String signature = Sha1Util.SHA1(appSecret + nonce + timestamp);

        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        BufferedReader in = null;
        String result = "";
        if (!params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        UrlEncodedFormEntity entity = null;
        try {
            // 接收类型，这里是传统的表单模式
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            // 接收类型，这里是JSON模式
            //            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.setHeader("App-Key", appKey);
            httpPost.setHeader("Nonce", nonce);
            httpPost.setHeader("Timestamp", timestamp);
            httpPost.setHeader("Signature", signature);
            httpPost.setHeader("accept", "*/*");
            // 设置编码
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(entity);
            // 发送
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                // 收集
                HttpEntity resEntity = response.getEntity();
                // 处理状态码，仅列出常见的
                if (resEntity != null) {
                    int code = response.getStatusLine().getStatusCode();
                    if (code == 200) {
                        //System.out.println("请求成功！");
                    } else if (code == 404) {
                        System.out.println("找不到页面");
                    } else if (code == 400) {
                        System.out.println("无效的请求");
                    } else {
                        System.out.println("未知错误");
                    }
                }
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                return result;
            } finally {
                if (in != null) {
                    in.close();
                }
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String generateUID() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 8; i++) {
            //首字母不能为0
            result += (random.nextInt(9) + 1);
        }
        return result;
    }
}
