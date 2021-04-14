package me.ianhe.isite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianhe.isite.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
    protected QrcodeService qrcodeService;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected CommonService commonService;
    @Autowired
    protected JwtComponent jwtComponent;
    @Autowired
    protected AsyncService asyncService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected SysUserService sysUserService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 默认分页大小
     */
    protected static final String DEFAULT_PAGE_NUMBER = "1";
    protected static final String DEFAULT_PAGE_SIZE = "10";

}
