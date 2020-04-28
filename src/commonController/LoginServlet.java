package commonController;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserSelectDAO;
import model.LoginLogic;
import model.LoginUser;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		// URLから直接入ってきた場合
		request.setAttribute("error", "ログインを完了させてください");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// index.jsp → LoginServlet

		UserSelectDAO dao = new UserSelectDAO();
		List<User> userList = dao.findUlist();

		// DB接続失敗
		if (!userList.get(0).isDb_flag()) {
			// DB接続エラー表示
			request.setAttribute("userList", userList);
			request.setAttribute("error", "データベースへの接続エラーでログインできません");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;

		} else { // DB接続成功

			// 変数宣言
			String userid = request.getParameter("userid");
			String pass = request.getParameter("pass");
			LoginUser lu = new LoginUser(userid, pass);
			LoginLogic lo = new LoginLogic();
			LoginUser user = lo.Save(lu);
			boolean result = lo.execute(lu);

			if (result) {
				// セッションスコープにユーザーIDを保存
				HttpSession session = request.getSession(true);
				session.setAttribute("userid", userid);

				// MenuServletへリダイレクト
				response.sendRedirect("/zaiko2020/BookServlet");
			} else {
				// ログイン失敗時
				// エラーメッセージ表示
				request.setAttribute("error", "ユーザーID、またはパスワードが違います");
				// 認証画面へフォワード
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}
	}
}