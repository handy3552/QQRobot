package qq.moni;

public class QQConstants {

	/**
	 * 获取好友
	 */
	public static final String URL_GET_FRIEND_INFO = "http://s.web2.qq.com/api/get_user_friends2";
	/**
	 * 陌生人
	 */
	public static final String URL_GET_STRANGER_INFO = "http://s.web2.qq.com/api/get_stranger_info2";
	/**
	 * 获取群
	 */
	public static final String URL_GET_GROUP_NAME_LIST = "http://s.web2.qq.com/api/get_group_name_list_mask2";
	
	/**
	 * 发送消息给好友
	 */
	public static final String URL_SEND_FRIEND = "http://d1.web2.qq.com/channel/send_buddy_msg2";
	/**
	 * 发送消息给群
	 */
	public static final String URL_SEND_GROUP = "http://d1.web2.qq.com/channel/send_qun_msg2";
	/**
	 * 发送消息给讨论组
	 */
	public static final String URL_SEND_DISCU = "http://d1.web2.qq.com/channel/send_discu_msg2";
	/**
	 * 发消息给好友、群发、讨论组时用的referer
	 */
	public static final String URL_SEND_REFERER = "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";
	
}
