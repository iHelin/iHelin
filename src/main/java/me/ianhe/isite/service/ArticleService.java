package me.ianhe.isite.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {

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
    public IPage<Article> findByPage(String title, int pageNum, int pageSize) {

        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(title)) {
            queryWrapper.like("title", title);
        }
        return articleMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
    }

}
