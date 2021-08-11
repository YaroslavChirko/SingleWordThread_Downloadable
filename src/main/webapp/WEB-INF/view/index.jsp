<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.singleword.db.entity.SingleWordMessage"  %>
<html>
<head>
Welcome to single word thread
</head>

<body>
	<c:forEach items="${messages}" var="message">
		<p><c:out value="${message.word}"></c:out>
		<p><c:out value="${message.randomName}"></c:out>
		<p><c:out value="${message.sentAt}"></c:out>
	</c:forEach>
	
	
	<form action="" method="post">
		<textarea name="message" maxlength="50"></textarea>
		<input type="submit" value="send">
	</form>
</body>
</html>
