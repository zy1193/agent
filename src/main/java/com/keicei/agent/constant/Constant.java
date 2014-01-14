package com.keicei.agent.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {

	public static final String AGENT_SESSION_NAME = "AGENT.SESSION.NAME";
	public static final String USER_SESSION_NAME = "USER.SESSION.NAME";
	public static final String MENU_SESSION_NAME = "USER.SESSION.MENU";
	public static final String MENULIST_SESSION_NAME = "USER.SESSION.MENULIST";
	public static final String ACCT_STATUS = "AGENT.ACCT.STATUS";

	public static final Map<String, String> ACCT_STATUS_DESC = new HashMap<String, String>(
			4);
	static {
		ACCT_STATUS_DESC.put("0", "正常");
		ACCT_STATUS_DESC.put("1", "冻结");
		ACCT_STATUS_DESC.put("2", "注销");
		ACCT_STATUS_DESC.put("3", "未审核");
	}

	public static final Map<String, String> AGENT_RANK_DESC = new HashMap<String, String>(
			4);
	static {
		AGENT_RANK_DESC.put("0", "顶级");
		AGENT_RANK_DESC.put("1", "高级");
		AGENT_RANK_DESC.put("2", "中级");
		AGENT_RANK_DESC.put("3", "初级");
	}

	public static final Map<String, String> AGENT_STATUS_DESC = new HashMap<String, String>(
			4);
	static {
		AGENT_STATUS_DESC.put("0", "正常");
		AGENT_STATUS_DESC.put("1", "冻结");
		AGENT_STATUS_DESC.put("2", "注销");
	}

	/** 购卡成功 **/
	public static final int RC_BUYCARD_SUCCESS = 0;
	/** 购卡 密码错误 **/
	public static final int RC_BUYCARD_PWD_ERR = 1;
	/** 购卡 余额不足 **/
	public static final int RC_BUYCARD_BAL_LESS = 2;
	/** 购卡 远程调用失败 **/
	public static final int RC_BUYCARD_RPC_ERR = 3;
	/** 购卡 其他错误 **/
	public static final int RC_BUYCARD_OTHER = 4;
	
	
	/** 代充成功 **/
	public static final int RC_USERRECHARGE_SUCCESS = 0;
	/** 代充 密码错误 **/
	public static final int RC_USERRECHARGE_PWD_ERR = 1;
	/** 代充 余额不足 **/
	public static final int RC_USERRECHARGE_BAL_LESS = 2;
	/** 代充 远程调用失败 **/
	public static final int RC_USERRECHARGE_RPC_ERR = 3;
	/** 代充 其他错误 **/
	public static final int RC_USERRECHARGE_OTHER = 4;
	

	public static final String[] RC_BUYCARD_DESC = { "成功", "密码错误", "账户余额不足",
			"内部错误-远程调用提卡接口失败", "内部错误-系统故障" };

	public static final String[] RC_USERRECHARGE_DESC = { "成功", "密码错误", "账户余额不足",
		"内部错误-远程调用充值接口失败", "内部错误-系统故障" };
	
	/** 冻结状态 0=未解冻 1=已解冻 **/
	public static final String FROZEN_STATUS_UNTHAW = "0",
			FROZEN_STATUS_THAW = "1";

	/** 账户冻结类型 - 01=购卡 04用户代充值 **/
	public static final String FROZEN_TYPE_BUYCARD = "01";
	public static final String FROZEN_TYPE_USERRECHARGE = "04";

	/** 购卡订单表状态 0=初始 1=处理中 2=购卡成功 3=购卡失败 **/
	public static final String BUYCARD_ORDER_STATUS_INIT = "0",
			BUYCARD_ORDER_STATUS_PROCESSING = "1",
			BUYCARD_ORDER_STATUS_SUCCESS = "2",
			BUYCARD_ORDER_STATUS_FAIL = "3";
	
	/** 代充表状态 0=初始 1=处理中 2=代充成功 3=代充失败 **/
	public static final String USERRECHARGE_ORDER_STATUS_INIT = "0",
			USERRECHARGE_ORDER_STATUS_PROCESSING = "1",
			USERRECHARGE_ORDER_STATUS_SUCCESS = "2",
			USERRECHARGE_ORDER_STATUS_FAIL = "3";
	

	/** 账户交易类型 01=购卡 **/
	public static final String ACCT_TRANS_TYPE_BUYCARD = "01";
	/** 账户交易类型 02=授信 **/
	public static final String ACCT_TRANS_TYPE_PROVIDE = "02";
	/** 账户交易类型 03=接收授信 **/
	public static final String ACCT_TRANS_TYPE_PROVIDE_RECEIVE = "03";
	/** 账户交易类型 04=代用户充值 **/
	public static final String ACCT_TRANS_TYPE_USERRECHARGE = "04";

	/** 充值卡充值状态 0=未充值 1=已充值 */
	public static final String CARD_RECHARGE_STATUS_UNRECHARGE = "0",
			CARD_RECHARGE_STATUS_RECHARGE = "1";

	/** 充值卡销售状态 0=为销售 1=销售中 2=已销售 */
	public static final String CARD_SALE_STATUS_UNSALE = "0",
			CARD_SALE_STATUS_SALING = "1", CARD_SALE_STATUS_SALED = "2";
	public static final String CARD_STATUS_NORMAL = "0";
	
	/** 不同系统默认的联盟ID **/
	public static final Map<String, String> INVITEBY_MAP = new HashMap<String, String>(
			3);
	static{
		INVITEBY_MAP.put("sky", "1333");
		INVITEBY_MAP.put("3g", "178");
		INVITEBY_MAP.put("efl", "349");
	}

}
