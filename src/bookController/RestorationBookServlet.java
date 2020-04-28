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

/**
 * Servlet implementation class RestorationBookServlet
 */
@WebServlet("/RestorationBookServlet")
public class RestorationBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestorationBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession(true);
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
			List<Book> bookList = dao.findResBookList();

			// DB接続失敗
			if (!bookList.get(0).isDb_flag()) {
				// DB接続エラー表示
				request.setAttribute("dbError", "データベースに接続できません");
			}
			// リクエストに結果を乗せる
			request.setAttribute("bookList", bookList);
			request.setAttribute("title", "在庫hukugen");
			// 在庫一覧画面へフォワード
			forwardPath = "/WEB-INF/viewer/bookResList.jsp";
		}
		//設定されたフォワード先にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}
	    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession(true);
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
			List<Book> bookList = dao.findResBookList();

			// DB接続失敗
			if (!bookList.get(0).isDb_flag()) {
				// DB接続エラー表示
				request.setAttribute("dbError", "データベースに接続できません");
			}
			// リクエストに結果を乗せる
			request.setAttribute("bookList", bookList);
			request.setAttribute("title", "在庫hukugen");
			// 在庫一覧画面へフォワード
			forwardPath = "/WEB-INF/viewer/bookResList.jsp";
		}
		//設定されたフォワード先にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
	    try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
