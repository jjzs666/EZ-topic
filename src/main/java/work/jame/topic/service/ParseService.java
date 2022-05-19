package work.jame.topic.service;

import work.jame.topic.pojo.Topic;

/**
 * @author : Jame
 * @date : 2022-05-19 17:13
 **/
public interface ParseService {


    /**
     * 所有网站解析的通用接口
     * 返回object因为每个网站里面的内容不同
     * 而且存在单选/多选/判断/甚至填空题
     * @param topic
     * @return
     */
    Object parse(Topic topic);
}
