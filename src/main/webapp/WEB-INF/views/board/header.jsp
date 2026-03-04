<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="site-nav" role="navigation" aria-label="주 메뉴">
  <div class="menu">
    <div class="menu__item menu__item--right">
      <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="currentUser" />
        <span style="margin-right: 10px;"><c:out value="${currentUser.name}"/>님 환영합니다!</span>
        <a class="menu__btn" href="<c:url value='/logout'/>">로그아웃</a>
      </sec:authorize>
      <sec:authorize access="!isAuthenticated()">
        <a class="menu__btn" href="<c:url value='/login'/>">로그인</a>
      </sec:authorize>
    </div>
  </div>
</nav>

<style>
  .menu, .menu ul {list-style:none;margin:0;padding:0}
  .site-nav{background:#fff;border-bottom:1px solid #e0eef;position:sticky;top:0;z-index:100}
  .menu{max-width:1000px;margin:0 auto;padding:10px 16px;display:flex;align-items:center}
  .menu__item--right{margin-left:auto}
  .menu__btn{display:inline-block;padding:10px 14px;border-radius:10px;text-decoration:none;color:#fff;background:#0d6efd;box-shadow:0 2px 8px rgba(13,110,253,.25)}
  .menu__btn:hover,.menu__btn:focus{background:#0b5ed7;box-shadow:0 4px 12px rgba(13,110,253,.35)}
</style>
