package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Book;


public class BookEditDAO{
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://172.19.71.32/zaiko2020";
	private static final String dbuser = "root";
	private static final String dbpass = "";
	
	/***** 商品情報編集 *****/
	public void booksUpdate(List<Book> bookList) {
		Connection conn = null;

		// 接続先DB、ユーザー名、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);
			for(Book targetBook :bookList) {
				// booksテーブルの値を変更する
				String sql = "UPDATE books SET title=?, author=?, salesDate=?, isbn=?, price=?, stock=? WHERE id = ?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				// 値の設定
				pStmt.setString(1, targetBook.getTitle());
				pStmt.setString(2, targetBook.getAuthor());
				pStmt.setString(3, targetBook.getSalesDate());
				pStmt.setString(4, targetBook.getIsbn());
				pStmt.setInt(5, targetBook.getPrice());
				pStmt.setInt(6, targetBook.getStock());
				pStmt.setInt(7, targetBook.getId());
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

	public void booksInsert(List<Book> bookList) {
		Connection conn = null;

		// 接続先DB、ユーザー名、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);
			for(Book targetBook :bookList) {
				// booksテーブルの値を変更する
				String sql = "INSERT INTO books (id,title,author,salesDate,isbn,price,stock,deleteflg) VALUES (?,?,?,?,?,?,?,?)";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				// 値の設定
				pStmt.setInt(1, targetBook.getId());
				pStmt.setString(2, targetBook.getTitle());
				pStmt.setString(3, targetBook.getAuthor());
				pStmt.setString(4, targetBook.getSalesDate());
				pStmt.setString(5, targetBook.getIsbn());
				pStmt.setInt(6, targetBook.getPrice());
				pStmt.setInt(7, targetBook.getStock());
				pStmt.setBoolean(8, targetBook.isDeleteflg());
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
