package com.yl.recon.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 * @author AnLin
 * @since 2017/6/21
 */
@Data
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = -346935695810614182L;
	/**
     * id
     */
    private Long code;
    /**
     * 版本号
     */
    private Long version;
    /**
     * 创建时间
     */
    private Date createTime;


}
