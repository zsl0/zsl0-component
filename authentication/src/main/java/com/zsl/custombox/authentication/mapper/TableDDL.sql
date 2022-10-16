-- 用户信息表和auth信息合并，可考虑拆分
create table user_info(
	`id` bigint primary key auto_increment comment '主键',
	`username` varchar(32) comment '用户名',
	`account_name` varchar(32) comment '账号名称',
	`password` varchar(255) comment '密码',
	`email` varchar(32) comment '邮箱',
	`phone` varchar(32) comment '手机号',

	`login_type` tinyint(2) comment '登录类型（0：密码；1：手机号；2：邮箱）',
	`login_time` datetime comment '登录时间',
	`access_token` varchar(255) comment '访问Token',
	`refresh_token` varchar(255) comment '刷新Token',
	`status` tinyint(2) comment '状态',
	`admin` tinyint(1) comment '0：普通用户；1：管理员',
	`deleted` tinyint(1) comment '0：未删除；1：被删除',

	`insert_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
	`update_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) comment '用户信息表（和auth信息合并，可考虑拆分）';

-- user_info索引
create index username_index on user_info(username);
create index email_index on user_info(email);
create index phone_index on user_info(phone);

-- 用户详情信息表
create table user_details_info(
	`id` bigint primary key auto_increment comment '主键',
	`user_id` bigint comment 'user_info表主键',
	`gender` tinyint(2) comment '性别',
	`country` varchar(32) comment '国家',
	`province` varchar(32) comment '省',
	`city` varchar(32) comment '市',
	`addr_details` varchar(128) comment '地址详情',
	`birthday` datetime comment '出生日期',
	`realname` varchar(16) comment '真实名字',


	`insert_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
	`update_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) comment '用户详情信息表';

-- user_id索引
create index user_id_index on user_details_info(user_id);

-- 角色表
create table role(
	`id` bigint primary key auto_increment comment '主键',
	`role_name` varchar(32) comment '角色名称',

	`insert_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
	`update_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) comment '角色表';

create index role_name_index on role(role_name);

-- 菜单权限表
create table menu(
	`id` bigint primary key auto_increment comment '主键',
	`menu_name` varchar(32) comment '权限名称',
	`parent_id` bigint comment '0:表示顶级菜单',

	`insert_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
	`update_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) comment '菜单权限表';

-- 角色权限关联表
create table role_menu(
	`role_id` bigint comment 'role表主键',
  `menu_id` bigint comment 'menu表主键'
) comment '角色权限关联表';

-- 用户角色关联表
create table user_role(
	`user_id` bigint comment 'user_info表主键',
  `role_id` bigint comment 'role表主键'
) comment '用户角色关联表';

-- 操作日志
create table operation_log(
	`id` bigint primary key auto_increment comment '主键',
	`user_id` bigint comment '用户id(0: 无需登录接口)',

	`datetime` datetime comment '数据时间',
	`content` varchar(255) comment '信息',
	`host` varchar(64) comment '主机',
	`uri` varchar(255) comment '统一资源标识符（Uniform Resource Identifier）',

	`insert_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
	`update_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) comment '操作日志';

create index user_id_datetime_index on operation_log(user_id, datetime);