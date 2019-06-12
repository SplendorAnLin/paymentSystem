package com.yl.chat.dao.impl;

import com.yl.chat.dao.UserDao;
import com.yl.chat.mapper.UserMapper;
import com.yl.chat.wecaht.model.User;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;

/**
 * 微信接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Resource
    private UserMapper userMapper;

    @Override
    public void saveWechat(User user) {
        try {
            userMapper.saveWechat(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findUserOpenid(String openid) {
        return userMapper.findUserOpenid(openid);
    }

    public List<User> findOpenIdByCustomerNo(String openid) {
        return userMapper.findOpenIdByCustomerNo(openid);
    }

    @Override
    public void updateWechat(User user) {
        userMapper.updateWechat(user);
    }
}