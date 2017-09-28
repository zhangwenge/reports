package cn.com.dplus.report.entity;

import cn.com.dplus.project.entity.BaseEntity;


/**
 * 
 *  @类功能:	TODO	这里封装了用户的基础信息
 *	@文件名:	UserInfo.java
 * 	@所在包:	cn.com.dplus.user.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年8月19日下午3:07:33
 *	@公_司:		广州讯动网络科技有限公司
 */
public class UserInfo extends BaseEntity{

	private static final long serialVersionUID = 6517317173478040343L;

	/** 用户的id  */
	private String id;
	
	/** 用户名 */
	private String userName;
	
	/** 密码 */
	private String password;
	
	/** 密码的盐值  */
	private String passwordSalt;
	
	/** 注册时间 */
	private int createdAt;
	
	/** 更新时间 */
	private int updatedAt;
	
	/** 是否被锁定  */
	private int isLocked;
	
	/** 被锁定的时间  */
	private int lockedAt;
	
	/** 是否需要获取密码  */
	private boolean needLoadPassword;
	
	private String avatars;
	
	/** 公司名称 */
	private String company;
	
	/** 联系人 */
	private String contact;
	
	/** 电话 */
	private String phone;
	
	/** QQ */
	private String qq;
	
	public UserInfo() {
	}
	public UserInfo(String userId) {
		this.id = userId;
	}
	
	public UserInfo(String userId, String userName) {
		this.id = userId;
		this.userName = userName;
	}
	
	public UserInfo(String userId, String userName, boolean needLoadPassword) {
		this.id = userId;
		this.userName = userName;
		this.needLoadPassword = needLoadPassword;
	}
	
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	public int getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}
	public int getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(int updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public boolean isNeedLoadPassword() {
		return needLoadPassword;
	}
	public void setNeedLoadPassword(boolean needLoadPassword) {
		this.needLoadPassword = needLoadPassword;
	}
	public int getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}
	public int getLockedAt() {
		return lockedAt;
	}
	public void setLockedAt(int lockedAt) {
		this.lockedAt = lockedAt;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getAvatars() {
		return avatars;
	}
	public void setAvatars(String avatars) {
		this.avatars = avatars;
	}
}
