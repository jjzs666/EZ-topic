package work.jame.topic.pojo;

import java.util.List;

/**
 * @author : Jame
 * @date : 2022-05-19 14:08
 **/
public class Topic {
    private String name;
    /**
     * 当前题目的类型
     * 1 单选题
     * 2 多选题
     * 3 判断题
     */
    private Integer type;
    private List<Answers> answers;

    public List<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
