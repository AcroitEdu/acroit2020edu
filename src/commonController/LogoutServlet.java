package commonController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// ログアウト処理
		// ログイン情報を取得
		HttpSession session = request.getSession(true);
		String sessionChk = (String) session.getAttribute("userid");

		// ログイン情報がnullの場合、認証画面へ戻す
		if (sessionChk == null) {
			request.setAttribute("error", "ログインを完了させてください");
			request.getRequestDispatcher("index.jsp").forward(request, response);

		} else { // ログイン情報が存在
			// セッション情報の無効化
			session.invalidate(); 
			request.getRequestDispatcher("/WEB-INF/viewer/logout.jsp").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			// ログアウト処理
			// ログイン情報を取得
			HttpSession session = request.getSession(true);
			String sessionChk = (String) session.getAttribute("userid");

			// ログイン情報がnullの場合、認証画面へ戻す
			if (sessionChk == null) {
				request.setAttribute("error", "ログインを完了させてください");
				request.getRequestDispatcher("index.jsp").forward(request, response);

			} else { // ログイン情報が存在
				// セッション情報の無効化
				session.invalidate(); 
				request.getRequestDispatcher("/WEB-INF/viewer/logout.jsp").forward(request, response);
			}
	}
}