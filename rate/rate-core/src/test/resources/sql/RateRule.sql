-- auto Generated on 2017-12-03 17:22:10 
-- DROP TABLE IF EXISTS rate_rule; 
CREATE TABLE rate_rule(
	rate_code VARCHAR (50) NOT NULL COMMENT '费率编号',
	fee_type VARCHAR (50) NOT NULL COMMENT '费率类型',
	comp_fee_type VARCHAR (50) NOT NULL COMMENT '计费类型',
	product_type VARCHAR (50) NOT NULL COMMENT '产品类型',
	product_code VARCHAR (50) NOT NULL COMMENT '产品编号',
	id BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
	code VARCHAR (50) NOT NULL COMMENT '编号',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'rate_rule';
