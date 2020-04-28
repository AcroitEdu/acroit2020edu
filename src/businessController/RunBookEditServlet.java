package businessController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookEditDAO;
import dao.BookSelectDAO;
import model.Book;
import model.CommonLogic;

@WebServlet("/RunBookEditServlet")
public class RunBookEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RunBookEditServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession(true);
		String sessionChk = (String) session.getAttribute("userid");
		CommonLogic comLogic = new CommonLogic();
		int intShowPage = 0;
    	
    	//フォワード先
		String forwardPath = null;
		
		// ログイン情報がnullの場合、認証画面へ戻す
		if (sessionChk == null) {
			request.setAttribute("error", "ログインを完了させてください");
			forwardPath = "index.jsp";
		} else { // ログイン情報が存在
			//リクエスト取得
			int[] intBookIds = comLogic.castInt(request.getParameterValues("Eid"));
			String[] getTitle = comLogic.checkParam(request.getParameterValues("Etitle"));
			String[] getAuthor = comLogic.checkParam(request.getParameterValues("Eauthor"));
			String[] getIsbn = comLogic.checkParam(request.getParameterValues("Eisbn"));
			String[] getSalesDate = comLogic.checkParam(request.getParameterValues("EsalesDate"));
			int[] intPrices = comLogic.castInt(request.getParameterValues("Eprice"));
			int[] intStocks = comLogic.castInt(request.getParameterValues("Estock"));
			
			BookSelectDAO dao = new BookSelectDAO();
			BookEditDAO eDao = new BookEditDAO();

			int n = 0;
			
			List<Book> beforeBookList = dao.gEdit(intBookIds);
			List<Book> afterBookList = new ArrayList<>();
					
			n = 0;
			for(Book before:beforeBookList) {
				if(!before.getTitle().equals(getTitle[n]) || !before.getAuthor().equals(getAuthor[n]) ||
						!before.getSalesDate().equals(getSalesDate[n]) || !before.getIsbn().equals(getIsbn[n]) ||
						before.getPrice() != intPrices[n] || before.getStock() != intStocks[n]) {
					Book book = new Book(before.getId(), getTitle[n], getAuthor[n],
							getSalesDate[n], getIsbn[n], intPrices[n], intStocks[n], false, 0);
					afterBookList.add(book);
				}
				n++;
			}
			eDao.booksUpdate(afterBookList);
			// booksテーブルから在庫一覧画面に表示する情報を取得
			List<Book> bookList = dao.findBooklist();
			// DB接続失敗
			if (!bookList.get(0).isDb_flag()) {
				// DB接続エラー表示
				request.setAttribute("dbError", "データベースに接続できません");
			}
			Book lastBook = bookList.get(bookList.size() -1);
			
			// リクエストに結果を乗せる
			request.setAttribute("lastPage", lastBook.getPageNo());
			request.setAttribute("showPage", intShowPage);
			request.setAttribute("bookList", bookList);
			request.setAttribute("title", "在庫一覧画面");
			// 在庫一覧画面へフォワード
			forwardPath = "/WEB-INF/viewer/bookList.jsp";
		}
    	//設定されたフォワード先にフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
	    dispatcher.forward(request, response);
    }

}
