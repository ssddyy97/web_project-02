<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title><c:out value="${post.title}"/> - 게시글</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<%@ include file="header.jsp" %> <%-- 경로 수정 --%>

<main class="container py-4" style="max-width: 980px;">
  <div class="card mb-3">
    <div class="card-body">
      <h3 class="card-title mb-2"><c:out value="${post.title}"/></h3>
      <div class="text-muted small mb-3">
        작성일: <c:out value="${post.createdAtFormatted}"/>
      </div>
      <div class="card-text" style="white-space:pre-wrap;">
        <c:out value="${post.body}"/>
      </div>

      <div class="mt-3 d-flex justify-content-end">
        <a href="${ctx}/board/${post.board.board_id}" class="btn btn-sm btn-secondary me-2">목록으로 돌아가기</a>

        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="principal" var="currentUser" />
            <c:if test="${currentUser.role == 'ADMIN' || currentUser.user_id == post.user.user_id}">
                <a href="${ctx}/post/edit/${post.post_id}" class="btn btn-sm btn-outline-primary me-2">수정</a>
                <form action="${ctx}/post/delete/${post.post_id}" method="post" onsubmit="return confirm('정말로 이 게시글을 삭제하시겠습니까?');">
                  <button type="submit" class="btn btn-sm btn-outline-danger">삭제</button>
                </form>
            </c:if>
        </sec:authorize>
      </div>
    </div>
  </div>

  <h5 class="mb-3">댓글</h5>
<c:choose>
  <c:when test="${not empty commentsPage.content}">
    <c:forEach var="commentItem" items="${commentsPage.content}">

      <c:if test="${empty commentItem.parent}">
        <c:set var="comment" scope="request" value="${commentItem}"/>
        <c:set var="level"   scope="request" value="0"/>
        <c:set var="postId"  scope="request" value="${post.post_id}"/>
        <jsp:include page="commentFragment.jsp"/>
      </c:if>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <div class="text-muted">댓글이 없습니다.</div>
  </c:otherwise>
</c:choose>

  <hr class="my-4"/>

  <form method="post" action="${ctx}/post/${post.post_id}/comment">
    <div class="mb-3">
      <label class="form-label">댓글 내용</label>
      <textarea name="content" class="form-control" rows="3" required></textarea>
    </div>
    <button type="submit" class="btn btn-primary">댓글 등록</button>
  </form>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
