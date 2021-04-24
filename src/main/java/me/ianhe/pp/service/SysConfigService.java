package me.ianhe.pp.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.ianhe.pp.dao.SysConfigDao;
import me.ianhe.pp.entity.SysConfigEntity;
import me.ianhe.pp.exception.RRException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Service
public class SysConfigService extends ServiceImpl<SysConfigDao, SysConfigEntity> {

    public IPage<SysConfigEntity> queryPage(Long page, Long limit, String paramKey) {
        IPage<SysConfigEntity> data = this.page(
                new Page<>(page, limit),
                new QueryWrapper<SysConfigEntity>()
                        .like(StringUtils.hasText(paramKey), "param_key", paramKey)
                        .eq("status", 1)
        );
        return data;
    }

    public void saveConfig(SysConfigEntity config) {
        this.save(config);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfigEntity config) {
        this.updateById(config);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            SysConfigEntity config = this.getById(id);
        }

        this.removeByIds(Arrays.asList(ids));
    }

    public String getValue(String key) {
        SysConfigEntity config = baseMapper.queryByKey(key);
        return config == null ? null : config.getParamValue();
    }

    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StringUtils.hasText(value)) {
            return JSONUtil.toBean(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RRException("获取配置参数失败");
        }
    }
}
