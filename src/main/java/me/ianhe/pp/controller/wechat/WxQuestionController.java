package me.ianhe.pp.controller.wechat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.ianhe.pp.entity.QuestionEntity;
import me.ianhe.pp.service.QuestionService;
import me.ianhe.pp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-04-16 12:02:07
 */
@RestController
@RequestMapping("wechat/question")
public class WxQuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list() {
        QueryWrapper<QuestionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "question", "answer");
        queryWrapper.orderByAsc("sort");
        List<QuestionEntity> data = questionService.list(queryWrapper);
        return R.ok(data);
    }

}
