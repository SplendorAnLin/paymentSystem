-- auto Generated on 2017-12-03 17:21:03 
-- DROP TABLE IF EXISTS owner_rate; 
CREATE TABLE owner_rate(
	owner_role VARCHAR (50) NOT NULL COMMENT '所有者角色',
	owner_id VARCHAR (50) NOT NULL COMMENT '所有者编号',
	owner_rate_type VARCHAR (50) NOT NULL COMMENT '商户费率类型',
	rate_code VARCHAR (50) NOT NULL COMMENT '费率编号或者模板编号，跟商户费率类型配合使用',
	status VARCHAR (50) NOT NULL COMMENT '费率状态',
	id BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
	code VARCHAR (50) NOT NULL COMMENT '编号',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'owner_rate';
