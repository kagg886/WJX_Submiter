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
        Survey survey = new Survey("https://ks.wjx.top/vm/tfhjWwL.aspx");
        System.out.println(survey.getTitle());
        System.out.println(survey.getSummary());


//        for (Question a : survey.getQuestions()) {
//            System.out.println("Question:" + a.getQuestion());
//
//            if (a instanceof SingleChoiceQuestion) {
//                System.out.println(((SingleChoiceQuestion) a).getChoices());
//                ((SingleChoiceQuestion) a).select(1);
//            }
//
//            if (a instanceof MultiChoiceQuestion) {
//                System.out.println(((MultiChoiceQuestion) a).getChoices());
//                ((MultiChoiceQuestion) a).select(1, 2);
//            }
//
//            if (a instanceof TextQuestion) {
//                ((TextQuestion) a).setAnswer(UUID.randomUUID().toString());
//            }
//        }
//        Thread.sleep(3000);
//        System.out.println(survey.submit());
        System.out.println(survey.submit("1$1}2$1}3$1}4$1}5$1}6$1}7$1}8$1}9$1}10$1|2}11$1|2}12$1|2}13$1|2}14$1|2}15$1|2}16$1|2}17$1}18$1}19$bce5da50-4d1a-481b-9cd8-26bab381de65}20$32b88b69-d7da-460b-a9d3-6fd0510f20e9}21$8aa365a3-4be1-480e-9a6f-6f45e8ee6bc1}22$5a6ba19b-62cb-46a7-bf2f-07fb30bc1f2f}23$f36dd286-8b4b-449c-959f-b4d62347ddc6"));
    }
}
