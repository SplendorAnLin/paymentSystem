-- auto Generated on 2017-12-03 17:23:04 
-- DROP TABLE IF EXISTS rate_template; 
CREATE TABLE rate_template(
	name VARCHAR (50) NOT NULL COMMENT '模板名称',
	status VARCHAR (50) NOT NULL COMMENT '模板状态',
	rate_code VARCHAR (50) NOT NULL COMMENT '费率编号',
	default_rate VARCHAR (50) NOT NULL COMMENT '是否是默认模板',
	id BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
	code VARCHAR (50) NOT NULL COMMENT '编号',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'rate_template';
