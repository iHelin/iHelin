package me.ianhe.pp.controller.wechat;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.ianhe.pp.entity.FeedbackEntity;
import me.ianhe.pp.entity.SysUserEntity;
import me.ianhe.pp.service.FeedbackService;
import me.ianhe.pp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("wechat/feedback")
public class WxFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 列表
     */
    @GetMapping("/todayTimes")
    public R list(UsernamePasswordAuthenticationToken token) {
        SysUserEntity sysUserEntity = (SysUserEntity) token.getPrincipal();
        QueryWrapper<FeedbackEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sysUserEntity.getId());
        Date now = new Date();
        DateTime beginOfDay = DateUtil.beginOfDay(now);
        DateTime endOfDay = DateUtil.endOfDay(now);
        queryWrapper.between("create_time", beginOfDay, endOfDay);
        int count = feedbackService.count(queryWrapper);
        return R.ok(count);
    }


    /**
     * 新增
     */
    @PostMapping("/save")
    public R save(@RequestBody FeedbackEntity feedback, UsernamePasswordAuthenticationToken token) {
        SysUserEntity sysUserEntity = (SysUserEntity) token.getPrincipal();
        feedback.setUserId(sysUserEntity.getId());
        feedbackService.save(feedback);
        return R.ok();
    }

}
