package com.yl.recon.core.handler;

import java.util.Date;
import java.util.List;

/**
 * 对账处理器
 *
 * @author AnLin
 * @since 2017/6/22
 */
public interface ReconHandler {


  
    /**
     * 数据库对账
	 * @param reconDate
 	 */
    public void executeByDb(Date reconDate);
}
