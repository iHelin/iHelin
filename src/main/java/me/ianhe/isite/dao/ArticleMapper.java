package me.ianhe.isite.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.Article;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * ArticleMapper
 *
 * @author iHelin
 * @since 2017/10/17 15:27
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> listByCondition(Map<String, Object> res, RowBounds rowBounds);

    List<Integer> selectAllId();
}