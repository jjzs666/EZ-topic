package work.jame.topic.elasticsearch.encapsulation;

import org.apache.xalan.lib.sql.XConnection;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * @author : Jame
 * @date : 2022-05-21 14:52
 **/
@Component
public class ESUtil {


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 创建默认的索引
     */
    public void createIndex(){
        try {




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
