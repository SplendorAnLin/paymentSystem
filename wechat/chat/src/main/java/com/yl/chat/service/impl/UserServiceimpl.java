package com.yl.chat.service.impl;

import com.yl.chat.dao.UserDao;
import com.yl.chat.service.UserService;
import com.yl.chat.wecaht.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceimpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceimpl.class);

    @Resource
    private UserDao userDao;

    @Override
    public void saveWechat(User user) {
        try {
            userDao.saveWechat(user);
        } catch (Exception e) {
            logger.error("微信存储用户信息异常", e);
        }
    }

    @Override
    public User findUserOpenid(String openid) {
        return userDao.findUserOpenid(openid);
    }

    public List<User> findOpenIdByCustomerNo(String customerNo) {
        return userDao.findOpenIdByCustomerNo(customerNo);
    }

    @Override
    public void updateWechat(User user) {
        userDao.updateWechat(user);
    }

}