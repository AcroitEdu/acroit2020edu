package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.LoginUser;

public class AuthenticationDAO {
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://172.19.71.32/zaiko2020";
	private static final String dbuser = "root";
	private static final String dbpass = "";

	/***** 認証 *****/
	public LoginUser findLogin(LoginUser loginUser) {
		Connection conn = null;
		LoginUser user = null;

		// 接続先DB、ユーザーID、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			// SELECT文
			String sql = "SELECT *  FROM user WHERE name = ? AND pass = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// 値の設定
			pStmt.setString(1, loginUser.getUserid());
			pStmt.setString(2, loginUser.getPass());
			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				// 結果表からデータを取得
				String userid = rs.getString("id");
				String pass = rs.getString("pass");
				user = new LoginUser(userid, pass);
			}

			conn.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // JDBCドライバが見つからないときの処理
		} catch (SQLException e) {
			e.printStackTrace(); // 接続やSQL処理の失敗時の処理
		} finally {
			// DBの切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// 切断失敗時の処理
					e.printStackTrace();
				}
			}
		}
		return user;
	}

	/***** useridのみ抽出 *****/
	public int LoginId(String userid) {
		Connection conn = null;
		int id = 0;
		// 接続先DB、ユーザー名、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			String sql = "SELECT id FROM user WHERE userid=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// 値の設定
			pStmt.setString(1, userid);
			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // JDBCドライバが見つからないときの処理
		} catch (SQLException e) {
			e.printStackTrace(); // 接続やSQL処理の失敗時の処理
		} finally {
			// DBの切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// 切断失敗時の処理
					e.printStackTrace();
				}
			}
		}
		return id;
	}

}