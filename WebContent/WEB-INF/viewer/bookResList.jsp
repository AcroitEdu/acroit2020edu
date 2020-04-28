<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import = "java.util.List,model.Book" %>
<%
	List<Book> bookList = (List<Book>) request.getAttribute("bookList");
	String title = (String) request.getAttribute("title");
	String userid = (String) session.getAttribute("userid");
	String error = (String) request.getAttribute("error");
	String dbError = (String) request.getAttribute("dbError");

	int i=0;
	int j=0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫商品復元</title>
<link rel="stylesheet" href="css/gList.css" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
</head>

<body>
	<div id="main">
	<h1><%= title %></h1>
	<form method="post" action="/zaiko2020/MultiServlet">
		<jsp:include page="header.jsp" flush="true" />
		<ul>
			<li><p id="error"><% if(dbError != null){ %><%= dbError %><% } %></p></li>
			<li>
				<!-- チェックボックス未入力のエラー表示 -->
				<p id="error"><% if(error != null){ %><%= error %><% } %></p>
				<input type="search" name="search" placeholder="search keyword is here" /><input type="submit" name="searchButton" value="search">
				<div class="paging">
				<table border=1>
					<tr>
						<th><input type="checkbox" name="allcheck" onClick="AllChecked();" /> 全選択</th>
						<th id="gid">商品ID</th>
						<th>商品名</th>
						<th id="author">Author</th>
						<th id="isbn">ISBN</th>
						<th id="salesDate">発売日</th>
						<th id="price">Price</th>
						<th id="stock">在庫数</th>
					</tr>
					<!-- DBエラー時はテーブル非表示 -->
					<% if(dbError == null){ %>
						<!-- 一覧表示 -->
						<% for(Book g:bookList){ %>
					<tr>
						<td id="check">
							<input type="checkbox" name="bookSelect" value="<%= g.getId() %>">
						</td>
						<td id="gid"><%= g.getId() %></td>

						<td><!-- マウスオーバー -->
							<div class="tof" onmouseover="$(this).attr('title',$(this).text())"><%= g.getTitle() %></div>
						</td>
						<td id="author"><%=g.getAuthor()%></td>
						<td id="isbn"><%=g.getIsbn()%></td>
						<td id="salesDate"><%=g.getSalesDate()%></td>
						<td id="price"><%=g.getPrice()%></td>
						<td id="stock"><%=g.getStock()%></td>
							<% } %>
					</tr>
					<% } %>
				</table>
				</div>
			</li>
		</ul>
		<input type="submit" name="kakutei" value="kakutei">
	</form>
	</div>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>