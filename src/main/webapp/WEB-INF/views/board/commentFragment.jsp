<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Retrieve parameters from request attributes --%>
<c:set var="currentComment" value="${requestScope.comment}" />
<c:set var="currentLevel" value="${requestScope.level}" />
<c:set var="currentPostId" value="${requestScope.postId}" />

<div class="card mb-2 comment-level-${currentLevel}" style="margin-left: ${currentLevel * 20}px;">
    <div class="card-body">
        <p class="card-text"><c:out value="${currentComment.content}"/></p>
        <p class="card-subtitle text-muted text-end small">
            <c:out value="${currentComment.createdAtFormatted}"/>
        </p>

        <!-- 답글 버튼 & 폼 토글 (Bootstrap 5) -->
        <button class="btn btn-sm btn-link p-0" type="button"
                data-bs-toggle="collapse"
                data-bs-target="#replyForm${currentComment.id}">
            답글
        </button>

        <div id="replyForm${currentComment.id}" class="collapse mt-2">
            <form action="${ctx}/post/${currentPostId}/comment" method="post" class="needs-validation" novalidate onsubmit="disableButton(this)">
                <c:if test="${not empty _csrf}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </c:if>
                <input type="hidden" name="parentId" value="${currentComment.id}">
                <div class="form-group mb-2">
                    <textarea class="form-control" name="content" rows="2" placeholder="답글을 입력하세요" required></textarea>
                    <div class="invalid-feedback">답글을 입력하세요.</div>
                </div>
                <div class="text-end">
                    <button type="submit" class="btn btn-sm btn-primary">답글 등록</button>
                </div>
            </form>
        </div>

        <!-- 대댓글 재귀 렌더링 -->
        <c:if test="${not empty currentComment.children}">
            <c:forEach items="${currentComment.children}" var="reply">
                <c:set var="comment" scope="request" value="${reply}"/>
                <c:set var="level" scope="request" value="${currentLevel + 1}"/>
                <c:set var="postId" scope="request" value="${currentPostId}"/> <%-- Pass postId recursively --%>
                <jsp:include page="commentFragment.jsp"/>
            </c:forEach>
        </c:if>
    </div>
</div>

<script>
    function disableButton(form){
        const b=form.querySelector('button[type="submit"]');
        if(b){b.disabled=true; b.textContent='등록 중...';}
    }
</script>
