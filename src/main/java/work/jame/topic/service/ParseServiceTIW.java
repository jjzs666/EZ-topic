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
import java.util.concurrent.TimeUnit;

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

    public Result parse(Topic topic) {
        String topicStr = topic.getName();

        //直接取最大尾数
        //这个网站的查询最大20长度,而且开头重复的内容较多,所以从后面开始查
        topicStr = topicStr.substring(topicStr.length() >= 20 ? topicStr.length() - 20 : 0);

        String url;
        try {

            url = properties.getAllRequestUrl().get("tiwSearchUrl") + URLEncoder.encode(topicStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String html = null;
        try {
            html = HttpUtil.getHtmlContent(url);
            //防止请求太快给禁ip了...
            Thread.sleep(150);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (StringUtil.isEmpty(html)) {
            return Result.failed("该页面没有数据!url:" + url);
        }

        //当前尝试次数
        int currentTryAcquireCount = 1;

        Document doc = Jsoup.parse(html);

        Elements htmllist = doc.select("ul[class=search_zhaodao_list] li");


        //如果此次没有匹配到则继续找下个答案的次数
        int nextTopicRetryCount;
        //防止搜索出的答案不够尝试的次数
        if (htmllist.size() < properties.getRetryNextTopicCount()) {
            nextTopicRetryCount = htmllist.size();
        } else {
            nextTopicRetryCount = properties.getRetryNextTopicCount();
        }


        //获取到查询结果的首页,里面有很多结果,需要点进去具体那个
        for (Element element : htmllist) {

            if (currentTryAcquireCount > nextTopicRetryCount) {
                return Result.failed("没有找到合适的答案", currentTryAcquireCount);
            }

            //这个题目的类型
            String searchResultType = element.select("em").text();
            //判断这个题目的类型,例如当前题目是单选,然后查出的题目是多选或判断,那么在下去没有意义
            if (StringUtil.getAnswerType(searchResultType) != topic.getType()) {
                continue;
            }

            //当前题目的相似度
            double currentTopicSimilarity;

            //题目
            String topicTitle = element.select("p").text();
            //当题目和前端传入的匹配值超过最低限制后才会继续执行答案的判断
            //题目都不一样那答案咋能对嘛
            if (StringUtil.isEmpty(topicTitle) ||
                    (currentTopicSimilarity = StringUtil.similarityRatio(topic.getName(), topicTitle)) < properties.getTopicAllowPassPrice()) {
                continue;
            }


            Result result;
            if ((result = lookForMatching(topic, element, currentTryAcquireCount, currentTopicSimilarity))
                    != null) {
                //设置题目的类型
                result.setType(StringUtil.getAnswerType(searchResultType));
                //找到了
                return result;
            } else {
                currentTryAcquireCount++;
            }
            try {
                //防止访问太快
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Result.failed("没有找到合适的答案", currentTryAcquireCount);
    }


    /**
     * 寻找匹配度最高的答案
     *
     * @param topic
     * @param element
     * @param tryAcquireCount
     * @param topicSimilarity
     * @return
     */
    private Result lookForMatching(Topic topic, Element element, Integer tryAcquireCount, double topicSimilarity) {

        String href = element.select("li").select("a").attr("href");
        String topicHtml = null;
        try {
            topicHtml = HttpUtil.getHtmlContent(properties.getAllRequestUrl().get("tiwSearchParticularsUrl") + href);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (StringUtil.isEmpty(topicHtml)) {
            return null;
        }
        //现在已经到题目的具体解析界面了,寻找答案即可
        Document topicDoc = Jsoup.parse(topicHtml);
        String topicType = topicDoc.select("p[class=zuoti_maintop_left]").select("span").text();

        Elements select = topicDoc.select("div[class=zuoti_main_list danxuan]").select("div[class=chapter_main_timutigan]");

        //题解给的选项
        String key = topicDoc.select("span[class=zuoti_note_bomspan]").text().substring(0, 1).toLowerCase();

        String searchAnswer = null;
        for (Element element1 : select) {
            //判断当前题目类型,如果单多选则获取选项abcd来和答案解析首字母来判断
            //如果是判断题则使用内容来判断,因为判断题的题解直接就是对或错
            if (topic.getType() == 3) {
                if (element1.select("p").text().equals(key)) {
                    searchAnswer = element1.select("p").text();
                    break;
                }

            } else if (topic.getType() == 1) {
                if (element1.select("span").text().toLowerCase().equals(key)) {
                    searchAnswer = element1.select("p").text();
                    break;
                }
            }
        }

        //单选or判断
        if (topic.getType() == 1 || topic.getType() == 3) {

            //相似度最高的选择答案
            Answers similarityHighest = null;
            //当前最高的相似度值
            double currentSimilarityHighest = 0;

            //获取到所有选项了,现在只需要遍历寻找到匹配度最高的选择返回即可
            List<Answers> answers = topic.getAnswers();
            for (Answers answer : answers) {
                double similarity;
                //当前的匹配度大于设置的才能返回
                if ((similarity = StringUtil.similarityRatio(answer.getContent(), searchAnswer)) >= properties.getAnswerAllowPassPrice()) {
                    if (similarity >= currentSimilarityHighest) {
                        currentSimilarityHighest = similarity;
                        similarityHighest = answer;
                    }
                }
            }
            if (similarityHighest != null) {
                return Result.succeed(new Answers[]{similarityHighest}, tryAcquireCount, topicSimilarity, currentSimilarityHighest);
            }
            return null;

        } else if (topicType.contains("多") && topic.getType() == 2) {
        }
//      }  } else if (topicType.contains("判断") && topic.getType() == 3) {
//            //searchAnswer
//         //   return Result.succeed(topicDoc.select("span[class=zuoti_note_bomspan]").text().substring(0, 1));
//        }
        return null;
    }
}
