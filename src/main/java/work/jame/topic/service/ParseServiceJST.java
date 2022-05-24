package work.jame.topic.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.jame.topic.pojo.Answers;
import work.jame.topic.pojo.Topic;
import work.jame.topic.util.HttpUtil;
import work.jame.topic.util.Result;
import work.jame.topic.util.StringUtil;
import work.jame.topic.util.TopicProperties;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

/**
 * @author : Jame
 * @date : 2022-05-24 13:23
 * https://www.jiansouti.com/
 * 简搜题解析服务
 **/
@Service("JST")
public class ParseServiceJST implements ParseService {
    @Autowired
    private TopicProperties properties;

    @Override
    public Result parse(Topic topic) {
        String url;
        try {
            url = "https://www.jiansouti.com/search.php?q=" + URLEncoder.encode(topic.getName(), "utf-8") + "&f=_all&m=yes&syn=yes&s=relevance";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Document doc = HttpUtil.htmlTransitionDocument(url, properties.getIntervalTime());

        if (doc == null) {
            return Result.failed("该页面没有数据.jst!url:" + url);
        }
        Elements li = doc.select("ul[class=middle-fl-ul]").select("li");


        //当前尝试次数
        int currentTryAcquireCount = 1;

        //如果此次没有匹配到则继续找下个答案的次数
        int nextTopicRetryCount;
        //防止搜索出的答案不够尝试的次数
        if (li.size() < properties.getRetryNextTopicCount()) {
            nextTopicRetryCount = li.size();
        } else {
            nextTopicRetryCount = properties.getRetryNextTopicCount();
        }

        for (Element element : li) {
            if (currentTryAcquireCount > nextTopicRetryCount) {
                return Result.failed("尝试次数已完", currentTryAcquireCount);
            }
            Element a = element.select("li").select("a").get(1);
            //题目
            String topicTitle = a.text();


            //当前题目的相似度
            double currentTopicSimilarity;
            if (StringUtil.isEmpty(topicTitle) ||
                    (currentTopicSimilarity = StringUtil.similarityRatio(topic.getName(), topicTitle)) < properties.getTopicAllowPassPrice()) {
                continue;
            }

            Document particularsDoc = HttpUtil.htmlTransitionDocument(a.attr("href"), properties.getIntervalTime());

            Result result;
            if ((result = lookForMatching(topic, particularsDoc))
                    != null) {
                //找到了
                //设置题目的相似度和尝试次数
                result.setTryAcquireCount(currentTryAcquireCount);
                result.setTopicSimilarity(currentTopicSimilarity);
                result.setSource("简搜题");
                result.setType(topic.getType());
                return result;
            } else {
                currentTryAcquireCount++;
            }
            try {
                //防止访问太快
                Thread.sleep(properties.getIntervalTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        return Result.failed("没有找到合适的答案");
    }

    @Override
    public void parse(Topic topic, Set<Result> resultSet) {
       resultSet.add(parse(topic));
    }

    @Override
    public Result lookForMatching(Topic topic, Document document) {
        Element p = document.select("div[class=show]").get(0).select("p").get(2);
        String[] answers = p.html().split("<br>");

        //先根据题解找到对应的答案
        String searchAnswerOption = document.select("div[class=show]").get(1).select("p").get(0).text();
        searchAnswerOption = searchAnswerOption.substring(searchAnswerOption.length() - 1);

        String searchAnswer = null;
        for (String answer : answers) {
            if (answer.startsWith(searchAnswerOption)) {
                searchAnswer = answer.substring(2);
                break;
            }
        }
        if (searchAnswer == null) {
            return Result.failed("没有合适的答案");
        }

        //相似度最高的选择答案
        Answers similarityHighest = null;
        //当前最高的相似度值
        double currentSimilarityHighest = 0;
        for (Answers answer : topic.getAnswers()) {
            double similarity;
            if ((similarity = StringUtil.similarityRatio(searchAnswer, answer.getContent())) >= properties.getAnswerAllowPassPrice()) {
                currentSimilarityHighest = similarity;
                similarityHighest = answer;
            }
        }
        if(similarityHighest==null){
            return Result.failed("没有合适的答案");
        }

        return Result.succeed(new Answers[]{similarityHighest}, currentSimilarityHighest);
    }


}
