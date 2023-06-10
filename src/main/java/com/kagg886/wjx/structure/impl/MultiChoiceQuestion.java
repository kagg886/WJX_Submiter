package com.kagg886.wjx.structure.impl;

import com.kagg886.wjx.structure.Question;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author kagg886
 * @date 2023/6/10 11:51
 **/
public class MultiChoiceQuestion extends Question {

    private List<String> choices;
    private String choiceStr;

    @Override
    public String spawnFormatAnswer() {

        if (isMust && choiceStr == null) {
            throw new IllegalStateException("the question:" + question + "is must");
        }
        return choiceStr == null ? "" : choiceStr;
    }

    public MultiChoiceQuestion(Element root) {
        super(root);
        choices = new ArrayList<>();
        for (Element i : root.getElementsByClass("label")) {
            choices.add(i.text());
        }
    }

    public void select(int... i) {
        this.choiceStr = Arrays.stream(i).mapToObj(String::valueOf).collect(Collectors.joining("|"));
    }

    public List<String> getChoices() {
        return choices;
    }
}
