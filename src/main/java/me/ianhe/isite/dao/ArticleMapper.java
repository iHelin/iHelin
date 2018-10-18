package me.ianhe.isite.dao;

import me.ianhe.isite.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * ArticleMapper
 *
 * @author iHelin
 * @since 2017/10/17 15:27
 */
@CacheConfig(cacheNames = "article")
public interface ArticleMapper {

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @CacheEvict
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    /**
     * 查询文章
     *
     * @param id
     * @return
     */
    @Cacheable
    Article selectByPrimaryKey(Integer id);

    /**
     * 更新文章并更新缓存
     * key = "#article.id" 或 key = "#result.id"
     *
     * @param article
     * @return
     */
    @CachePut(key = "#article.id")
    int updateByPrimaryKeySelective(Article article);

    /**
     * 更新文章并更新缓存
     * key = "#article.id" 或 key = "#result.id"
     *
     * @param article
     * @return
     */
    @CachePut(key = "#article.id")
    int updateByPrimaryKey(Article article);

    List<Article> listByCondition(Map<String, Object> res, RowBounds rowBounds);

    List<Integer> selectAllId();
}