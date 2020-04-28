package model;

import java.io.Serializable;

public class User implements Serializable {

	private int id;
	private String pass;
	private boolean db_flag = true;

	// ユーザー情報一覧表示
	public User(int id, String pass) {
		super();
		this.id = id;
		this.pass = pass;
	}

	public User() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getId() {
		return id;
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