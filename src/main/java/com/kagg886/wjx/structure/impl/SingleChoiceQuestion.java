package com.kagg886.wjx.structure.impl;

import com.kagg886.wjx.structure.Question;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kagg886
 * @date 2023/6/10 11:50
 **/
public class SingleChoiceQuestion extends Question {
    private List<String> choices;
    private Integer choiceIndex;

    @Override
    public String spawnFormatAnswer() {
        if (isMust && choiceIndex == null) {
            throw new IllegalStateException("the question:" + question + "is must");
        }
        return choiceIndex == null ? "" : String.valueOf(choiceIndex);
    }

    public SingleChoiceQuestion(Element root) {
        super(root);
        choices = new ArrayList<>();
        for (Element i : root.getElementsByClass("label")) {
            choices.add(i.text());
        }
    }

    public void select(int i) {
        this.choiceIndex = i;
    }

    public List<String> getChoices() {
        return choices;
    }
}
