package me.ianhe.pp.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @author iHelin
 * @since 2017/6/13 15:53
 */
@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DingService dingService;

    @Autowired
    private EmailService emailService;

    /**
     * 工作日11点执行
     *
     * @author iHelin
     * @since 2018/1/10 09:07
     */
    @Scheduled(cron = "0 0 11 ? * MON-FRI")
    public void runEveryDay11() {
        logger.debug("runWorkDay11");
    }

    /**
     * 工作日12点执行
     *
     * @author iHelin
     * @since 2017/12/21 10:24
     */
    @Scheduled(cron = "0 0 12 ? * MON-FRI")
    public void runWorkDay12() {
        logger.debug("runWorkDay12");
    }


    /**
     * 工作日18点执行
     *
     * @author iHelin
     * @since 2017/12/21 10:24
     */
    @Scheduled(cron = "0 0 18 ? * MON-FRI")
    public void runWorkDay18() {
        logger.debug("runWorkDay18");
    }

    /**
     * 每天0点执行
     *
     * @author iHelin
     * @since 2017/12/21 10:21
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void runEveryDay0() {
        logger.debug("runEveryDay0");
//        syncReadCount();
    }

    /**
     * 每天7点执行
     *
     * @author iHelin
     * @since 2018/1/10 09:07
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void runEveryDay() {
        logger.debug("runEveryDay");
        dailyEnglish();
        daily();
    }

    /**
     * 每周五16点执行
     *
     * @author iHelin
     * @since 2018/7/8 18:06
     */
    @Scheduled(cron = "0 0 16 ? * FRI")
    public void runFriday16() {
        logger.debug("runFriday16");
        sendMovie();
    }

    /**
     * 每日一句
     */
    private void daily() {
        String res = HttpUtil.get("http://poetry.apiopen.top/sentences");
        JSONObject sentences = JSONUtil.parseObj(res);
        String name = sentences.getJSONObject("result").getStr("name");
        String from = sentences.getJSONObject("result").getStr("from");
        dingService.sendTextMsg(name + "--from：" + from);
    }

    /**
     * 英语
     *
     * @author iHelin
     * @since 2017/11/13 17:17
     */
    private void dailyEnglish() {
        Calendar terminalDate = new Calendar.Builder().setDate(2018, Calendar.DECEMBER, 23)
                .build();
        long terminalLong = terminalDate.getTimeInMillis();
        long nowLong = System.currentTimeMillis();
        long betweenDays = (terminalLong - nowLong) / (1000L * 3600 * 24);
        if (betweenDays > 0) {
            JSONObject contentMap = new JSONObject();
            String res = HttpUtil.get("http://open.iciba.com/dsapi");
            JSONObject resMap = JSONUtil.parseObj(res);
            contentMap.set("title", "葫芦娃学英语");
            String text = "## 距离2019【ky】还剩" + betweenDays + "天！\n" +
                    "![葫芦娃学英语](" + resMap.getStr("picture") + ")\n" +
                    "##### " + resMap.getStr("content") + " \n" +
                    "> " + resMap.getStr("note") + " \n";
            contentMap.set("text", text);
            JSONObject data = new JSONObject();
            data.set("msgtype", "markdown");
            data.set("markdown", contentMap);
            dingService.doSend(data);
        }
    }

    /**
     * 电影
     *
     * @author iHelin
     * @since 2017/12/1 15:57
     */
    private void sendMovie() {
        String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&page_limit=50&page_start=0";
        String res = HttpUtil.get(url);
        JSONObject movie = JSONUtil.parseObj(res);
        JSONArray subjectList = movie.getJSONArray("subjects");
        if (subjectList == null || subjectList.isEmpty()) {
            dingService.sendTextMsg("电影接口有问题啦，快去看看咋回事！");
            return;
        }
        List<JSONObject> links = Lists.newArrayList();
        JSONObject link = new JSONObject();
        link.set("title", "又要到周末啦，快来看看最近的热门电影吧！");
        link.set("messageURL", subjectList.getJSONObject(0).getStr("url"));
        link.set("picURL", subjectList.getJSONObject(0).getStr("cover"));
        links.add(link);
        int maxSize = 5;
        for (int i = 1; i < maxSize; i++) {
            link = new JSONObject();
            link.set("title", subjectList.getJSONObject(i).getStr("title"));
            link.set("messageURL", subjectList.getJSONObject(i).getStr("url"));
            link.set("picURL", subjectList.getJSONObject(i).getStr("cover"));
            links.add(link);
        }
        JSONObject feedCard = new JSONObject();
        feedCard.set("links", links);
        dingService.sendFeedCardMsg(feedCard);
    }

}
