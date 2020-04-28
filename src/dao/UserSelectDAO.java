package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserSelectDAO {

	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://172.19.71.32/zaiko2020";
	private static final String dbuser = "root";
	private static final String dbpass = "";

	/***** ユーザー情報一覧表示 *****/
	public List<User> findUlist() {
		Connection conn = null;
		List<User> userList = new ArrayList<User>();
		boolean dbError = false;

		// 接続先DB、ユーザーID、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			// userテーブルからid,userid,pass,level,flagをidの昇順で取得
			String sql = "SELECT id,pass FROM user ORDER BY id";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();
			dbError = true;

			// userテーブルの情報がなくなるまでテーブルの情報を取得
			while (rs.next()) {
				int id = rs.getInt("id");
				String pass = rs.getString("pass");
				User user = new User(id, pass);
				userList.add(user);
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
		// DBに接続している場合userListを返す
		if (dbError) {
			return userList;

		} else { // DBに接続できていない場合dbErrorListを返す
			List<User> dbErrorList = new ArrayList<User>();
			User error = new User();
			error.setDb_flag(false);
			dbErrorList.add(error);

			return dbErrorList;
		}
	}

}