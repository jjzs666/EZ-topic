package work.jame.topic.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import work.jame.topic.service.ParseService;
import work.jame.topic.util.TopicProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : Jame
 * @date : 2022-05-24 08:44
 **/
@Component
public class SpringConfig implements ApplicationContextAware {


    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> Map<String, T> getBeansByType(Class<T> tClass) {
        TopicProperties properties = getBean(TopicProperties.class);
        List<String> excludeService = properties.getExcludeService();
        if(excludeService!=null&&excludeService.size()!=0){
            HashMap<String, T> resultMap = new HashMap<>();

            context.getBeansOfType(tClass).forEach((k, v) -> {
                if (!excludeService.contains(k)) {
                    resultMap.put(k, v);
                }
            });
            return resultMap;
        }


        return  context.getBeansOfType(tClass);
    }

    public static Map<String, ParseService> getAllService() {
        return context.getBeansOfType(ParseService.class);
    }
}