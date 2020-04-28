package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Book;

public class BookSelectDAO {

	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://172.19.71.32/zaiko2020";
	private static final String dbuser = "root";
	private static final String dbpass = "";

	/***** 在庫情報一覧表示 *****/
	public List<Book> findBooklist() {
		Connection conn = null;
		List<Book> bookList = new ArrayList<Book>();
		boolean dbError = false;

		// 接続先DB、ユーザーID、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			// bookテーブルからidの昇順で取得
			String sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteFlg,((@i:=@i+1) -1) DIV 10 as pageNo FROM books where deleteflg = 0 ORDER BY id";
			String set_sql = "SET @i:=0";
			PreparedStatement pStmt_set = conn.prepareStatement(set_sql);
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SELECTを実行し、結果表を取得
			ResultSet rs_set = pStmt_set.executeQuery();
			ResultSet rs = pStmt.executeQuery();

			dbError = true;
			// bookテーブルの情報がなくなるまでテーブルの情報を取得
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String salesDate = rs.getString("salesDate");
				String isbn = rs.getString("isbn");
				int price = Integer.parseInt(rs.getString("price"));
				int stock = Integer.parseInt(rs.getString("stock"));
				boolean deleteflg = rs.getBoolean("deleteflg");
				int pageNo = Integer.parseInt(rs.getString("pageNo"));
				Book book = new Book(id, title, author, salesDate, isbn, price, stock, deleteflg, pageNo);
				bookList.add(book);
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
		// DBに接続している場合bookListを返す
		if (dbError) {
			return bookList;
		} else { // DBに接続できていない場合dbErrorListを返す
			List<Book> dbErrorList = new ArrayList<Book>();
			Book error = new Book();
			error.setDb_flag(false);
			dbErrorList.add(error);

			return dbErrorList;
		}
	}
	/***** 在庫情報一覧表示 *****/
	public List<Book> findBooklist(String searchWord, String sortWord) {
		Connection conn = null;
		List<Book> bookList = new ArrayList<Book>();
		boolean dbError = false;

		// 接続先DB、ユーザーID、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			String sql = "";
			switch(sortWord) {
				case "bookIdAs":
					sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteFlg, ((@i:=@i+1) -1) DIV 10 as pageNo FROM books where deleteflg = 0 AND (title LIKE ? OR author LIKE ? or isbn LIKE ?) ORDER BY id asc";
					break;
				case "bookIdDs":
					sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteFlg, ((@i:=@i+1) -1) DIV 10 as pageNo FROM books where deleteflg = 0 AND (title LIKE ? OR author LIKE ? or isbn LIKE ?) ORDER BY id desc";
					break;
				case "salesDateAs":
					sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteFlg, ((@i:=@i+1) -1) DIV 10 as pageNo FROM books where deleteflg = 0 AND (title LIKE ? OR author LIKE ? or isbn LIKE ?) ORDER BY salesDate asc";
					break;
				case "salesDateDs":
					sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteFlg, ((@i:=@i+1) -1) DIV 10 as pageNo FROM books where deleteflg = 0 AND (title LIKE ? OR author LIKE ? or isbn LIKE ?) ORDER BY salesDate desc";
					break;
				case "ISBNAs":
					sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteFlg, ((@i:=@i+1) -1) DIV 10 as pageNo FROM books where deleteflg = 0 AND (title LIKE ? OR author LIKE ? or isbn LIKE ?) ORDER BY isbn asc";
					break;
				case "ISBNDs":
					sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteFlg, ((@i:=@i+1) -1) DIV 10 as pageNo FROM books where deleteflg = 0 AND (title LIKE ? OR author LIKE ? or isbn LIKE ?) ORDER BY isbn desc";
					break;
			}
			String set_sql = "SET @i:=0";
			PreparedStatement pStmt_set = conn.prepareStatement(set_sql);
			PreparedStatement pStmt = conn.prepareStatement(sql);
			String editWord = "%" + searchWord + "%";
			// 値の設定
			pStmt.setString(1, editWord);
			pStmt.setString(2, editWord);
			pStmt.setString(3, editWord);
			// SELECTを実行し、結果表を取得
			ResultSet rs_set = pStmt_set.executeQuery();
			ResultSet rs = pStmt.executeQuery();

			dbError = true;
			// bookテーブルの情報がなくなるまでテーブルの情報を取得
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String salesDate = rs.getString("salesDate");
				String isbn = rs.getString("isbn");
				int price = Integer.parseInt(rs.getString("price"));
				int stock = Integer.parseInt(rs.getString("stock"));
				boolean deleteflg = rs.getBoolean("deleteflg");
				int pageNo = Integer.parseInt(rs.getString("pageNo"));
				Book book = new Book(id, title, author, salesDate, isbn, price, stock, deleteflg, pageNo);
				bookList.add(book);
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
		// DBに接続している場合bookListを返す
		if (dbError) {
			return bookList;
		} else { // DBに接続できていない場合dbErrorListを返す
			List<Book> dbErrorList = new ArrayList<Book>();
			Book error = new Book();
			error.setDb_flag(false);
			dbErrorList.add(error);

			return dbErrorList;
		}
	}

	/***** 在庫復元一覧表示 *****/
	public List<Book> findResBookList() {
		Connection conn = null;
		List<Book> restList = new ArrayList<Book>();
		boolean dbError = false;

		// 接続先DB、ユーザーID、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			// bookテーブルからidの昇順で取得
			String sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteFlg, ((@i:=@i+1) -1) DIV 10 as pageNo FROM books where deleteflg = 1 ORDER BY id";
			String set_sql = "SET @i:=0";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			PreparedStatement pStmt_set = conn.prepareStatement(set_sql);
			// SELECTを実行し、結果表を取得
			ResultSet rs_set = pStmt_set.executeQuery();
			ResultSet rs = pStmt.executeQuery();

			dbError = true;

			// bookテーブルの情報がなくなるまでテーブルの情報を取得
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String salesDate = rs.getString("salesDate");
				String isbn = rs.getString("isbn");
				int price = Integer.parseInt(rs.getString("price"));
				int stock = Integer.parseInt(rs.getString("stock"));
				boolean deleteflg = rs.getBoolean("deleteflg");
				int pageNo = Integer.parseInt(rs.getString("pageNo"));
				Book book = new Book(id, title, author, salesDate, isbn, price, stock, deleteflg, pageNo);
				restList.add(book);
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
		// DBに接続している場合restListを返す
		if (dbError) {
			return restList;
		} else { // DBに接続できていない場合dbErrorListを返す
			List<Book> dbErrorList = new ArrayList<Book>();
			Book error = new Book();
			error.setDb_flag(false);
			dbErrorList.add(error);

			return dbErrorList;
		}
	}
	/***** 商品編集情報のセレクト *****/
	public List<Book> gEdit(int[] intBookIds) {
		Connection conn = null;
		List<Book> editList = new ArrayList<Book>();
		boolean dbError = false;

		// 接続先DB、ユーザー名、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			for(int i = 0;i < intBookIds.length; i++) {
				// goodsテーブルのid,goodsid,goods_name,genre,quantityをidで取得
				String sql = "SELECT id,title,author,salesDate,isbn,price,stock,deleteflg, ((@i:=@i+1) -1) DIV 10 as pageNo FROM books WHERE id=?";
				String set_sql = "SET @i:=0";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				PreparedStatement pStmt_set = conn.prepareStatement(set_sql);
				// 値の設定
				pStmt.setInt(1, intBookIds[i]);
				// SELECTを実行し、結果表を取得
				ResultSet rs_set = pStmt_set.executeQuery();
				ResultSet rs = pStmt.executeQuery();
		
				dbError = true;
		
				// goodsテーブルの情報がなくなるまでテーブルの情報を取得
				while (rs.next()) {
					int id = rs.getInt("id");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String salesDate = rs.getString("salesDate");
					String isbn = rs.getString("isbn");
					int price = Integer.parseInt(rs.getString("price"));
					int stock = Integer.parseInt(rs.getString("stock"));
					boolean deleteflg = rs.getBoolean("deleteflg");
					int pageNo = Integer.parseInt(rs.getString("pageNo"));
					Book book = new Book(id, title, author, salesDate, isbn, price, stock, deleteflg, pageNo);
					editList.add(book);
				}
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
		// DBに接続している場合editListを返す
		if (dbError) {
			return editList;
		} else { // DBに接続できていない場合dbErrorListを返す
			List<Book> dbErrorList = new ArrayList<Book>();
			Book error = new Book();
			error.setDb_flag(false);
			dbErrorList.add(error);

			return dbErrorList;
		}
	}
	/***** 商品編集情報のセレクト *****/
	public Book getMaxId() {
		Connection conn = null;
		Book maxBookId = new Book();
		boolean dbError = false;

		// 接続先DB、ユーザー名、パスワード
		try {
			Class.forName(DRIVER_NAME);
			// DBへ接続
			conn = DriverManager.getConnection(url, dbuser, dbpass);

			// goodsテーブルのid,goodsid,goods_name,genre,quantityをidで取得
			String sql = "SELECT count(id) as id FROM books";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();
	
			dbError = true;
	
			// goodsテーブルの情報がなくなるまでテーブルの情報を取得
			while (rs.next()) {
				int bookid = rs.getInt("id");
				String title = "";
				String author = "";
				String salesDate = "";
				String isbn = "";
				int price = 0;
				int stock = 0;
				boolean deleteflg = false;
				int pageNo = 0;
				Book book = new Book(bookid, title, author, salesDate, isbn, price, stock, deleteflg, pageNo);
				maxBookId = book;
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
		// DBに接続している場合editListを返す
		if (dbError) {
			return maxBookId;
		} else { // DBに接続できていない場合dbErrorListを返す
			Book dbErrorList = new Book();
			dbErrorList.setDb_flag(false);

			return dbErrorList;
		}
	}
	
}