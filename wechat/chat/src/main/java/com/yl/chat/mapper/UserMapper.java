package com.yl.chat.mapper;

import com.yl.chat.dao.UserDao;
import com.yl.chat.wecaht.model.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 微信接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
public interface UserMapper extends UserDao {

    /**
     * 存储微信用户信息
     */
    void saveWechat(User user);

    /**
     * 根据openid查询是否授权过
     */
    User findUserOpenid(@Param("openid") String openid);

    /**
     * 根据商户号查询openId
     *
     * @param customerNo
     * @return
     */
    List<User> findOpenIdByCustomerNo(@Param("customerNo") String customerNo);
}