package work.jame.topic.service;

import work.jame.topic.pojo.Topic;
import work.jame.topic.util.Result;

/**
 * @author : Jame
 * @date : 2022-05-19 17:13
 **/
public interface ParseService {


    /**
     * 所有网站解析的通用接口
     * @param topic
     * @return
     */
    Result parse(Topic topic);
}
