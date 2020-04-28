<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String error = (String) request.getAttribute("error"); %>

<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<title>認　証</title>
<link rel="stylesheet" href="css/Login.css" type="text/css">
</head>

<body>
	<h1>在庫管理システム</h1>
	<h1 id="h1_2">認　証</h1>
	<!-- エラー表示 -->
	<% if(error !=null){ %><p id="error"><%= error %></p><% } %>
	<form action="/zaiko2020/Login" method="post">
		<ul>
			<li>
				<label>ユーザーID：</label>
				<input type="text" id="userid" name="userid" maxlength="20">
			</li>
			<li>
				<label>パスワード：</label>
				<input type="password" name="pass" maxlength="6">
			</li>
			<li><br><br><br>
				<input id="submit" type="submit" value="ログイン">
			</li>
		</ul>
	</form>
</body>
</html>