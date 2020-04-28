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
 * Servlet implementation class InsertBookServlet
 */
@WebServlet("/InsertBookServlet")
public class InsertBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String retFlg = "true";

    public InsertBookServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
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
			BookSelectDAO dao = new BookSelectDAO();
			// goodsテーブルから在庫一覧画面に表示する情報を取得
			Book maxBook = dao.getMaxId();
			// DB接続失敗
			if (!maxBook.isDb_flag()) {
				// DB接続エラー表示
				request.setAttribute("dbError", "データベースに接続できません");
			}
			String maxBookId = String.valueOf(maxBook.getId());
			request.setAttribute("maxBookId", maxBookId);
			request.setAttribute("ret", retFlg);
			request.setAttribute("title", "在庫tuika画面");
			// 在庫一覧画面へフォワード
			forwardPath = "/WEB-INF/viewer/bookAdd.jsp";
		}
    	//設定されたフォワード先にフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
	    dispatcher.forward(request, response);
    }

}
