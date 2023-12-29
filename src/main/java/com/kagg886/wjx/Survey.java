package com.kagg886.wjx;

import com.kagg886.wjx.structure.Question;
import com.kagg886.wjx.util.EncryptTool;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 问卷星公开API
 *
 * @author kagg886
 * @date 2023/6/10 10:09
 **/
public class Survey {
    //五个加密参数，提交用
    private final String shortId;
    private final String startTime;
    private final String rn;
    private final String activityId;

    private final String jqnonce;


    //下面是存储问题的区域
    private final List<Question> questions;

    //摘要提取器
    private String title;
    private String summary;

    public List<Question> getQuestions() {
        return questions;
    }


    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String submit() {
        StringBuilder dataSpawner = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            dataSpawner.append(i + 1).append("$");
            dataSpawner.append(questions.get(i).spawnFormatAnswer());
            dataSpawner.append("}");
        }
        return submit(dataSpawner.substring(0, dataSpawner.length() - 1));
    }

    public String submit(String encodedAnswer) {
        String submitType = "1";
        String kTimes = String.valueOf(new Random().nextInt(20) + 4);
        String hlv = "1";
        String jqParam = EncryptTool.getJQParam(rn, startTime, activityId);
        String jqsign = EncryptTool.getDataEncByJQNonce(jqnonce, Integer.parseInt(kTimes));
        String nw = "1";
        String t = String.valueOf(System.currentTimeMillis());
        Connection connection = Jsoup.connect("https://www.wjx.cn/joinnew/processjq.ashx" +
                "?shortid=" + shortId +
                "&starttime=" + URLEncoder.encode(startTime, StandardCharsets.UTF_8) +
                "&submittype=" + submitType +
                "&ktimes=" + kTimes +
                "&hlv=" + hlv +
                "&rn=" + rn +
                "&jqpram=" + URLEncoder.encode(jqParam, StandardCharsets.UTF_8) +
                "&nw=" + nw +
                "&t=" + t +
                "&jqnonce=" + URLEncoder.encode(jqnonce, StandardCharsets.UTF_8) +
                "&jqsign=" + URLEncoder.encode(jqsign, StandardCharsets.UTF_8)
        );
        connection.data("submitdata",encodedAnswer);
        connection.header("accept", "text/plain, */*; q=0.01");
        connection.header("accept-language", "zh-CN,zh;q=0.9");
        connection.header("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.referrer("https://www.wjx.cn/vm/" + shortId);

        String bdy;
        try {
            bdy = connection.method(Connection.Method.POST).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String code[] = bdy.split("〒");
        if (!code[0].equals("10")) {
            throw new IllegalStateException(code[1]);
        }
        return "https://www.wjx.cn" + code[1];
    }

    public Survey(String url) {
        this.shortId = url.split("vm/")[1].split("\\.")[0];
        this.startTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis());

        Connection conn = Jsoup.connect(url);
        Document document;
        try {
            document = conn.execute().parse();
            String dom = document.html();
            this.rn = dom.split("rndnum=\"")[1].split("\"")[0];
            this.activityId = String.valueOf(Long.parseLong(dom.split("activityId")[1].split("=")[1].split(";")[0]) ^ 2130030173L);
            this.jqnonce = dom.split("jqnonce=\"")[1].split("\"")[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!document.getElementById("divTip").text().isEmpty()) {
            throw new IllegalStateException(document.getElementById("divTip").text());
        }

        this.title = document.getElementById("htitle").text();
        this.summary = document.getElementById("divDesc") == null ? "" : document.getElementById("divDesc").text();

        questions = new ArrayList<>();
        for (Element i : document.getElementsByClass("ui-field-contain")) {
            questions.add(Question.forQuestion(i));
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Survey{");
        sb.append("shortId='").append(shortId).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", rn='").append(rn).append('\'');
        sb.append(", activityId='").append(activityId).append('\'');
        sb.append(", jqnonce='").append(jqnonce).append('\'');
        sb.append(", questions=").append(questions);
        sb.append('}');
        return sb.toString();
    }
}
