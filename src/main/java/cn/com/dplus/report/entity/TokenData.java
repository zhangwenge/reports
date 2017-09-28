package cn.com.dplus.report.entity;

import java.io.Serializable;
import java.util.Map;

import cn.com.dplus.project.entity.BaseEntity;

/**
 * 
 *  @类功能:	TODO	这里封装了
 *	@文件名:	TokenData.java
 * 	@所在包:	cn.com.dplus.accesslayer.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年8月19日下午1:17:27
 *	@公_司:		广州讯动网络科技有限公司
 */
public class TokenData extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -2963243313676193491L;

	/** 用户的id  */
	private String userId;
	
	/** 用户名 */
	private String userName;
	
	/** 用户登录的ip */
	private String loginIP;
	
	/** 当前的token 值 */
	private String token;
	
	/** 当前token的创建时间  */
	private long createTime;
	
	/** 当前token过期时间 */
	private long expireTime;
	
	/** 附加缓存的内容 */
	private Map<String, Object> data;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "TokenData [userId=" + userId + ", userName=" + userName
				+ ", loginIP=" + loginIP + ", token=" + token + ", expireTime="
				+ expireTime + ", data=" + data + "]";
	}



	/**
	 * 
	 *  @类功能:	TODO	这里封装的是用户上一次登陆的信息
	 *	@文件名:	TokenData.java
	 * 	@所在包:	cn.com.dplus.accesslayer.entity
	 *	@开发者:	黄先国
	 * 	@邮_件:     hsamgle@qq.com
	 *  @时_间:		2016年8月19日下午1:55:22
	 *	@公_司:		广州讯动网络科技有限公司
	 */
	public static class LastLoginInfo extends BaseEntity implements Serializable{
		
		private static final long serialVersionUID = 6808063950141216375L;

		/** 上一次登录的ip地址 */
		private String lastLoginIp;
		
		/** 上次的登录地 */
		private String lastLoginLoaction;
		
		/** 上一次登录时间 */
		private String lastLoginTime;

		public String getLastLoginIp() {
			return lastLoginIp;
		}

		public void setLastLoginIp(String lastLoginIp) {
			this.lastLoginIp = lastLoginIp;
		}

		public String getLastLoginLoaction() {
			return lastLoginLoaction;
		}

		public void setLastLoginLoaction(String lastLoginLoaction) {
			this.lastLoginLoaction = lastLoginLoaction;
		}

		public String getLastLoginTime() {
			return lastLoginTime;
		}

		public void setLastLoginTime(String lastLoginTime) {
			this.lastLoginTime = lastLoginTime;
		}

		@Override
		public String toString() {
			return "LastLoginInfo [lastLoginIp=" + lastLoginIp
					+ ", lastLoginLoaction=" + lastLoginLoaction
					+ ", lastLoginTime=" + lastLoginTime + "]";
		}
		
	}
	
}
