package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class ZaikoFilter
 */
@WebFilter("/*")
public class ZaikoFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ZaikoFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {

		// Encoding UTF-8
		request.setCharacterEncoding("UTF-8");

		// セッションタイムアウト処理
		try {
			// リクエスト情報取得
			HttpSession time = ((HttpServletRequest) request).getSession();
			// 現在の時間を取得し変数nowに代入
			long now = System.currentTimeMillis();

			if (time.getAttribute("endTime") != null) {

				// タイムアウトまでの時間設定
				if (now - (long) time.getAttribute("endTime") >= 1000 * 60 * 20) { // 20分に設定
					HttpSession session = ((HttpServletRequest) request).getSession();
					// セッションの無効化
					session.invalidate();
					// エラーメッセージをリクエストに乗せる
					request.setAttribute("error", "セッションが切れました。再度ログインしてください。");
					// 認証画面にフォワード
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}
			}
			// "endTime"に現在の時間を乗せる
			time.setAttribute("endTime", System.currentTimeMillis());
			chain.doFilter(request, response);
		} catch (ServletException se) {
			se.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
