<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="isEdit" value="${not empty post and not empty post.post_id}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title><c:out value="${isEdit ? '게시글 수정' : '게시글 작성'}"/></title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <style>
    body{background:#f7f7fb}
    .container-narrow{max-width:900px}
    .card{border:0;box-shadow:0 2px 12px rgba(16,24,40,.06)}
  </style>
</head>
<body>
<%@ include file="header.jsp" %>

<c:choose>
  <c:when test="${isEdit}">
    <c:set var="cancelUrl" value="${ctx}/post/${post.post_id}" />
    <c:set var="actionUrl" value="${ctx}/post/edit/${post.post_id}" />
  </c:when>
  <c:otherwise>
    <c:set var="cancelUrl" value="${ctx}/board/${boardId}" />
    <c:set var="actionUrl" value="${ctx}/board/writepro" />
  </c:otherwise>
</c:choose>

<main class="container container-narrow py-4">
  <div class="card">
    <div class="card-body">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h5 class="mb-0"><c:out value="${isEdit ? '게시글 수정' : '게시글 작성'}"/></h5>
        <a class="btn btn-outline-secondary btn-sm" href="${cancelUrl}">취소</a>
      </div>

      <form action="${actionUrl}" method="post" class="needs-validation" novalidate>
        <c:if test="${not empty _csrf}">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </c:if>

        <c:if test="${!isEdit}">
          <!-- 새 글: 보드 식별자 필요 -->
          <input type="hidden" name="boardId" value="${boardId}">
        </c:if>
        <c:if test="${isEdit}">
          <!-- 컨트롤러 정책에 따라 필요 시 -->
          <input type="hidden" name="post_id" value="${post.post_id}">
        </c:if>

        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="title" name="title"
                 placeholder="제목" value="<c:out value='${post.title}'/>"
                 required maxlength="200">
          <label for="title">제목</label>
          <div class="invalid-feedback">제목을 입력하세요.</div>
        </div>

        <div class="form-floating mb-3">
          <textarea class="form-control" id="body" name="body" placeholder="내용" style="height:320px" required><c:out value="${post.body}"/></textarea>
          <label for="body">내용</label>
          <div class="invalid-feedback">내용을 입력하세요.</div>
        </div>

        <div class="d-flex gap-2">
          <button type="submit" class="btn btn-primary">
            <c:out value="${isEdit ? '수정 완료' : '작성 완료'}"/>
          </button>
          <a class="btn btn-light" href="${cancelUrl}">취소</a>
        </div>
      </form>
    </div>
  </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  (() => {
    const f=document.querySelector('.needs-validation'); if(!f) return;
    f.addEventListener('submit', e => {
      if(!f.checkValidity()){ e.preventDefault(); e.stopPropagation(); }
      f.classList.add('was-validated');
    }, false);
  })();
</script>
</body>
</html>
