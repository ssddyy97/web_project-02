<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head><meta charset="UTF-8"><title>Register</title></head>
<body>
<h1>Register</h1>

<c:if test="${not empty errorMessage}">
    <div style="color:red"><c:out value="${errorMessage}"/></div>
</c:if>
<c:if test="${not empty successMessage}">
    <div style="color:green"><c:out value="${successMessage}"/></div>
</c:if>

<form method="post" action="<c:url value='/register'/>">
    <label>Username <input type="text" name="username" required></label><br>
    <label>Password <input type="password" name="password" required></label><br>
    <label>Email <input type="email" name="email"></label><br>
    <button type="submit">Register</button>
</form>
<p>Already have an account? <a href="<c:url value='/login'/>">Login here</a></p>
</body>
</html>