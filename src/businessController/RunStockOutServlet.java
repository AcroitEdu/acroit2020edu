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
 * Servlet implementation class RunStockOutController
 */
@WebServlet("/RunStockOutServlet")
public class RunStockOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RunStockOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
		}else { // ログイン情報が存在
			//リクエスト取得
			String[] bookId = request.getParameterValues("Eid");
			String[] SyukaCount = request.getParameterValues("Estock");
			
			BookSelectDAO dao = new BookSelectDAO();
			ProcessingDAO pDao = new ProcessingDAO();
			
			int[] intBookIds = new int[bookId.length];
			int[] intSyukaCounts = new int[SyukaCount.length];
			int intBookId = 0;
			int intSyukaCount = 0;
			int n = 0;
			
			for(String syukaCount :SyukaCount) {
				intBookId = Integer.parseInt(bookId[n]);
				intSyukaCount = Integer.parseInt(syukaCount);
				intBookIds[n] = intBookId;
				intSyukaCounts[n] = intSyukaCount;
				n++;
			}
			pDao.arrival(intSyukaCounts, intBookIds);
			
			// booksテーブルから在庫一覧画面に表示する情報を取得
			List<Book> bookList = dao.findBooklist();
			// DB接続失敗
			if (!bookList.get(0).isDb_flag()) {
				// DB接続エラー表示
				request.setAttribute("dbError", "データベースに接続できません");
			}
			// リクエストに結果を乗せる
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
