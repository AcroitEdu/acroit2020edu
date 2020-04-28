<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import = "java.util.List,model.Book" %>
<%
	List<Book> bookList = (List<Book>) request.getAttribute("bookList");
	String changeFlg = (String) request.getAttribute("changeFlg");
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
<title>kakunin</title>
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
						<td id="title"><input type="hidden" name="title" value="<%=g.getTitle()%>"><%=g.getTitle()%></td>
						<td id="author"><input type="hidden" name="author" value="<%=g.getAuthor()%>"><%=g.getAuthor()%></td>
						<td id="salesDate"><input type="hidden" name="salesDate" value="<%=g.getSalesDate()%>"><%=g.getSalesDate()%></td>
						<td id="isbn"><input type="hidden" name="isbn" value="<%=g.getIsbn()%>"><%=g.getIsbn()%></td>
						<td id="price"><input type="hidden" name="price" value="<%=g.getPrice()%>"><%=g.getPrice()%></td>
						<td id="stock"><input type="hidden" name="stock" value="<%=g.getStock()%>"><%=g.getStock()%></td>
							<% } %>
					</tr>
					<% } %>
				</table>
				</div>
				<div>
					<h2>↓</h2>
				</div>
				<% if(changeFlg != "Insert" ){ 
					List<Book> editList = (List<Book>) request.getAttribute("editList");%>
					<table border=1>
					<tr>
						<th id="Eid">商品ID</th>
						<th id="Etitle">商品名</th>
						<th id="Eauthor">Author</th>
						<th id="Eisbn">ISBN</th>
						<th id="EsalesDate">salesDate</th>
						<th id="Eprice">Price</th>
						<th id="Estock">在庫数</th>
					</tr>
					
					<% for(Book g:editList){ %>
					<tr>
						<% Book beforeBook = bookList.get(i); %>
						<td id="Eid"><%= g.getId() %>
						<input type="hidden" name="Eid" value="<%= g.getId()%>">
						</td>
							<% if(g.getTitle().equals(beforeBook.getTitle())){%>
								<td id="Etitle">
								<%= beforeBook.getTitle()%>
								<input type="hidden" name="Etitle" value="<%= beforeBook.getTitle()%>">
							<% } else { %>
								<td id="Etitle" bgcolor="#c0ffc0">
								<%=g.getTitle()%>
								<input type="hidden" name="Etitle" value="<%= g.getTitle()%>">
							<% } %>
						</td>
							<% if(g.getAuthor().equals(beforeBook.getAuthor())){%>
								<td id="Eauthor">
								<%= beforeBook.getAuthor()%>
								<input type="hidden" name="Eauthor" value="<%= beforeBook.getAuthor()%>">
							<% } else { %>
								<td id="Eauthor" bgcolor="#c0ffc0">
								<%=g.getAuthor()%>
								<input type="hidden" name="Eauthor" value="<%= g.getAuthor()%>">
							<% } %>
						</td>
							<% if(g.getSalesDate().equals(beforeBook.getSalesDate())){%>
								<td id="EsalesDate">
								<%= beforeBook.getSalesDate()%>
								<input type="hidden" name="EsalesDate" value="<%= beforeBook.getSalesDate()%>">
							<% } else { %>
								<td id="EsalesDate" bgcolor="#c0ffc0">
								<%=g.getSalesDate()%>
								<input type="hidden" name="EsalesDate" value="<%= g.getSalesDate()%>">
							<% } %>
						</td>
							<% if(g.getIsbn().equals(beforeBook.getIsbn())){%>
									<td id="Eisbn">
									<%= beforeBook.getIsbn()%>
									<input type="hidden" name="Eisbn" value="<%= beforeBook.getIsbn()%>">
								<% } else { %>
									<td id="Eisbn" bgcolor="#c0ffc0">
									<%=g.getIsbn()%>
									<input type="hidden" name="Eisbn" value="<%= g.getIsbn()%>">
								<% } %>
							</td>
							<% if(g.getPrice() == beforeBook.getPrice()){%>
								<td id="Eprice">
								<%= beforeBook.getPrice()%>
								<input type="hidden" name="Eprice" value="<%= beforeBook.getPrice()%>">
							<% } else { %>
								<td id="Eprice" bgcolor="#c0ffc0">
								<%=g.getPrice()%>
								<input type="hidden" name="Eprice" value="<%= g.getPrice()%>">
							<% } %>
						</td>
							<% if(g.getStock() == beforeBook.getStock()){%>
								<td id="Estock">
								<%= beforeBook.getStock()%>
								<input type="hidden" name="Estock" value="<%= beforeBook.getStock()%>">
							<% } else { %>
								<td id="Estock" bgcolor="#c0ffc0">
								<%=g.getStock()%>
								<input type="hidden" name="Estock" value="<%= g.getStock()%>">
							<% } %>
						</td>
						<% i++; %>
					</tr>
					<% } %>
				</table>
				<% }else{ %>
					上記、商品を追加します。
				<% } %>
			</li>
		</ul>
		<button type="submit" name="access" value="<%= changeFlg %>">確定</button>
	</form>
	</div>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>
