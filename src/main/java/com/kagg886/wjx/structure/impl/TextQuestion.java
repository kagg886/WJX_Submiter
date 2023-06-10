package com.kagg886.wjx.structure.impl;

import com.kagg886.wjx.structure.Question;
import org.jsoup.nodes.Element;

/**
 * @author kagg886
 * @date 2023/6/10 12:03
 **/
public class TextQuestion extends Question {
    private String answer;

    @Override
    public String spawnFormatAnswer() {
        if (isMust && answer == null) {
            throw new IllegalStateException("the question:" + question + "is must");
        }
        return answer == null ? "" : answer;
    }

    public TextQuestion(Element root) {
        super(root);
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
