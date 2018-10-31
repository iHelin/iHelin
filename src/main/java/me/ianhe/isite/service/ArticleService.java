package me.ianhe.isite.service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.google.common.collect.Maps;
import me.ianhe.isite.dao.ArticleMapper;
import me.ianhe.isite.entity.Article;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文章管理
 * PackageName:   com.seven.ianhe.manager
 * ClassName:     ArticleManager
 * Description:
 * Date           16/12/27
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Service
public class ArticleService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ArticleMapper articleMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public int addArticle(Article article) {
        article.setAuthor("Ian He");
        Date now = new Date();
        article.setCreateTime(now);
        article.setUpdateTime(now);
        article.setReadNum(0L);
        logger.debug("create a new article:{}", article.getTitle());
        return articleMapper.insert(article);
    }

    public Article editArticle(Article article) {
        logger.info("update article:{}", article);
        articleMapper.updateByPrimaryKey(article);
        return article;
    }

    public Article selectArticleById(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    public int deleteById(Integer id) {
        return articleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 分页查询
     *
     * @author iHelin
     * @since 2017/12/19 15:17
     */
    public PageInfo<Article> findByPage(String title, int pageNum, int pageSize) {
        Map<String, Object> res = Maps.newHashMap();
        if (StringUtils.isNotEmpty(title)) {
            res.put("title", title);
        }
        List<Article> data = articleMapper.listByCondition(res, new PageRowBounds(pageNum, pageSize));
        return new PageInfo<>(data);
    }

}
