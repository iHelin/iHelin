package me.ianhe.isite.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * CommonRedisDao
 *
 * @author iHelin
 * @create 2017-04-11 15:07
 */
@Repository
public class CommonRedisDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取String类型值
     *
     * @author iHelin
     * @since 2017/8/19 15:01
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置String类型值
     *
     * @author iHelin
     * @since 2017/8/19 15:01
     */
    public void set(String key, String val) {
        stringRedisTemplate.opsForValue().set(key, val);
    }

    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 取得Integer值
     *
     * @author iHelin
     * @since 2017/9/16 17:17
     */
    public Integer getInt(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNumeric(value)) {
            return Integer.valueOf(value);
        }
        return 0;
    }

    /**
     * 获取long类型
     *
     * @author iHelin
     * @since 2017/9/16 17:17
     */
    public Long getLong(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNumeric(value)) {
            return Long.valueOf(value);
        }
        return 0L;
    }

    /**
     * 获取
     *
     * @param retain 是否保留
     * @author iHelin
     * @since 2017/9/16 17:17
     */
    public String get(String key, boolean retain) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            stringRedisTemplate.delete(key);
        }
        return value;
    }

    /**
     * 递增操作
     *
     * @author iHelin
     * @since 2017/9/16 17:16
     */
    public double incr(String key, double by) {
        return stringRedisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 递增操作
     *
     * @author iHelin
     * @since 2017/9/16 17:17
     */
    public Long incr(String key, long by) {
        return stringRedisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 递增1
     * key不存在时默认为0，返回1
     *
     * @author iHelin
     * @since 2017/9/16 17:16
     */
    public Long incr(String key) {
        return stringRedisTemplate.boundValueOps(key).increment(1L);
    }

    /**
     * 将map写入缓存
     *
     * @author iHelin
     * @since 2017/9/16 17:16
     */
    public <T> void setMap(String key, Map<String, T> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @author iHelin
     * @since 2017/9/16 17:16
     */
    public void addMap(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取map缓存
     *
     * @author iHelin
     * @since 2017/9/16 17:16
     */
    public <T> Map<String, T> mget(String key, Class<T> clazz) {
        BoundHashOperations<String, String, T> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }


    /**
     * 获取map缓存中的某个对象
     *
     * @author iHelin
     * @since 2017/9/16 17:16
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field) {
        return (T) stringRedisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 删除map中的某个对象
     *
     * @author iHelin
     * @since 2017/9/16 17:15
     */
    public void delMapField(String key, Object... field) {
        BoundHashOperations<String, String, Object> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    /**
     * 指定缓存的失效时间
     *
     * @author iHelin
     * @since 2017/9/16 17:15
     */
    public void setExpire(String key, long timeout) {
        if (timeout > 0) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 添加set
     *
     * @author iHelin
     * @since 2017/9/16 17:15
     */
    public void addSet(String key, String... values) {
        stringRedisTemplate.boundSetOps(key).add(values);
    }

    /**
     * 删除Set集合中的对象
     *
     * @author iHelin
     * @since 2017/9/16 17:15
     */
    public void delSet(String key, Object... value) {
        stringRedisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * Set重命名
     *
     * @author iHelin
     * @since 2017/9/16 17:15
     */
    public void renameSet(String oldKey, String newKey) {
        stringRedisTemplate.boundSetOps(oldKey).rename(newKey);
    }

    /**
     * 检查key是否存在
     *
     * @author iHelin
     * @since 2017/9/16 17:15
     */
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置值和过期时间
     *
     * @author iHelin
     * @since 2017/8/17 16:32
     */
    public void setTimeout(String key, String value, Long timeout, TimeUnit unit) {
        if (value == null) {
            return;
        }
        if (timeout != null) {
            stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
        } else {
            set(key, value);
        }
    }

}
