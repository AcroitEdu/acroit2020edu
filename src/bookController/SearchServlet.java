package bookController;

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
import model.Book;
import model.CommonLogic;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession(true);
		String serchWord = (String) request.getParameter("search");
		String sortPuttrn = (String) request.getParameter("sortPuttrn");
		CommonLogic comLogic = new CommonLogic();
		int[] selectCheck = comLogic.castInt(request.getParameterValues("bookSelect"));
		int intShowPage = 0;
		int lastPaae = 0;
		
		String sessionChk = (String) session.getAttribute("userid");
		
		//フォワード先
		String forwardPath = null;

		// ログイン情報がnullの場合、認証画面へ戻す
		if (sessionChk == null) {
			request.setAttribute("error", "ログインを完了させてください");
			forwardPath = "index.jsp";
		} else { // ログイン情報が存在
			// 変数宣言
			BookSelectDAO dao = new BookSelectDAO();		
			
			// goodsテーブルから在庫一覧画面に表示する情報を取得
			List<Book> bookList = dao.findBooklist(serchWord, sortPuttrn);

			if(bookList.size() == 0) {
				// DB接続エラー表示
				request.setAttribute("error", "検索結果は0件です。");
			}else {
				// DB接続失敗
				if (!bookList.get(0).isDb_flag()) {
					// DB接続エラー表示
					request.setAttribute("dbError", "データベースに接続できません");
				}
				lastPaae = bookList.get(bookList.size() -1).getPageNo();
			}
			
			// リクエストに結果を乗せる
			request.setAttribute("lastPage", lastPaae);
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
