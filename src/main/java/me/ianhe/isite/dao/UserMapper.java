package me.ianhe.isite.dao;

import me.ianhe.isite.entity.User;
import me.ianhe.isite.model.SecurityUser;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByOpenId(String openId);

    User selectByPhone(String phone);

    List<User> listUserByCondition(Map<String, Object> res, RowBounds rowBounds);

    int listUserCount(Map<String, Object> res);

    SecurityUser selectByUsername(String username);

}