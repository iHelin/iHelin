package me.ianhe.isite.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.ianhe.isite.entity.QuestionEntity;
import me.ianhe.isite.service.QuestionService;
import me.ianhe.isite.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-04-16 12:02:07
 */
@RestController
@RequestMapping("sys/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  String key) {
        QueryWrapper<QuestionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(key), "id", key);
        IPage<QuestionEntity> data = questionService.page(new Page<>(page, limit), queryWrapper);
        return R.ok(data);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        QuestionEntity question = questionService.getById(id);
        return R.ok().put("data", question);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public R save(@RequestBody QuestionEntity questionEntity) {
        questionService.save(questionEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody QuestionEntity questionEntity) {
        questionService.updateById(questionEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Integer> ids) {
        questionService.removeByIds(ids);
        return R.ok();
    }

}
