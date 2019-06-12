/**
 * 
 */
package com.yl.account.core.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.response.AccountFreezeDetailResponse;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.AccountFreezeDetail;

/**
 * 账务冻结明细数据映射接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public interface AccountFreezeDetailBaseMapper {
	
	List<AccountFreezeDetailResponse> findAccountFzBy(@Param("AccountFreezeDetailResponse") Map<String, Object> AccountFreezeDetailResponse,
			@Param("page") Page<?> page);

	/**
	 * @Description 持久化冻结明细
	 * @param accountFreezeDetail 冻结明细实体
	 * @see 需要参考的类或方法
	 */
	void create(AccountFreezeDetail accountFreezeDetail);

	/**
	 * @Description 账号、冻结状态获取冻结明细
	 * @param accountNo 账号
	 * @param freezeStatus 冻结状态
	 * @returnList<AccountFreezeDetail> 冻结明细
	 * @see 需要参考的类或方法
	 */
	List<AccountFreezeDetail> findAccountFreezeDetailBy(@Param("accountNo") String accountNo, @Param("freezeStatus") FreezeStatus freezeStatus);

	/**
	 * @Description 冻结金额累加发生额
	 * @param accountFreezeDetail 冻结明细
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int addFreezeBalance(@Param("accountFreezeDetail") AccountFreezeDetail accountFreezeDetail, @Param("newVersion") long newVersion);

	/**
	 * @Description 预冻完成
	 * @param accountFreezeDetail 冻结明细
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int preFreezeComplete(@Param("accountFreezeDetail") AccountFreezeDetail accountFreezeDetail, @Param("newVersion") long newVersion);

	/**
	 * @Description 冻结编号查询冻结明细
	 * @param freezeNo 冻结编号
	 * @return AccountFreezeDetail 冻结明细
	 * @see 需要参考的类或方法
	 */
	AccountFreezeDetail findAccountFreezeDetailByFreezeNo(String freezeNo);

	/**
	 * @Description 解冻完成
	 * @param accountFreezeDetail 冻结明细
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int thawComplete(@Param("accountFreezeDetail") AccountFreezeDetail accountFreezeDetail, @Param("newVersion") long newVersion);

	/**
	 * @Description 请款金额增加
	 * @param accountFreezeDetail 冻结明细
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int addConsultBalance(@Param("accountFreezeDetail") AccountFreezeDetail accountFreezeDetail, @Param("newVersion") long newVersion);

	/**
	 * @Description 批量查询需解冻数据
	 * @param page 分页实体
	 * @param date 当前系统日期
	 * @return List<AccountFreezeDetail> 冻结明细集
	 * @see 需要参考的类或方法
	 */
	List<AccountFreezeDetail> findAllAccountFreezeDetailsBy(@Param("page") Page<List<AccountFreezeDetail>> page, @Param("date") Date date);

	/**
	 * @Description 查询参数获取冻结明细信息
	 * @param queryParams 查询参数
	 * @return AccountFreezeDetail 冻结明细信息
	 * @see 需要参考的类或方法
	 */
	AccountFreezeDetail findAccountDetailBy(@Param("queryParams") Map<String, Object> queryParams);
	
	/**
	 * 查询冻结编号是否存在
	 * @param freezeNo
	 * @return
	 */
	int queryFreezeNoExists(@Param("freezeNo")String freezeNo);
}
