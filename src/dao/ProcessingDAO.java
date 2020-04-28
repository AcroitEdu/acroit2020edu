package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProcessingDAO {

	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://172.19.71.32/zaiko2020";
	private static final String dbuser = "root";
	private static final String dbpass = "";

	/***** 入荷処理 *****/
	public void arrival(int[] i, int[] j) {
		Connection conn = null;

		// 接続先DB、ユーザー名、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			for (int s = 0; s <= i.length -1; s++) {
				// goodsテーブルのquantityに加算した値を更新する
				String sql = "UPDATE books SET stock=? WHERE id=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				// 値の設定
				pStmt.setInt(1, i[s]);
				pStmt.setInt(2, j[s]);
				// UPDATEを実行
				pStmt.executeUpdate();
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
		return;
	}

	/***** 出荷処理 *****/
	public void shipment(int[] i, String[] j) {
		Connection conn = null;

		// 接続先DB、ユーザー名、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			for (int s = 0; i[s] != 0 && j[s] != null; s++) {
				// goodsテーブルのquantityから減算した値を更新する
				String sql = "UPDATE goods SET quantity = quantity-? WHERE goods_name=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				// 値の設定
				pStmt.setInt(1, i[s]);
				pStmt.setString(2, j[s]);
				// updateを実行
				pStmt.executeUpdate();
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
		return;
	}

}