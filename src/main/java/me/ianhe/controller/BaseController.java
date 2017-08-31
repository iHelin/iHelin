package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.service.*;
import me.ianhe.utils.Global;
import me.ianhe.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;

/**
 * Created by iHelin on 16/11/1.
 */
public abstract class BaseController {

    @Autowired
    protected AccessTokenService accessTokenManager;
    @Autowired
    protected UserService userManager;
    @Autowired
    protected ServiceMenuService serviceMenuManager;
    @Autowired
    protected ArticleService articleManager;
    @Autowired
    protected QrcodeService qrcodeManager;
    @Autowired
    protected ScoreService myScoreManager;
    @Autowired
    protected Global global;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final int DEFAULT_PAGE_LENGTH = 10;//默认分页大小
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String STATUS = "status";
    private static final String DATA = "data";
    private static final String TOTAL = "total";

    protected String success() {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, SUCCESS);
        return JSON.toJson(res);
    }

    protected String page(Collection model, Integer count) {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, SUCCESS);
        res.put(DATA, model);
        res.put(TOTAL, count);
        return JSON.toJson(res);
    }

    protected <T> String success(T model) {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, SUCCESS);
        res.put(DATA, model);
        return JSON.toJson(res);
    }

    protected String error() {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, ERROR);
        return JSON.toJson(res);
    }

    protected <T> String error(T model) {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, ERROR);
        res.put(DATA, model);
        return JSON.toJson(res);
    }

    protected String ftl(String ftlFileName) {
        return ftlFileName;
    }

}
