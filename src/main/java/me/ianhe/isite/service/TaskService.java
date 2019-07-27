package me.ianhe.isite.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.ianhe.isite.dao.ArticleMapper;
import me.ianhe.isite.dao.CommonRedisDao;
import me.ianhe.isite.dao.PoemMapper;
import me.ianhe.isite.entity.Article;
import me.ianhe.isite.entity.Poem;
import me.ianhe.isite.model.ding.FeedCard;
import me.ianhe.isite.model.ding.Link;
import me.ianhe.isite.model.douban.Movie;
import me.ianhe.isite.model.douban.Subject;
import me.ianhe.isite.utils.Constant;
import me.ianhe.isite.utils.JsonUtil;
import me.ianhe.isite.utils.WeChatUtil;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private ArticleMapper articleMapper;

    @Autowired
    private PoemMapper poemMapper;

    @Autowired
    private CommonRedisDao commonRedisDao;

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
        sendMenu();
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
        poemRun();
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
        syncReadCount();
    }

    /**
     * 每天7点执行
     *
     * @author iHelin
     * @since 2018/1/10 09:07
     */
    @Scheduled(cron = "0 0 7 * * *")
    public void runEveryDay7() {
        logger.debug("runEveryDay7");
        dailyEnglish();
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
     * 每天同步文章阅读数
     *
     * @author iHelin
     * @since 2017/12/21 10:17
     */
    private void syncReadCount() {
        List<Integer> ids = articleMapper.selectAllId();
        for (Integer id : ids) {
            Long readCount = commonRedisDao.getLong(Constant.READ_COUNT_KEY + id);
            Article article = new Article();
            article.setId(id);
            article.setReadNum(readCount);
            articleMapper.updateByPrimaryKeySelective(article);
        }
    }

    /**
     * 古诗
     *
     * @author iHelin
     * @since 2017/11/13 17:17
     */
    private void poemRun() {
        Poem poem = poemMapper.getByRandom();
        String msg = poem.getContent() + "      --" + poem.getTitle();
        dingService.sendTextMsg(msg);
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
            Map<String, Object> contentMap = Maps.newHashMap();
            String res = WeChatUtil.doGetStr("http://open.iciba.com/dsapi");
            Map<String, Object> resMap = JsonUtil.parseMap(res);
            contentMap.put("title", "葫芦娃学英语");
            String text = "## 距离2019【ky】还剩" + betweenDays + "天！\n" +
                    "![葫芦娃学英语](" + resMap.get("picture") + ")\n" +
                    "##### " + resMap.get("content") + " \n" +
                    "> " + resMap.get("note") + " \n";
            contentMap.put("text", text);
            Map<String, Object> data = Maps.newHashMap();
            data.put("msgtype", "markdown");
            data.put("markdown", contentMap);
            String jsonData = JsonUtil.toJson(data);
            dingService.doSend(jsonData);
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
        String res = WeChatUtil.doGetStr(url);
        Movie movie = JsonUtil.parseObject(res, Movie.class);
        List<Subject> subjectList = movie.getSubjects();
        if (subjectList == null || subjectList.isEmpty()) {
            dingService.sendTextMsg("电影接口有问题啦，快去看看咋回事！");
            return;
        }
        List<Link> links = Lists.newArrayList();
        Link link = new Link();
        link.setTitle("又要到周末啦，快来看看最近的热门电影吧！");
        link.setMessageURL(subjectList.get(0).getUrl());
        link.setPicURL(subjectList.get(0).getCover());
        links.add(link);
        int maxSize = 5;
        for (int i = 1; i < maxSize; i++) {
            link = new Link();
            link.setTitle(subjectList.get(i).getTitle());
            link.setMessageURL(subjectList.get(i).getUrl());
            link.setPicURL(subjectList.get(i).getCover());
            links.add(link);
        }
        FeedCard feedCard = new FeedCard();
        feedCard.setLinks(links);
        dingService.sendFeedCardMsg(feedCard);
    }

    /**
     * 发送菜单
     *
     * @author iHelin
     * @since 2018/7/8 20:05
     */
    private void sendMenu() {
        InputStream inputStream = WeChatUtil.doGetInputStream("https://dev.fluttercn.com/now-eat/menu-190310.json");
        BOMInputStream bomInputStream = new BOMInputStream(inputStream); //仅能检测到UTF8的bom，且在流中exclude掉bom
        HashMap map = JsonUtil.parseMap(bomInputStream);
        List<String> workDate = (List<String>) map.get("workDate");
        String currentDateStr = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        for (int currentIndex = 0; currentIndex < workDate.size(); currentIndex++) {
            if (currentDateStr.equals(workDate.get(currentIndex))) {
                Map<String, Object> nooning = (Map<String, Object>) map.get("nooning");
                nooning.put("currentIndex", currentIndex);
                List<Map> timeplan = (List<Map>) map.get("timeplan");
                String mealTime = "";
                for (Map tp : timeplan) {
                    if (((String) tp.get("key")).contains("B")) {
                        mealTime = (String) tp.get("value");
                    }
                }
                nooning.put("mealTime", mealTime);
                emailService.sendTemplateMail("ihelin@outlook.com", "今日菜单", "iflytekFood.ftl", nooning);
            }
        }
    }

}
