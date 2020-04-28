package model;

public class Book {

	private int id;
	private String title;
	private String author;
	private String salesDate;
	private String isbn;
	private int price;
	private int stock;
	private boolean deleteflg;
	private int pageNo;
	private boolean db_flag = true;

	// 在庫情報一覧表示
	public Book(int id, String title, String author, String salesDate, String isbn, int price, int stock, boolean deleteflg, int pageNo) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.salesDate = salesDate;
		this.isbn = isbn;
		this.price = price;
		this.stock = stock;
		this.deleteflg = deleteflg;
		this.pageNo = pageNo;
	}
	public Book() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getSalesDate() {
		return salesDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public int getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public boolean isDeleteflg() {
		return deleteflg;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public boolean isDb_flag() {
		return db_flag;
	}

	public void setDb_flag(boolean db_flag) {
		this.db_flag = db_flag;
	}
}