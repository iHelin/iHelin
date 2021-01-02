package me.ianhe.isite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.common.collect.Maps;
import me.ianhe.isite.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * BaseController
 *
 * @author iHelin
 * @since 2017/10/17 15:28
 */
public abstract class BaseController {

    @Autowired
    protected ArticleService articleService;
    @Autowired
    protected ScoreService scoreService;
    @Autowired
    protected FileService fileService;
    //    @Autowired
//    protected JmsProducerService producerService;
    @Autowired
    protected QrcodeService qrcodeService;
    //    @Autowired
//    protected CommonRedisDao commonRedisDao;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected CommonService commonService;
    @Autowired
    protected DefaultKaptcha defaultKaptcha;
    @Autowired
    protected AsyncService asyncService;
    @Autowired
    protected TaskService taskService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 默认分页大小
     */
    protected static final String DEFAULT_PAGE_NUMBER = "1";
    protected static final String DEFAULT_PAGE_LENGTH = "10";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String STATUS = "status";
    private static final String DATA = "data";
    private static final String MESSAGE = "msg";

    protected Map<String, Object> success() {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, SUCCESS);
        return res;
    }

    protected Map<String, Object> success(Object data) {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, SUCCESS);
        res.put(DATA, data);
        return res;
    }

    protected Map<String, Object> error() {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, ERROR);
        return res;
    }

    protected Map<String, Object> error(Object model) {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, ERROR);
        res.put(MESSAGE, model);
        return res;
    }

}
