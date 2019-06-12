package com.yl.chat.dao;

import com.yl.chat.wecaht.model.User;
import java.util.List;

/**
 * 存储微信用户信息
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */

public interface UserDao {
    /**
     * 存储微信用户信息
     */
    void saveWechat(User user);

    /**
     * 根据openid查询是否授权过
     */
    User findUserOpenid(String openid);

    List<User> findOpenIdByCustomerNo(String customerNo);

    /**
     * 修改微信用户信息
     *
     * @param user
     */
    void updateWechat(User user);
}