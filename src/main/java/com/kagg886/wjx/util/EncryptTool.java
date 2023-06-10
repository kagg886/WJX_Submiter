package com.kagg886.wjx.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author kagg886
 * @date 2023/6/9 22:22
 **/
public class EncryptTool {

    public static String getDataEncByJQNonce(String a, int kTimes) {
        int b = kTimes % 10;
        if (b == 0) {
            b = 1;
        }
        int e;
        List<Character> c;
        int d;
        for (c = new ArrayList<>(), d = 0; d < a.length(); d++) {
            e = a.charAt(d) ^ b;
            c.add(((char) e));
        }
        StringBuilder builder = new StringBuilder();
        for (char chr : c) {
            builder.append(chr);
        }
        return builder.toString();
    }

    public static String getJQParam(String rndnum, String starttime, String activityId) {
        String aj = rndnum.split("\\.")[0];
        long ak = abcd1(Integer.parseInt(aj));
        String[] al = String.valueOf(ak).split("");
        long an = abcdu(starttime);
        String ao = String.valueOf(an);
        if (an % 10 > 0) {
            ao = new StringBuilder(ao).reverse().toString();
        }
        long ap = Long.parseLong(ao + "89123");
        String[] aq = (ap + String.valueOf(ak)).split("");
        String ar = abcd4(aq, "kgESOLJUbB2fCteoQdYmXvF8j9IZs3K0i6w75VcDnG14WAyaxNqPuRlpTHMrhz");
        long as = ap + ak + Integer.parseInt(activityId);
        String jqParam = abcd3(as, ar);
        return abcd5(jqParam);
    }

    public static String abcd5(String y) {
        int ad = 0;
        String[] aa = y.split("");
        for (int z = 0; z < aa.length; z++) {
            ad += aa[z].charAt(0);
        }
        int ab = y.length();
        int ac = ad % ab;
        List<String> ae = new ArrayList<>();
        for (int af = ac; af < ab; af++) {
            ae.add(aa[af]);
        }
        for (int ag = 0; ag < ac; ag++) {
            ae.add(aa[ag]);
        }
        return String.join("", ae);
    }

    public static String abcd3(long m, String n) {
        if (m - 62 < 0) {
            return String.valueOf(n.substring(Math.toIntExact(m)).charAt(0));
        }
        long o = m % 62;
        long p = m / 62;
        return abcd3(p, n) + n.substring(Math.toIntExact(o)).charAt(0);
    }

    public static String abcd4(String[] q, String r) {
        String[] s = r.split("");
        int t = r.length();
        for (int u = 0; u < q.length; u++) {
            int v = Integer.parseInt(q[u]);
            String w = s[v];
            s[v] = s[t - 1 - v];
            s[t - 1 - v] = w;
        }
        r = String.join("", s);
        return r;
    }

    public static long abcdu(String starttime) { //2023/6/9 21:35:44
        starttime = starttime.replace(":", "/").replace("/", " ");
        int[] unit = Arrays.stream(starttime.split(" ")).mapToInt(Integer::parseInt).toArray();
        LocalDateTime time = LocalDateTime.of(
                unit[0],
                unit[1],
                unit[2],
                unit[3],
                unit[4],
                unit[5]
        );
        int ag = -480;
        int ah = new Date().getTimezoneOffset();
        int ai = ag - ah;
        return time.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000 + ai * 60L;
    }

    public static long abcd1(long b) {
        long d = 2147483648L;
        long e = d - 1;
        long f = Long.parseLong(String.valueOf(1.0 * b / d).split("\\.")[0]);
        long h = Long.parseLong(String.valueOf(3597397.0 / d).split("\\.")[0]);
        long i = b & e;
        long j = 3597397 & e;
        long k = f ^ h;
        long l = i ^ j;
        return k * d + l;
    }
}
