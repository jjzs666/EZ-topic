package com.example.kfdx.service;

import com.example.kfdx.pojo.Topic;
import com.example.kfdx.util.Result;
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

    public Object getTopic(Topic topic) {
        String param = topic.getName();

        //直接取最大尾数
        param = param.substring(param.length() >= 20 ? param.length() - 20 : 0);

        String uri;
        try {
            uri = "https://www.tiw.cn/s/" + URLEncoder.encode(param, "utf-8");

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

        Elements htmllist = doc.select("ul[class=search_zhaodao_list]");
        for (Element element : htmllist) {
            if (topic.getStartLine() > htmllist.size()) {
                topic.setStartLine(htmllist.size());
            }
            String href = element.select("li").get(topic.getStartLine()).select("a").attr("href");
            String topicHtml = null;
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
                return Result.succeed(map.get(topicDoc.select("span[class=zuoti_note_bomspan]").text().substring(0, 1).toLowerCase()));
            }
            if (topicType.contains("判断")) {
                return Result.succeed(topicDoc.select("span[class=zuoti_note_bomspan]").text().substring(0, 1));
            }
            return Result.succeed(topicDoc.select("span[class=zuoti_note_bomspan]").text());

        }

        return Result.fail();
    }


}
