package model;

public class LoginUser {

	private String userid;
	private String pass;
	private boolean db_flag = true;

	public LoginUser(String userid, String pass) {
		this.userid = userid;
		this.pass = pass;
	}

	public String getUserid() {
		return userid;
	}

	public String getPass() {
		return pass;
	}
	
	public boolean isDb_flag() {
		return db_flag;
	}

	public void setDb_flag(boolean db_flag) {
		this.db_flag = db_flag;
	}

}