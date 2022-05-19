package work.jame.topic.service;

import work.jame.topic.pojo.Answers;
import work.jame.topic.pojo.Topic;
import work.jame.topic.util.TopicProperties;
import work.jame.topic.util.Result;
import work.jame.topic.util.HttpUtil;
import work.jame.topic.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author : Jame
 * @date : 2022-05-19 08:57
 * https://www.tiw.cn/
 * 题王网站解析服务
 **/
@Service
public class ParseServiceTIW implements ParseService {


    @Autowired
    private TopicProperties properties;

    public Object parse(Topic topic) {
        String topicStr = topic.getName();

        //直接取最大尾数
        //这个网站的查询最大20长度,而且开头重复的内容较多,所以从后面开始查
        topicStr = topicStr.substring(topicStr.length() >= 20 ? topicStr.length() - 20 : 0);

        String uri;
        try {
            uri = properties.getUrl()[0] + URLEncoder.encode(topicStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String html = null;
        try {
            html = HttpUtil.getHtmlContent(uri);
            //防止请求太快给禁ip了...
            Thread.sleep(200);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (StringUtil.isEmpty(html)) {
            return -1;
        }

        Document doc = Jsoup.parse(html);

        Elements htmllist = doc.select("ul[class=search_zhaodao_list]");
        //获取到查询结果的首页,里面有很多结果,需要点进去具体那个
        for (Element element : htmllist) {
            //如果此次没有匹配到则继续找下个答案的此处
            int nextTopicRetryCount;
            if (htmllist.size() < properties.getNextTopicRetryCount()) {
                nextTopicRetryCount = htmllist.size();
            } else {
                nextTopicRetryCount = properties.getNextTopicRetryCount();
            }
            for (int i = 0; i < nextTopicRetryCount; i++) {
                int correctAnswerIndex;
                if ((correctAnswerIndex = lookForMatching(topic, element, nextTopicRetryCount)) > -1) {
                    //找到了
                    return correctAnswerIndex;
                }
                try {
                    //防止访问太快
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }


    /**
     * 寻找匹配度最高的答案
     *
     * @param topic
     * @param element
     * @param nextTopicRetryCount
     * @return
     */
    private int lookForMatching(Topic topic, Element element, int nextTopicRetryCount) {
        String href = element.select("li").get(nextTopicRetryCount).select("a").attr("href");
        String topicHtml = null;
        try {
            topicHtml = HttpUtil.getHtmlContent(properties.getUrl()[1] + href);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (StringUtil.isEmpty(topicHtml)) {
            return -1;
        }
        //现在已经到题目的具体解析界面了,寻找答案即可
        Document topicDoc = Jsoup.parse(topicHtml);
        String topicType = topicDoc.select("p[class=zuoti_maintop_left]").select("span").text();
        //判断这个题目的类型,例如当前题目是单选,然后查出的题目是多选或判断,那么在下去没有意义
        if (topicType.contains("单") && topic.getType() == 1) {
            //选项a/b/c/d-题目
            HashMap<String, String> map = new HashMap<>();
            Elements select = topicDoc.select("div[class=zuoti_main_list danxuan]").select("div[class=chapter_main_timutigan]");
            for (Element element1 : select) {
                map.put(element1.select("span").text().toLowerCase(), element1.select("p").text());
            }

            //题解给的选项
            String key=topicDoc.select("span[class=zuoti_note_bomspan]").text().substring(0, 1).toLowerCase();


            //相似度最高的选择答案
            Answers similarityHighest = null;
            //当前最高的相似度值
            double currentSimilarityHighest = 0;

            //获取到所有选项了,现在只需要遍历寻找到匹配度最高的选择返回即可
            List<Answers> answers = topic.getAnswers();
            for (Answers answer : answers) {
                double currentSimilarity = -1;
                if ((currentSimilarity = StringUtil.similarityRatio(map.get(key), answer.getContent())) > currentSimilarityHighest) {
                    //当前的匹配度大于设置的才能返回
                    if (currentSimilarity >= properties.getAllowPassPrice()) {
                        currentSimilarityHighest = currentSimilarity;
                        similarityHighest = answer;
                    }
                }
            }
            if (similarityHighest != null) {
                return similarityHighest.getIndex();
            }
            return -1;

        } else if (topicType.contains("多") && topic.getType() == 2) {

        } else if (topicType.contains("判断") && topic.getType() == 2) {
            //return Result.succeed(topicDoc.select("span[class=zuoti_note_bomspan]").text().substring(0, 1));
        }
        return -1;
    }
}
