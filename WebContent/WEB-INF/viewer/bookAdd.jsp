<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import = "java.util.List,model.Book" %>
	<!--  -->
<%
	String maxBookId = (String) request.getAttribute("maxBookId");
 	String retFlg = (String) request.getAttribute("ret");
 	List<Book> bookList = (List<Book>) request.getAttribute("bookList");
	String title = (String) request.getAttribute("title");
	String error = (String) request.getAttribute("error");
	String dbError = (String) request.getAttribute("dbError");

	String userid = (String) session.getAttribute("userid");
	
	int i=0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫商品追加</title>
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
				<div class="paging">
				<table border=1>
					<tr>
						<th id="gid">商品ID</th>
						<th id="title">商品名</th>
						<th id="author">著者</th>
						<th id="salesDate">発売日</th>
						<th id="isbn">ISBN</th>
						<th id="price">価格</th>
						<th id="stock">在庫数</th>
					</tr>
					<!-- DBエラー時はテーブル非表示 -->
					<% if(dbError == null){ %>
						<!-- 一覧表示 -->
							<% if(retFlg.equals("true")) {
									int m = Integer.parseInt(maxBookId);
									for(int j = 1; j<11;j++){ %>
 								<tr>
 									<% i = m + j; %>
 									<td id="gid"><input type="hidden" name="bookId" value="<%= i %>"><%= i %></td>
 									<td id="title"><input type="text" name="title" value=""></td>
 									<td id="author"><input type="text" name="author" value=""></td>
 									<td id="salesDate"><input type="text" name="salesDate" value=""></td>
 									<td id="isbn"><input type="text" name="isbn" value=""></td>
 									<td id="price"><input type="number" name="price" value=""></td>
 									<td id="stock"><input type="number" name="stock" value=""></td>
 								</tr> 
 								<% } %>
							<% } else {
								for(Book g:bookList){ %>
									<tr>
									<td id="gid"><input type="hidden" name="bookId" value="<%= g.getId() %>"></td>
 									<td id="title"><input type="text" name="title" value="<%=g.getTitle()%>"></td>
 									<td id="author"><input type="text" name="author" value="<%=g.getAuthor()%>"></td>
 									<td id="salesDate"><input type="text" name="salesDate" value="<%=g.getSalesDate()%>"></td>
 									<td id="isbn"><input type="text" name="isbn" value="<%=g.getIsbn()%>"></td>
 									<td id="price"><input type="number" name="price" value="<%=g.getPrice()%>"></td>
 									<td id="stock"><input type="number" name="stock" value="<%=g.getStock()%>"></td>
 								</tr> 
								<% } %>	
							<% } %>	
					<% } %>
				</table>
				</div>
			</li>
		</ul>
		<button type="submit" name="access" value="addRun">追加実行</button>
	</form>
	</div>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>
