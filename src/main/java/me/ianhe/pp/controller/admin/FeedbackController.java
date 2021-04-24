package me.ianhe.pp.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.ianhe.pp.entity.FeedbackEntity;
import me.ianhe.pp.service.FeedbackService;
import me.ianhe.pp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-04-08 11:20:43
 */
@RestController
@RequestMapping("sys/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  String key) {
        QueryWrapper<FeedbackEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(key), "id", key);
        queryWrapper.orderByDesc("create_time");
        IPage<FeedbackEntity> data = feedbackService.page(new Page<>(page, limit), queryWrapper);
        return R.ok(data);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        FeedbackEntity feedback = feedbackService.getById(id);
        return R.ok().put("data", feedback);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public R save(@RequestBody FeedbackEntity feedbackEntity) {
        feedbackService.save(feedbackEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody FeedbackEntity feedbackEntity) {
        feedbackService.updateById(feedbackEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Integer> ids) {
        feedbackService.removeByIds(ids);
        return R.ok();
    }

}
