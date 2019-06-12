-- auto Generated on 2017-12-03 17:19:45 
-- DROP TABLE IF EXISTS combination_rate; 
CREATE TABLE combination_rate(
	rate_rule_code VARCHAR (50) NOT NULL COMMENT '费率规则表',
	fee DOUBLE (16,4) NOT NULL COMMENT '费率',
	compute_mode VARCHAR (50) NOT NULL COMMENT '计算方式',
	max_fee DOUBLE (16,4) NOT NULL COMMENT '最大费率',
	min_fee DOUBLE (16,4) NOT NULL COMMENT '最小费率',
	id BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
	code VARCHAR (50) NOT NULL COMMENT '编号',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'combination_rate';
