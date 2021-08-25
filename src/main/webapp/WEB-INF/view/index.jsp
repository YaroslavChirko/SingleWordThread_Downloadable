<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.singleword.db.entity.SingleWordMessage"  %>
<%@page isELIgnored="false" %>
<html>
<head>
<title>Welcome to single word thread</title>
<link rel="stylesheet" href="<c:url value='css/index.css'/>">
</head>

<body>
	<div class="threads">
		<c:forEach items="${threads}" var="thread">
			<a href="chat/${thread.name}"><c:out value="${thread.name}"></c:out></a>
			<br>
			<br>
		</c:forEach>
	</div>
	
	<div class="inputs">
		<form action="" method="post">
			<textarea name="threadName" maxlength="50"></textarea>
			<input type="submit" value="send">
		</form>
	</div>
</body>
</html>
