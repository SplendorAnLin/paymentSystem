package com.yl.customer.dao;

import java.util.List;

import com.yl.customer.entity.BulletinRead;

/**
 * 公告读取数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public interface BulletinReadDao
{
    public void persist(BulletinRead bulletinRead);
    public void remove(BulletinRead bulletinRead);
    public List<BulletinRead> getBySysCode(String sysCode,Long id);
}
