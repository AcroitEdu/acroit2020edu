package bookController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookSelectDAO;
import dao.ProcessingDAO;
import model.Book;
import model.CommonLogic;


@WebServlet("/ConfirmServlet")
public class ConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RunTypeArrival = "Arrival";
	private static final String RunTypeShipment = "Shipment";
	private static final String RunTypeEdit = "Update";
	private static final String RunTypeAdd = "Insert";
	private static final String retFlg = "false";
    
    public ConfirmServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession(true);
		String sessionChk = (String) session.getAttribute("userid");
		String access = (String) request.getParameter("access");
		CommonLogic comLogic = new CommonLogic();

    	//フォワード先
		String forwardPath = null;
		
		// ログイン情報がnullの場合、認証画面へ戻す
		if (sessionChk == null) {
			request.setAttribute("error", "ログインを完了させてください");
			forwardPath = "index.jsp";
		} else { // ログイン情報が存在
			if(access.equals("nyukaRun")) {
				//リクエスト取得
				int[] intBookIds = comLogic.castInt(request.getParameterValues("bookId"));
				int[] NyukaCounts = comLogic.castInt(request.getParameterValues("nyuka"));
				
				BookSelectDAO dao = new BookSelectDAO();
				
				int n = 0;
				boolean checkFlg = false;
				List<Book> nyukaBookList = new ArrayList<Book>();
				//
				for(int nyukaCount :NyukaCounts) {
					if(nyukaCount == 0) {
						checkFlg = true;
						break;
			    	}
				}
				
				List<Book> bookList = dao.gEdit(intBookIds);
				// DB接続失敗
				if (!bookList.get(0).isDb_flag()) {
					// DB接続エラー表示
					request.setAttribute("dbError", "データベースに接続できません");
				}

				if(checkFlg) {
					request.setAttribute("error", "nyukasuをinputしてください");
					// リクエストに結果を乗せる
					request.setAttribute("nyukaList", bookList);
					request.setAttribute("title", "nyuka画面");
					// 在庫一覧画面へフォワード
					forwardPath = "/WEB-INF/viewer/nyuka.jsp";
				}else {
					n = 0;
					for(Book before:bookList) {
						int stock = before.getStock() + NyukaCounts[n];
						Book book = new Book(before.getId(), before.getTitle(), before.getAuthor(),
								before.getSalesDate(), before.getIsbn(), before.getPrice(), stock, before.isDeleteflg(), 0);
						nyukaBookList.add(book);
						n++;
					}
					
					// リクエストに結果を乗せる
					request.setAttribute("bookList", bookList);
					request.setAttribute("editList", nyukaBookList);
					request.setAttribute("title", "kakunin画面");
					request.setAttribute("changeFlg", RunTypeArrival);
					// 在庫一覧画面へフォワード
					forwardPath = "/WEB-INF/viewer/confirm.jsp";
				}
			}
			if(access.equals("syukaRun")) {
				//リクエスト取得
				int[] intBookIds = comLogic.castInt(request.getParameterValues("bookId"));
				int[] SyukaCounts = comLogic.castInt(request.getParameterValues("syuka"));
				
				BookSelectDAO dao = new BookSelectDAO();

				int n = 0;
				boolean checkFlg = false;
				List<Book> nyukaBookList = new ArrayList<Book>();
				//
				for(int syukaCount :SyukaCounts) {
					if(syukaCount == 0) {
						checkFlg = true;
						break;
			    	}
				}
				
				List<Book> bookList = dao.gEdit(intBookIds);
				// DB接続失敗
				if (!bookList.get(0).isDb_flag()) {
					// DB接続エラー表示
					request.setAttribute("dbError", "データベースに接続できません");
				}

				if(checkFlg) {
					request.setAttribute("error", "nyukasuをinputしてください");
					// リクエストに結果を乗せる
					request.setAttribute("nyukaList", bookList);
					request.setAttribute("title", "nyuka画面");
					// 在庫一覧画面へフォワード
					forwardPath = "/WEB-INF/viewer/nyuka.jsp";
				}else {
					n = 0;
					for(Book before:bookList) {
						int stock = before.getStock() - SyukaCounts[n];
						Book book = new Book(before.getId(), before.getTitle(), before.getAuthor(),
								before.getSalesDate(), before.getIsbn(), before.getPrice(), stock, before.isDeleteflg(), 0);
						nyukaBookList.add(book);
						n++;
					}
					
					// リクエストに結果を乗せる
					request.setAttribute("bookList", bookList);
					request.setAttribute("editList", nyukaBookList);
					request.setAttribute("title", "kakunin画面");
					request.setAttribute("changeFlg", RunTypeShipment);
					// 在庫一覧画面へフォワード
					forwardPath = "/WEB-INF/viewer/confirm.jsp";
				}
			}
			if(access.equals("editRun")) {
				//リクエスト取得
				int[] intBookIds = comLogic.castInt(request.getParameterValues("bookId"));
				String[] getTitle = comLogic.checkParam(request.getParameterValues("title"));
				String[] getAuthor = comLogic.checkParam(request.getParameterValues("author"));
				String[] getIsbn = comLogic.checkParam(request.getParameterValues("isbn"));
				String[] getSalesDate = comLogic.checkParam(request.getParameterValues("salesDate"));
				int[] intPrices = comLogic.castInt(request.getParameterValues("price"));
				int[] intStocks = comLogic.castInt(request.getParameterValues("stock"));
				
				BookSelectDAO dao = new BookSelectDAO();
				int n = 0;
				
				List<Book> beforeBookList = dao.gEdit(intBookIds);
				List<Book> afterBookList = new ArrayList<>();
				
				// DB接続失敗
				if (!beforeBookList.get(0).isDb_flag()) {
					// DB接続エラー表示
					request.setAttribute("dbError", "データベースに接続できません");
				}

				n = 0;
				for(Book before:beforeBookList) {
					Book book = new Book(before.getId(), getTitle[n], getAuthor[n], getSalesDate[n],
							getIsbn[n], intPrices[n], intStocks[n], false, 0);
					afterBookList.add(book);
					n++;
				}
				
				// リクエストに結果を乗せる
				request.setAttribute("bookList", beforeBookList);
				request.setAttribute("editList", afterBookList);
				request.setAttribute("title", "kakunin画面");
				request.setAttribute("changeFlg", RunTypeEdit);
				// 在庫一覧画面へフォワード
				forwardPath = "/WEB-INF/viewer/confirm.jsp";
			}
			if(access.equals("addRun")) {
				//リクエスト取得
				int[] intBookIds = comLogic.castInt(request.getParameterValues("bookId"));
				String[] getTitle = comLogic.checkParam(request.getParameterValues("title"));
				String[] getAuthor = comLogic.checkParam(request.getParameterValues("author"));
				String[] getIsbn = comLogic.checkParam(request.getParameterValues("isbn"));
				String[] getSalesDate = comLogic.checkParam(request.getParameterValues("salesDate"));
				int[] intPrices = comLogic.castInt(request.getParameterValues("price"));
				int[] intStocks = comLogic.castInt(request.getParameterValues("stock"));
				
				int n = 0;
				String errorMsg = "";
				
				List<Book> afterBookList = new ArrayList<>();
				
				for(int intId:intBookIds) {
					if(getTitle.length != getAuthor.length || getTitle.length != getSalesDate.length ||
							getTitle.length != getIsbn.length || getTitle.length != intPrices.length ||
							getTitle.length != intStocks.length) {
						errorMsg = "[error]kara no column ga arimasu";
						break;
					}
					if(getTitle.length <= n || getAuthor.length <= n || getSalesDate.length <= n ||
							getIsbn.length <= n || intPrices.length <= n || intStocks.length <= n) {
						break;
					}
					Book book = new Book(intId, getTitle[n], getAuthor[n], getSalesDate[n], getIsbn[n],
							intPrices[n], intStocks[n], false, 0);
					afterBookList.add(book);
					n++;
				}
				
				if(!errorMsg.equals("")) {
					afterBookList = comLogic.getBookList(intBookIds, getTitle, getAuthor,getSalesDate,getIsbn,intPrices,intStocks);
					// リクエストに結果を乗せる
					request.setAttribute("error", errorMsg);
					request.setAttribute("bookList", afterBookList);
					request.setAttribute("ret", retFlg);
					request.setAttribute("title", "在庫tuika画面");
					// 在庫一覧画面へフォワード
					forwardPath = "/WEB-INF/viewer/bookAdd.jsp";
				}else {
					// リクエストに結果を乗せる
					request.setAttribute("bookList", afterBookList);
					request.setAttribute("title", "kakunin画面");
					request.setAttribute("changeFlg", RunTypeAdd);
					// 在庫一覧画面へフォワード
					forwardPath = "/WEB-INF/viewer/confirm.jsp";
				}
			}
		}
    	//設定されたフォワード先にフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
	    dispatcher.forward(request, response);
    }
	
}
