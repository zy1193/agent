package com.keicei.agent.constant;

public interface MenuId {

	int LOGIN = 0;// 登录
	int LOGOUT = 1;// 登出
	int BUYC_ARD = 2;// 购卡
	int LIST_CARD = 3;// 列出卡
	int SHOW_CARD = 4; // 查卡
	int CARD_STATUS = 5;// 显示卡状态
	int CHANGE_CARD_STATUS = 6; // 修改卡状态
	int PROVIDE = 7; // 授信
	int ADMIN_PROVIDE = 8; // 后台授信
	int TRACE = 9;// 卡追踪
	int CARD_FROZEN_QUERY=10;//卡冻结查询
	int CARD_FROZEN=11;//卡冻结
	int CARD_THAW=12;//卡解冻
	int USER_RECHARGE = 13;// 代充
	int USER_RECHARGE_QUERY = 14;// 代充_查询用户
	int USER_RECHARGE_QUERY_LIST = 15;// 代充_查询用户_充值记录
}
