<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Anagram Assignment 1</title>
</head>
<body>
<!-- if the user is logged in then we need to render one version of the page
consequently if the user is logged out we need to render a different version of the page
-->
	<c:choose>
		<c:when test = "${user != null}">
		<p> Welcome ${user.email} <br/>
			You can sign out <a href="${logout_url}">"here"</a><br/> 
			
			</p>
			
			
			<form action="/AnagramServlet" method="get">
					Search Anagram Word : <input type="text" name="searchword">
					<input type="submit" value="Search" >
				</form>	<br/> 
				 
				<form action="/AnagramServlet" method="post">
					Add Anagram Word : <input type="text" name="addword">
					<input type="submit" value="Add" >
				</form>	
				
				<p><b>${output}</b></p>
				
				<p>
				<c:forEach var="word" items="${list}">
				    <c:out value="${word}"/><br/> 
				</c:forEach>
				</p>
		</c:when>
	<c:otherwise>
		<p>Welcome!<a href="${login_url}">Sign in or register</a></p>
	</c:otherwise>
	</c:choose>
</body>
</html>