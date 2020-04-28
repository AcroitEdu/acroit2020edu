package model;

import dao.AuthenticationDAO;

public class LoginLogic {
	public boolean execute(LoginUser loginUser) {
		AuthenticationDAO dao = new AuthenticationDAO();
		LoginUser user = dao.findLogin(loginUser);
		return user != null;
	}

	public LoginUser Save(LoginUser loginUser) {
		AuthenticationDAO dao = new AuthenticationDAO();
		LoginUser user = dao.findLogin(loginUser);
		return user;
	}
}