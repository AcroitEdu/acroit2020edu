package commonController;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MenuServlet
 */
@WebServlet(name = "MultiServlet", urlPatterns = { "/MultiServlet" })
public class MultiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MultiServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// ログイン情報を取得
		HttpSession session = request.getSession(true);
		String sessionChk = (String) session.getAttribute("userid");
		String access = (String) request.getParameter("access");
		
		//フォワード先
		String forwardPath = null;

		// ログイン情報がnullの場合、認証画面へ戻す
		if (sessionChk == null) {
			request.setAttribute("error", "ログインを完了させてください");
			forwardPath ="index.jsp";
		} else { // ログイン情報が存在
			switch(access) {
			case "ichiran":
				forwardPath ="/BookServlet";
				break;
			case "search":
				forwardPath ="/SearchServlet";
				break;
			case "nyuka":
				forwardPath ="/StockInServlet";
				break;
			case "nyukaRun":
				forwardPath ="/ConfirmServlet";
				break;
			case "Arrival":
				forwardPath ="/RunStockInServlet";
				break;
			case "syuka":
				forwardPath ="/StockOutServlet";
				break;
			case "syukaRun":
				forwardPath ="/ConfirmServlet";
				break;
			case "Shipment":
				forwardPath ="/RunStockOutServlet";
				break;
			case "edit":
				forwardPath ="/UpdateBookServlet";
				break;
			case "editRun":
				forwardPath ="/ConfirmServlet";
				break;
			case "Update":
				forwardPath ="/RunBookEditServlet";
				break;
			case "add":
				forwardPath ="/InsertBookServlet";
				break;
			case "addRun":
				forwardPath ="/ConfirmServlet";
				break;
			case "Insert":
				forwardPath ="/RunBookAddServlet";
				break;
			case "out":
				forwardPath ="/Logout";
				break;
			default:
				forwardPath ="index.jsp";
			}
		}
		//設定されたフォワード先にフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
	    dispatcher.forward(request, response);
	}

}