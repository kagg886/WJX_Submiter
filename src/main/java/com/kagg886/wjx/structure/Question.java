package com.kagg886.wjx.structure;

import com.kagg886.wjx.structure.impl.MultiChoiceQuestion;
import com.kagg886.wjx.structure.impl.SingleChoiceQuestion;
import com.kagg886.wjx.structure.impl.TextQuestion;
import org.jsoup.nodes.Element;

public abstract class Question {

    protected String question;

    protected boolean isMust;

    public abstract String spawnFormatAnswer();

    public Question(Element root) {
        this.isMust = root.getElementsByClass("req").size() != 0;
        this.question = root.getElementsByClass("field-label").text();
    }

    public static Question forQuestion(Element root) {
        return switch (root.attr("type")) {
            case "3" -> new SingleChoiceQuestion(root);
            case "4" -> new MultiChoiceQuestion(root);
            case "1" -> new TextQuestion(root);
            default -> throw new IllegalStateException("Unexpected value: " + root.attr("type"));
        };
    }

    public String getQuestion() {
        return question;
    }

    public boolean isMust() {
        return isMust;
    }
}