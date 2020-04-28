package businessController;

import java.io.IOException;
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

/**
 * Servlet implementation class RunStockInServlet
 */
@WebServlet("/RunStockInServlet")
public class RunStockInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RunStockInServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession(true);
		String sessionChk = (String) session.getAttribute("userid");
		int intShowPage = 0;
    	
    	//フォワード先
		String forwardPath = null;
		
		// ログイン情報がnullの場合、認証画面へ戻す
		if (sessionChk == null) {
			request.setAttribute("error", "ログインを完了させてください");
			forwardPath = "index.jsp";
		} else { // ログイン情報が存在
			//リクエスト取得
			String[] bookId = request.getParameterValues("Eid");
			String[] NyukaCount = request.getParameterValues("Estock");
			
			BookSelectDAO dao = new BookSelectDAO();
			ProcessingDAO pDao = new ProcessingDAO();
			
			int[] intBookIds = new int[bookId.length];
			int[] intNyukaCounts = new int[NyukaCount.length];
			int intBookId = 0;
			int intNyukaCount = 0;
			int n = 0;		
			
			for(String nyukaCount :NyukaCount) {
				intBookId = Integer.parseInt(bookId[n]);
				intNyukaCount = Integer.parseInt(nyukaCount);
				intBookIds[n] = intBookId;
				intNyukaCounts[n] = intNyukaCount;
				n++;
			}
			
			pDao.arrival(intNyukaCounts, intBookIds);
			
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
