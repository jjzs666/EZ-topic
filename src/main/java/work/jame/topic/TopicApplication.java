package work.jame.topic;

import work.jame.topic.util.TopicProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicProperties.class)
public class TopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicApplication.class, args);
    }

}
