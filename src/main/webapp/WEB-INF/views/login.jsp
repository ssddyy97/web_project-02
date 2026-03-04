<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head><meta charset="UTF-8"><title>Login</title></head>
<body>
<h1>Login</h1>

<c:if test="${param.error == 'true'}">
    <div style="color:red">아이디 또는 비밀번호가 올바르지 않습니다.</div>
</c:if>
<c:if test="${param.logout == 'true'}">
    <div style="color:green">로그아웃 되었습니다.</div>
</c:if>

<!-- ★ loginProcessingUrl("/doLogin") 와 동일해야 함 -->
<form method="post" action="<c:url value='/doLogin'/>">
    <label>ID <input type="text" name="username"></label><br>
    <label>PW <input type="password" name="password"></label><br>
    <button type="submit">로그인</button>
</form>
<p>Don't have an account? <a href="<c:url value='/register'/>">회원가입</a></p>
</body>
</html>
