package top.itning.qq.db;

import java.util.Date;

public class User {
	
	private int id;
	private String userName;
	private String password;
	private int permission;
	private String wbUserName;
	private String wbPassword;
	private String wbContent;
	private Date endDate;
	private String wbUserId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public String getWbUserName() {
		return wbUserName;
	}
	public void setWbUserName(String wbUserName) {
		this.wbUserName = wbUserName;
	}
	public String getWbPassword() {
		return wbPassword;
	}
	public void setWbPassword(String wbPassword) {
		this.wbPassword = wbPassword;
	}
	public String getWbContent() {
		return wbContent;
	}
	public void setWbContent(String wbContent) {
		this.wbContent = wbContent;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getWbUserId() {
		return wbUserId;
	}
	public void setWbUserId(String wbUserId) {
		this.wbUserId = wbUserId;
	}
	
}
