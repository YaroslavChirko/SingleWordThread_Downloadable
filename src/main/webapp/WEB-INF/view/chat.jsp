<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.singleword.db.entity.SingleWordMessage"  %>
<%@page isELIgnored="false" %>
<html>
<head>
<title>Welcome to "${threadName}"</title>
<link rel="stylesheet" href="<c:url value='css/index.css'/>">
</head>

<body>
	<div class="messages">
		<c:forEach items="${messages}" var="message">
			<p><c:out value="${message.word}"></c:out>
			<p><c:out value="${message.randomName}"></c:out>
			<p><c:out value="${message.sentAt}"></c:out>
			<br>
			<br>
		</c:forEach>
	</div>
	
	<div class="inputs">
		<form action="${threadName}" method="post">
			<textarea name="message" maxlength="50"></textarea>
			<input type="submit" value="send">
		</form>
		
		<button onclick="window.location.href = 'download'">download</button>
	</div>
</body>
</html>
