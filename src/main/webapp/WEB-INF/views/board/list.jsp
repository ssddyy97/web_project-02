<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시글 목록</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <style>
    body{background:#f7f7fb}
    .container-narrow{max-width:980px}
    .card{border:0;box-shadow:0 2px 12px rgba(16,24,40,.06)}
    .table thead th{border-bottom:2px solid #e9ecef}
  </style>
</head>
<body>
<%@ include file="header.jsp" %>

<main class="container container-narrow py-4">
  <div class="card">
    <div class="card-body">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h5 class="mb-0">게시글 목록</h5>
        <a class="btn btn-primary btn-sm" href="${ctx}/board/write?boardId=${boardId}">글쓰기</a>
      </div>

      <form action="${ctx}/post/search" method="get" class="mb-3">
        <div class="input-group">
          <input type="text" name="keyword" class="form-control" placeholder="제목 또는 내용 검색" value="${keyword}">
          <button class="btn btn-outline-secondary" type="submit">검색</button>
        </div>
      </form>

      <div class="table-responsive">
        <table class="table table-hover align-middle">
          <thead>
          <tr>
            <th style="width:100px">ID</th>
            <th>제목</th>
            <th style="width:150px">작성자</th>
            <th style="width:220px" class="text-end">작성일</th>
          </tr>
          </thead>
          <tbody>
          <c:choose>
            <c:when test="${not empty postPage.content}">
              <c:forEach items="${postPage.content}" var="post">
                <tr>
                  <td><c:out value="${post.post_id}"/></td>
                  <td>
                    <a class="text-decoration-none" href="${ctx}/post/${post.post_id}" title="<c:out value='${post.title}'/>">
                      <c:out value="${post.title}"/>
                    </a>
                  </td>
                  <td><c:out value="${post.user.name}"/></td>
                  <td class="text-end"><c:out value="${post.createdAtFormatted}"/></td>

                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr>
                <td colspan="3" class="text-center text-muted py-4">게시글이 없습니다.</td>
              </tr>
            </c:otherwise>
          </c:choose>
          </tbody>
        </table>
      </div>

      <%-- Pagination URL base --%>
      <c:set var="baseUrl" value="${ctx}" />
      <c:choose>
        <c:when test="${not empty keyword}">
          <c:set var="baseUrl" value="${ctx}/post/search?keyword=${keyword}&" />
        </c:when>
        <c:otherwise>
          <c:set var="baseUrl" value="${ctx}/board/${boardId}?" />
        </c:otherwise>
      </c:choose>

      <%-- Pagination 계산 --%>
      <c:set var="currentPage" value="${postPage.number}" />
      <c:set var="totalPages"  value="${postPage.totalPages}" />
      <c:set var="windowSize"  value="5" />

      <%-- 정수 나눗셈 효과: 윈도우 시작 --%>
      <c:set var="startPage" value="${currentPage - (currentPage % windowSize)}" />
      <c:set var="maxPage"   value="${totalPages > 0 ? totalPages - 1 : 0}" />
      <c:set var="endPage"   value="${startPage + windowSize - 1}" />
      <c:if test="${endPage > maxPage}">
        <c:set var="endPage" value="${maxPage}" />
      </c:if>

      <c:if test="${totalPages > 1}">
        <nav aria-label="페이지 이동">
          <ul class="pagination justify-content-center mb-0">
            <li class="page-item ${postPage.first ? 'disabled':''}">
              <a class="page-link" href="${baseUrl}page=0" aria-label="첫 페이지">&laquo;</a>
            </li>
            <li class="page-item ${postPage.first ? 'disabled':''}">
              <a class="page-link" href="${baseUrl}page=${currentPage - 1}" aria-label="이전">&lt;</a>
            </li>

            <c:forEach var="i" begin="${startPage}" end="${endPage}">
              <li class="page-item ${i == currentPage ? 'active':''}">
                <a class="page-link" href="${baseUrl}page=${i}">${i + 1}</a>
              </li>
            </c:forEach>

            <li class="page-item ${postPage.last ? 'disabled':''}">
              <a class="page-link" href="${baseUrl}page=${currentPage + 1}" aria-label="다음">&gt;</a>
            </li>
            <li class="page-item ${postPage.last ? 'disabled':''}">
              <a class="page-link" href="${baseUrl}page=${totalPages - 1}" aria-label="마지막">&raquo;</a>
            </li>
          </ul>
        </nav>
      </c:if>
    </div>
  </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
