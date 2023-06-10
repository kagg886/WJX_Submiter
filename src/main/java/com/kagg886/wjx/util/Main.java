package com.kagg886.wjx.util;

import com.kagg886.wjx.Survey;
import com.kagg886.wjx.structure.Question;
import com.kagg886.wjx.structure.impl.MultiChoiceQuestion;
import com.kagg886.wjx.structure.impl.SingleChoiceQuestion;
import com.kagg886.wjx.structure.impl.TextQuestion;

import java.util.UUID;

/**
 * @author kagg886
 * @date 2023/6/9 21:19
 **/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Survey survey = new Survey("https://www.wjx.cn/vm/wEMNFTP.aspx");
        System.out.println(survey.getTitle());
        System.out.println(survey.getSummary());


        for (Question a : survey.getQuestions()) {
            System.out.println("Question:" + a.getQuestion());

            if (a instanceof SingleChoiceQuestion) {
                System.out.println(((SingleChoiceQuestion) a).getChoices());
                ((SingleChoiceQuestion) a).select(1);
            }

            if (a instanceof MultiChoiceQuestion) {
                System.out.println(((MultiChoiceQuestion) a).getChoices());
                ((MultiChoiceQuestion) a).select(1, 2);
            }

            if (a instanceof TextQuestion) {
                ((TextQuestion) a).setAnswer(UUID.randomUUID().toString());
            }
        }
        Thread.sleep(3000);
        System.out.println(survey.submit());
    }
}
