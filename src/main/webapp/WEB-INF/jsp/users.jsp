<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h2>Here is a simple CRUD using Spring MVC and MongoDB.</h2>

	<table border="1">
		<c:forEach var="user" items="${userList}">
			<tr>
				<td>${user.name}</td>
				<td>${user.surname}</td>
				<td>${user.phone}</td>
				<td><input type="button" value="delete"
					onclick="window.location='delete?id=${user.id}'" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>