-- auto Generated on 2017-12-03 17:19:45
-- DROP TABLE IF EXISTS combination_rate;
CREATE TABLE combination_rate (
  id             BIGINT(15)    NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  code           VARCHAR(50)   NOT NULL
  COMMENT '编号',
  create_time    DATETIME      NOT NULL
  COMMENT '创建时间',
  rate_rule_code VARCHAR(50)   NOT NULL
  COMMENT '费率规则表',
  fee            DOUBLE(16, 4) NOT NULL
  COMMENT '费率',
  compute_mode   VARCHAR(50)   NOT NULL
  COMMENT '计算方式',
  max_fee        DOUBLE(16, 4) NOT NULL
  COMMENT '最大费率',
  min_fee        DOUBLE(16, 4) NOT NULL
  COMMENT '最小费率',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT 'combination_rate';
-- auto Generated on 2017-12-03 17:21:03
-- DROP TABLE IF EXISTS owner_rate;
CREATE TABLE owner_rate (
  id              BIGINT(15)  NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  code            VARCHAR(50) NOT NULL
  COMMENT '编号',
  create_time     DATETIME    NOT NULL
  COMMENT '创建时间',
  owner_role      VARCHAR(50) NOT NULL
  COMMENT '所有者角色',
  owner_id        VARCHAR(50) NOT NULL
  COMMENT '所有者编号',
  owner_rate_type VARCHAR(50) NOT NULL
  COMMENT '商户费率类型',
  rate_code       VARCHAR(50) NOT NULL
  COMMENT '费率编号或者模板编号，跟商户费率类型配合使用',
  status          VARCHAR(50) NOT NULL
  COMMENT '费率状态',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT 'owner_rate';
-- auto Generated on 2017-12-03 17:17:04
-- DROP TABLE IF EXISTS rate;
CREATE TABLE rate (
  id          BIGINT(15)  NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  code        VARCHAR(50) NOT NULL
  COMMENT '编号',
  create_time DATETIME    NOT NULL
  COMMENT '创建时间',
  sub_product VARCHAR(50) NOT NULL
  COMMENT '是否使用子产品费率',
  status      VARCHAR(50) NOT NULL
  COMMENT '费率状态',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT 'rate';
-- auto Generated on 2017-12-03 17:22:10
-- DROP TABLE IF EXISTS rate_rule;
CREATE TABLE rate_rule (
  id            BIGINT(15)  NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  code          VARCHAR(50) NOT NULL
  COMMENT '编号',
  create_time   DATETIME    NOT NULL
  COMMENT '创建时间',
  rate_code     VARCHAR(50) NOT NULL
  COMMENT '费率编号',
  fee_type      VARCHAR(50) NOT NULL
  COMMENT '费率类型',
  comp_fee_type VARCHAR(50) NOT NULL
  COMMENT '计费类型',
  product_type  VARCHAR(50) NOT NULL
  COMMENT '产品类型',
  product_code  VARCHAR(50) NOT NULL
  COMMENT '产品编号',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT 'rate_rule';
-- auto Generated on 2017-12-03 17:23:04
-- DROP TABLE IF EXISTS rate_template;
CREATE TABLE rate_template (
  id           BIGINT(15)  NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  code         VARCHAR(50) NOT NULL
  COMMENT '编号',
  create_time  DATETIME    NOT NULL
  COMMENT '创建时间',
  name         VARCHAR(50) NOT NULL
  COMMENT '模板名称',
  status       VARCHAR(50) NOT NULL
  COMMENT '模板状态',
  rate_code    VARCHAR(50) NOT NULL
  COMMENT '费率编号',
  default_rate VARCHAR(50) NOT NULL
  COMMENT '是否是默认模板',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT 'rate_template';
