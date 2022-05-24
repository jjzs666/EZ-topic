package work.jame.topic.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import work.jame.topic.pojo.Topic;
import work.jame.topic.util.Result;

import java.util.Set;

/**
 * @author : Jame
 * @date : 2022-05-19 17:13
 **/
public interface ParseService {

    /**
     * 所有网站解析的通用接口-解析搜索出来的页面
     * @param topic
     * @return
     */
    Result parse(Topic topic);

    void parse(Topic topic, Set<Result> resultSet);


    /**
     * 解析具体的答案页面
     * @param topic
     * @param document
     * @return
     */
    Result lookForMatching(Topic topic, Document document);
}
