<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import = "java.util.List,model.Book" %>
<%
	List<Book> bookList = (List<Book>) request.getAttribute("editList");
	String title = (String) request.getAttribute("title");
	String error = (String) request.getAttribute("error");
	String dbError = (String) request.getAttribute("dbError");

	String userid = (String) session.getAttribute("userid");
	
	int i=0;
	int j=0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫商品編集</title>
<link rel="stylesheet" href="css/guAdEd.css" type="text/css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
</head>

<body>
	<h1><%= title %></h1>
	<div id="main">
	<form method="post" action="/zaiko2020/MultiServlet">
		<jsp:include page="header.jsp" flush="true" />
		<ul>
			<li><p id="error"><% if(dbError != null){ %><%= dbError %><% } %></p></li>
			<li>
				<!-- チェックボックス未入力のエラー表示 -->
				<p id="error"><% if(error != null){ %><%= error %><% } %></p>
				<div class="paging">
				<table border=1>
					<tr>
						<th id="gid">商品ID</th>
						<th id="title">商品名</th>
						<th id="author">Author</th>
						<th id="salesDate">salesDate</th>
						<th id="isbn">ISBN</th>
						<th id="price">Price</th>
						<th id="stock">在庫数</th>
					</tr>
					<!-- DBエラー時はテーブル非表示 -->
					<% if(dbError == null){ %>
						<!-- 一覧表示 -->
						<% for(Book g:bookList){ %>
					<tr>
						<td id="gid"><input type="hidden" name="bookId" value="<%= g.getId() %>"><%= g.getId() %></td>
						<td id="title"><input type="text" name="title" value="<%=g.getTitle()%>" required></td>
						<td id="author"><input type="text" name="author" value="<%=g.getAuthor()%>" required></td>
						<td id="salesDate"><input type="text" name="salesDate" value="<%=g.getSalesDate()%>" required></td>
						<td id="isbn"><input type="text" name="isbn" value="<%=g.getIsbn()%>" required></td>
						<td id="price"><input type="number" name="price" value="<%=g.getPrice()%>" required></td>
						<td id="stock"><input type="number" name="stock" value="<%=g.getStock()%>" required></td>
							<% } %>
					</tr>
					<% } %>
				</table>
				</div>
			</li>
		</ul>
		<button type="submit" name="access" value="editRun">編集実行</button>
	</form>
	</div>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>