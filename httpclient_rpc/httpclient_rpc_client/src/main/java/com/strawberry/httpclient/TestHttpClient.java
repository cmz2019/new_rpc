package com.strawberry.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TestHttpClient {
    public static void main(String[] args) throws Exception {
//        testGetNoParams();
//        testGetParams();
        testPost();
    }

    /**
     * 无参数GET请求
     * 使用浏览器访问网站的过程：
     * 1. 打开浏览器
     * 2. 输入地址
     * 3. 访问
     * 4. 看结果
     * <p>
     * 使用Httpclient访问web服务的过程：
     * 1. 创建客户端，相当于打开浏览器
     * 2. 创建请求地址，相当于输入地址
     * 3. 发起请求，相当于访问网站
     * 4. 处理响应结果，相当于浏览器显示结果
     */
    public static void testGetNoParams() throws IOException {
        // 创建客户端对象
        HttpClient client = HttpClients.createDefault();
        // 创建请求地址
        HttpGet httpGet = new HttpGet("http://localhost:80/test");
        // 发起请求，接收响应对象
        HttpResponse response = client.execute(httpGet);
        // 获取响应体。响应数据是一个基于HTTP协议标准字符串封装的对象
        // 所以，响应体和响应头，都是封装的HTTP协议数据。直接使用可能有乱码或解析错误
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "UTF-8");
        System.out.println("服务器响应数据 - [" + string + "]");
    }

    /**
     * 有参数GET请求
     */
    public static void testGetParams() throws IOException, URISyntaxException {
        HttpClient client = HttpClients.createDefault();
        // 基于builder构建请求地址
        URIBuilder builder = new URIBuilder("http://localhost:80/params");
        // 基于单参数传递，构建请求地址
//        builder.addParameter("name", "straw");
//        builder.addParameter("password", "admin");

        // 基于多参数传递构建请求地址
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("name", "straw"));
        nvps.add(new BasicNameValuePair("password", "admin"));
        builder.addParameters(nvps);

        URI uri = builder.build();
        HttpResponse response = client.execute(new HttpGet(uri));
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(string);
    }

    /**
     * POST请求
     */
    public static void testPost() throws Exception {
        HttpClient client = HttpClients.createDefault();

        // 无参数POST请求
        HttpPost post = new HttpPost("http://localhost:80/test");
        HttpResponse response = client.execute(post);
        System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));

        //有参数POST请求
        //1. 请求头传递参数，和GET请求携带参数的方式一致
        URIBuilder builder = new URIBuilder("http://localhost:80/params");
        builder.addParameter("name", "post");
        builder.addParameter("password", "admin");
        HttpResponse response1 = client.execute(new HttpPost(builder.build()));
        System.out.println(EntityUtils.toString(response1.getEntity(), "UTF-8"));

        //2. 请求体传递参数
        HttpPost bodyParamsPost = new HttpPost("http://localhost:80/params");
        // 定义请求协议体，设置请求参数
        // 使用请求体传递参数的时候，需要定义请求体格式，默认是表单格式
        // 其实上述使用URIBuilder构建的URI对象，也是请求体传递参数的
        HttpEntity entity = new StringEntity("name=abc&password=123");
        // 这种方式会有问题，正确方式为 @RequestBody + 实体类传参和接收参数
        bodyParamsPost.setEntity(entity);
        System.out.println(EntityUtils.toString(client.execute(bodyParamsPost).getEntity(), "UTF-8"));

    }
}
