package com.example.kfdx.service;

import com.example.kfdx.util.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author : Jame
 * @date : 2022-05-19 08:57
 **/
@Service
public class ParseService {

    public Object getTopic(String param) {
        //请求的URl
        String uri = null;
        try {
            uri = "https://www.tiw.cn/s/" + URLEncoder.encode(param, "utf-8");
            System.out.println(uri);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //返回的页面内容
        String html = null;
        try {
            html = Utils.getHtmlContent(uri);
            Thread.sleep(400);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Document doc = Jsoup.parse(html);
        System.out.println(html);

        Elements htmllist = doc.select("ul[class=search_zhaodao_list]");
        for (Element element : htmllist) {
            String href = element.select("li").get(0).select("a").attr("href");
            String topicHtml = null;
            System.out.println(href);
            try {
                topicHtml = Utils.getHtmlContent("https://www.tiw.cn" + href);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Document topicDoc = Jsoup.parse(topicHtml);
            String topicType = topicDoc.select("p[class=zuoti_maintop_left]").select("span").text();
            if (topicType.contains("单") || topicType.contains("多")) {
                HashMap<String, String> map = new HashMap<>();
                Elements select = topicDoc.select("div[class=zuoti_main_list danxuan]").select("div[class=chapter_main_timutigan]");
                for (Element element1 : select) {
                    map.put(element1.select("span").text().toLowerCase(), element1.select("p").text());
                }
                System.out.println(map.get(topicDoc.select("span[class=zuoti_note_bomspan]").text().substring(0,1).toLowerCase()));
            }
            System.out.println(topicDoc.select("span[class=zuoti_note_bomspan]").text());


            return null;
        }

        return null;
    }


}
