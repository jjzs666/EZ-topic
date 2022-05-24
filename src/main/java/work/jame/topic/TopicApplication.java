package work.jame.topic;

import org.apache.commons.lang3.StringUtils;
import work.jame.topic.config.SpringConfig;
import work.jame.topic.service.ParseService;
import work.jame.topic.util.StringUtil;
import work.jame.topic.util.TopicProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(TopicProperties.class)
public class TopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicApplication.class, args);
        System.out.println(":) 服务已启动! 当前存在的服务:" +
                StringUtil.englishAbbreviationConversionToChineseName(SpringConfig.getAllService().keySet()));
        List<String> list = SpringConfig.getBean(TopicProperties.class).getExcludeService();
        if (list != null && list.size() != 0) {
            System.out.println("不使用的服务:" + StringUtil.englishAbbreviationConversionToChineseName(list));
        }

    }

}
