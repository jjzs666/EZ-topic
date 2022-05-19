package work.jame.topic.util;
 
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
 
import java.io.IOException;

/**
 * @ClassName: Utils
 * @Description: TODO
 * @Author: Wxz
 * @Date: 2020/11/13 15:25
 * @Version: V1.0
 */
public class HttpUtil {
    /**
     * 公共爬虫类
     * @param url 请求Uri路径
     * @return
     * @throws IOException
     */
    public static String getHtmlContent(String url) throws IOException {
        //1、建立请求客户端
        CloseableHttpClient aDefault = HttpClients.createDefault();

        //2、获取请求地址
        HttpGet httpGet = new HttpGet(url);
       // httpGet.setHeader("Cookie","_ga=GA1.1.2142790637.1652919567; Hm_lvt_d6d5dc482c0fa4b40dedfd1388b0793c=1652919575; _ga_3S9YBZCC9Q=GS1.1.1652919566.1.1.1652921953.0; Hm_lpvt_d6d5dc482c0fa4b40dedfd1388b0793c=1652921953");
       // httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

       // httpGet.setHeader("Accept-Encoding","gzip, deflate, br");
       // httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
       // httpGet.setHeader("Cache-Control","max-age=0");
       // httpGet.setHeader("Connection","keep-alive");
       // httpGet.setHeader("Host","www.tiw.cn");
       // httpGet.setHeader("Referer",url);
       // httpGet.setHeader("sec-ch-ua"," Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101");
       // httpGet.setHeader("sec-ch-ua-mobile","?0");
       // httpGet.setHeader("sec-ch-ua-platform","\"Windows\"");
       // httpGet.setHeader("Sec-Fetch-Dest","document");
       // httpGet.setHeader("Sec-Fetch-Mode","navigate");
       // httpGet.setHeader("Sec-Fetch-Site","same-origin");
       // httpGet.setHeader("Sec-Fetch-User","?1");
       // httpGet.setHeader("Upgrade-Insecure-Requests","1");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36");


        //3、获取网址返回结果
        CloseableHttpResponse execute = aDefault.execute(httpGet);
        //4、获取返回实体
        HttpEntity entity = execute.getEntity();
        //5、将获取的实体以字符串的形式进行返回
        String content = EntityUtils.toString(entity);
        //6、查看源代码是关闭流的意思
        EntityUtils.consume(entity);
        return content;
    }
}