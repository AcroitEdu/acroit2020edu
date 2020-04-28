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

/**
 * Servlet implementation class UpdateGoodsServlet
 */
@WebServlet("/UpdateBookServlet")
public class UpdateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateBookServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
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
			int[] selectCheck = comLogic.castInt(request.getParameterValues("bookSelect"));
	    	
	    	if(selectCheck == null){
	    		request.setAttribute("error", "商品を選択してください");
		    	forwardPath = "/BookServlet";
	    	}else {
	    		// 変数宣言
				BookSelectDAO dao = new BookSelectDAO();
				// goodsテーブルから在庫一覧画面に表示する情報を取得
				List<Book> bookList = dao.gEdit(selectCheck);
				// DB接続失敗
				if (!bookList.get(0).isDb_flag()) {
					// DB接続エラー表示
					request.setAttribute("dbError", "データベースに接続できません");
				}
				Book lastBook = bookList.get(bookList.size() -1);
				
				// リクエストに結果を乗せる
				request.setAttribute("lastPage", lastBook.getPageNo());
				request.setAttribute("showPage", intShowPage);
				request.setAttribute("editList", bookList);
				request.setAttribute("title", "在庫編集画面");
				// 在庫一覧画面へフォワード
				forwardPath = "/WEB-INF/viewer/bookEdit.jsp";
	    	}
		}
    	//設定されたフォワード先にフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
	    dispatcher.forward(request, response);
    }

}
