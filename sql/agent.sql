/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2011-05-26 17:01:33                          */
/*==============================================================*/


drop procedure if exists day_report;

drop table if exists acct;

drop table if exists acct_day_report;

drop table if exists acct_frozen_log;

drop index idx_acct_log on acct_trans_log;

drop table if exists acct_trans_log;

drop table if exists agent;

drop table if exists auth;

drop table if exists buycard_order;

drop table if exists card;

drop index idx_data_dict on data_dict;

drop table if exists data_dict;

drop table if exists menu;

drop table if exists operate_log;

drop table if exists recharge_order;

drop table if exists user;

/*==============================================================*/
/* Table: acct                                                  */
/*==============================================================*/
create table acct
(
   id                   varchar(20) not null comment '帐号',
   available_balance    numeric(13) comment '可用余额',
   frozen_balance       numeric(13) comment '冻结余额',
   deposit              numeric(13) comment '押金',
   status               char(1) comment '状态 0=正常 1=冻结 2=注销 3=未审核',
   primary key (id)
);

alter table acct comment '账户';

/*==============================================================*/
/* Table: acct_day_report                                       */
/*==============================================================*/
create table acct_day_report
(
   id                   int not null auto_increment comment '自增主键',
   acct_id              varchar(20) comment '帐号',
   trans_type           char(2) comment '交易类型',
   pay_amount           numeric(13) comment '支出金额',
   income_amount        numeric(13) comment '收入金额',
   trans_count          int comment '交易笔数',
   trans_date           date comment '交易日期',
   primary key (id)
);

alter table acct_day_report comment '账户日报 每天定时进行统计';

/*==============================================================*/
/* Table: acct_frozen_log                                       */
/*==============================================================*/
create table acct_frozen_log
(
   id                   int not null auto_increment comment '自增主键',
   acct_id              varchar(20) comment '帐号',
   frozen_amount        numeric(13) comment '冻结金额',
   frozen_type          char(2) comment '冻结类型',
   frozen_sn            varchar(20) comment '冻结流水',
   frozen_time          datetime comment '冻结时间',
   status               char(1) comment '状态 0=未解冻 1=已解冻',
   rmk                  varchar(50) comment '备注',
   primary key (id)
);

alter table acct_frozen_log comment '账户冻结日志';

/*==============================================================*/
/* Table: acct_trans_log                                        */
/*==============================================================*/
create table acct_trans_log
(
   id                   int not null auto_increment,
   acct_id              varchar(20) not null comment '帐号',
   pay_amount           numeric(13) not null default 0 comment '支出金额',
   income_amount        numeric(13) not null default 0 comment '收入金额',
   balance              numeric(13) not null comment '余额',
   trans_type           char(2) not null comment '交易类型',
   trans_sn             varchar(20) not null comment '交易流水号',
   trans_time           datetime not null comment '交易时间',
   rmk                  varchar(50) comment '备注',
   primary key (id)
);

alter table acct_trans_log comment '账户交易日志';

/*==============================================================*/
/* Index: idx_acct_log                                          */
/*==============================================================*/
create index idx_acct_log on acct_trans_log
(
   trans_time,
   acct_id,
   trans_type,
   trans_sn
);

/*==============================================================*/
/* Table: agent                                                 */
/*==============================================================*/
create table agent
(
   id                   varchar(20) not null comment '代理号码',
   name                 varchar(20) not null comment '名称',
   password             char(32) not null comment '密码',
   super_id             varchar(20) not null comment '上级代理商号码',
   rank                 char(1) not null comment '级别 0=顶级 1=高级 2=中级 3=初级',
   status               char(1) comment '状态 0=正常 1=冻结 2=注销 ',
   phone_no             varchar(20) comment '电话号码',
   mobile_no            varchar(20) comment '手机号码',
   id_card_no           varchar(20) comment '身份证号',
   qq                   varchar(20) comment 'qq号码',
   wangwang             varchar(20) comment '旺旺号码',
   taobao_shop_url      varchar(100) comment '淘宝店网址',
   paipai_shop_url      varchar(100) comment '拍拍店网址',
   reg_time             datetime comment '注册时间',
   approve_time         datetime comment '审核通过时间',
   primary key (id)
);

alter table agent comment '代理商';

/*==============================================================*/
/* Table: auth                                                  */
/*==============================================================*/
create table auth
(
   id                   int not null auto_increment comment '自增主键',
   role                 char(1) comment '角色 0=代理商  ',
   menu_id              int comment '菜单编号',
   primary key (id)
);

alter table auth comment '权限';

/*==============================================================*/
/* Table: buycard_order                                         */
/*==============================================================*/
create table buycard_order
(
   id                   int not null auto_increment comment '自增主键',
   order_sn             char(16) comment '订单流水号',
   agent_id             varchar(20) comment '代理号码',
   product_id           varchar(20) comment '商品编码',
   product_count        int comment '商品数量',
   product_price        int comment '商品单价',
   total_amount         numeric(13) comment '订单总金额',
   order_time           datetime comment '订单时间',
   status               char(1) comment '状态 0=初始 1=处理中 2=购卡成功 3=购卡失败',
   rmk                  varchar(100) comment '备注',
   primary key (id)
);

alter table buycard_order comment '购卡订单';

/*==============================================================*/
/* Table: card                                                  */
/*==============================================================*/
create table card
(
   id                   int not null auto_increment comment '自增主键',
   order_sn             char(16) comment '订单号',
   product_id           varchar(10) comment '商品编码',
   no                   varchar(50) comment '卡号',
   password             varchar(20) comment '密码',
   price                int comment '价格 精确到分',
   expire_date          date comment '过期日期',
   agent_id             varchar(20) comment '代理商号码',
   sale_status          char(1) comment '销售状态 0=未销售 1=销售中 2=已销售',
   recharge_status      char(1) comment '充值状态 0=未充值 1=已充值',
   buy_time             datetime comment '购入时间',
   sale_time            datetime comment '销售时间',
   recharge_time        datetime comment '充值时间',
   pay_orderno          varchar(50) comment '值充订单号',
   uid                  varchar(15) comment 'KC号码',
   paymoney             varchar(20) comment '充值金额',
   phone                varchar(20) comment '电话号码',
   primary key (id)
);

alter table card comment '卡信息';

/*==============================================================*/
/* Table: data_dict                                             */
/*==============================================================*/
create table data_dict
(
   id                   int not null auto_increment,
   category             varchar(10) comment '类别',
   property             varchar(10) comment '键',
   value                varchar(50) comment '值',
   primary key (id)
);

alter table data_dict comment '数据字典';

/*==============================================================*/
/* Index: idx_data_dict                                         */
/*==============================================================*/
create unique index idx_data_dict on data_dict
(
   category,
   property
);

/*==============================================================*/
/* Table: menu                                                  */
/*==============================================================*/
create table menu
(
   id                   int not null,
   name                 varchar(30) comment '菜单名称',
   url                  varchar(50) comment '地址',
   category             char(1) comment '类别 0=代理商菜单 1=用户菜单',
   rank                 char(1) comment '级别 1=一级菜单 2=二级菜单 3=三级菜单',
   super_id             int comment '上级菜单id',
   visible              char(1) comment '是否可见 Y=可见 N=不可见',
   primary key (id)
);

alter table menu comment '菜单';

/*==============================================================*/
/* Table: operate_log                                           */
/*==============================================================*/
create table operate_log
(
   id                   int not null auto_increment comment '自增主键',
   login_id             varchar(20) comment '代理商、用户id',
   ip                   varchar(15) comment 'ip地址',
   menu_id              int comment '菜单id',
   category             char(2) comment '操作类别 00=登录 01=注销 02=提卡 03=售卡 04=充值 05=授信',
   operate_detail       varchar(100) comment '操作详情',
   operate_time         datetime comment '操作时间',
   primary key (id)
);

alter table operate_log comment '操作日志';

/*==============================================================*/
/* Table: recharge_order                                        */
/*==============================================================*/
create table recharge_order
(
   id                   int not null auto_increment comment '自增主键',
   order_sn             char(16) comment '订单流水号',
   agent_id             varchar(20) comment '代理号码',
   bank_id              char(2) comment '银行编码',
   total_amount         numeric(13) comment '订单总金额',
   order_time           datetime comment '订单时间',
   status               char(1) comment '状态 0=初始 1=处理中 2=充值成功 3=充值失败',
   rmk                  varchar(100) comment '备注',
   primary key (id)
);

alter table recharge_order comment '充值订单';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   varchar(20) not null comment 'id',
   name                 varchar(20) comment '名称',
   password             char(32) comment '密码',
   role                 char(1) comment '角色 1=拓展员 2=监察员 3=管理员',
   primary key (id)
);

alter table user comment '用户';

 
 
DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  content varchar(2000) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;


/** 卡密加密保存 **/
/** 增加状态字段 0=正常 1=已冻结 **/
alter table card add status char(1) default 0;
alter table card add despwd varchar(50);
create index idx_card_pwd on card (password);
create index idx_card_despwd on card (despwd);



/** 增加brand_id字段 **/
alter table acct 
   add column brand_id varchar(20) NOT NULL after id;
   
alter table acct_day_report 
   add column brand_id varchar(20) NOT NULL after id;
   
alter table acct_frozen_log 
   add column brand_id varchar(20) NOT NULL after id;
   
alter table acct_trans_log 
   add column brand_id varchar(20) NOT NULL after id;
   
alter table agent 
   add column brand_id varchar(20) NOT NULL after id;   
   
alter table bcdr 
   add column brand_id varchar(20) NOT NULL after id;   
   
alter table buycard_order 
   add column brand_id varchar(20) NOT NULL after id;
   
alter table card 
   add column brand_id varchar(20) NOT NULL after id;
   
alter table recharge_order 
   add column brand_id varchar(20) NOT NULL after id;   
   
alter table notice 
   add column brand_id varchar(20) NOT NULL after id;   
