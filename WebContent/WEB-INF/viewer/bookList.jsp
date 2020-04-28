<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import = "java.util.List,model.Book" %>
<%
	List<Book> bookList = (List<Book>) request.getAttribute("bookList");
	String title = (String) request.getAttribute("title");
	int showPage = (int) request.getAttribute("showPage");
	int lastPage = (int) request.getAttribute("lastPage");
	String error = (String) request.getAttribute("error");
	String dbError = (String) request.getAttribute("dbError");
	
	String userid = (String) session.getAttribute("userid");

	int i=0;
	int j=0;
	int prevPage = 0;
	int nextPage = 0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>在庫情報一覧</title>
<link rel="stylesheet" href="css/gList.css" type="text/css">
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
				<input type="search" name="search" placeholder="search keyword is here" /><button type="submit" name="access" value="search">search</button>
				<div class="paging">
				<select name="sortPuttrn">
					<option value="bookIdAs" selected="selected">書籍IDの昇順</option>
					<option value="bookIdDs">書籍IDの降順</option>
					<option value="salesDateAs">発売日の新しい順</option>
					<option value="salesDateDs">発売日の古い順</option>
					<option value="ISBNAs">ISBNの昇順</option>
					<option value="ISBNDs">ISBNの降順</option>
				</select>
				<table border=1>
					<tr>
						<th><input type="checkbox" name="allcheck" onClick="AllChecked();" /> 全選択</th>
						<th id="gid">商品ID</th>
						<th id="title">商品名</th>
						<th id="author">Author</th>
						<th id="salesDate">発売日</th>
						<th id="isbn">ISBN</th>
						<th id="price">Price</th>
						<th id="stock">在庫数</th>
					</tr>
					<!-- DBエラー時はテーブル非表示 -->
					<% if(dbError == null){ %>
						<!-- 一覧表示 -->
						<% for(Book g:bookList){ 
								if(showPage == g.getPageNo()) {%>
					<tr>
						<td id="check">
							<input type="checkbox" name="bookSelect" value="<%= g.getId() %>">
						</td>
						<td id="gid"><%= g.getId() %></td>

						<td><!-- マウスオーバー -->
							<div class="tof" onmouseover="$(this).attr('title',$(this).text())"><%= g.getTitle() %></div>
						</td>
						<td id="author"><%=g.getAuthor()%></td>
						<td id="salesDate"><%=g.getSalesDate()%></td>
						<td id="isbn"><%=g.getIsbn()%></td>
						<td id="price"><%=g.getPrice()%></td>
						<td id="stock"><%=g.getStock()%></td>
					</tr>
								<% } %>
						<% } %>
					<% } %>
				</table>
				</div>
			</li>
		</ul>
	</form>
	<form method="get" action="/zaiko2020/BookServlet">
		<div align="left">
		<% if(showPage != 0){
			prevPage = showPage - 1; 
			} %>
			<button type="submit" name="page" value="0">fist</button><button type="submit" name="page" value="<%= prevPage %>">prev</button>
		</div>
		<div align="right">
		<% if(showPage != lastPage){
			nextPage = showPage + 1; 
			}%>
			<button type="submit" name="page" value="<%= nextPage %>">next</button><button type="submit" name="page" value="<%= lastPage %>">last</button>
		</div>
	</form>
	</div>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>