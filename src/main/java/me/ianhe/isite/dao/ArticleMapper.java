package me.ianhe.isite.dao;

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
public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> listByCondition(Map<String, Object> res, RowBounds rowBounds);

    long listCount(Map<String, Object> res);

    List<Integer> selectAllId();
}