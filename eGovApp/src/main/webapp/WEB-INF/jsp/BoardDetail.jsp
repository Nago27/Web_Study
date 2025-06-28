<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 내용</title>
<link rel="stylesheet" href="<c:url value='/css/mainStyle.css'/>"/>
<link rel="stylesheet" href="<c:url value='/css/post.css'/>"/>
</head>
<body>
  <!-- 다운로드 에러 메시지 -->
  <c:if test="${not empty downloadErrorMsg}">
    <div class="alert alert-danger">
      ${downloadErrorMsg}
    </div>
  </c:if>
<div id="wrapper">
	<!-- 사용자 정보 및 로그아웃 -->
	<header id="header">
		<div id="user-info">
			<strong><c:out value="${name}" /></strong>님, 환영합니다.&nbsp;
			<a href="<c:url value='/Logout.do'/>">Logout</a>
		</div>
	</header>
	
	<nav class="gnb">
		<ul>
			<li>
				<a href="#none" class="active">게시판</a>
				<ol style="display: none; height: 200px; padding-top: 15px; margin-top: 0px; padding-bottom: 20px; margin-bottom: 0px;">
					<li><a href="<c:url value='/main.do'/>">공지사항</a></li>
				</ol>
			</li>
			<li>
				<a href="#none">바로가기</a>
				<ol style="display: none; height: 200px; padding-top: 15px; margin-top: 0px; padding-bottom: 20px; margin-bottom: 0px;">
					<li><a href="https://www.gongdan.go.kr">홈페이지</a></li>
					<li><a href="<c:url value='/Attn.do'/>">근태관리</a></li>
				</ol>
			</li>
		</ul>
	</nav>
	
	<main id="main" class="board-page">
		<div class="post_contents">
			<h2 class="headline">공지사항</h2>
			<!-- 게시글 상단 -->
			<c:set var="post" value="${boardData}" />
			<div class="detail_title">
				<h2><c:out value="${post.title}" /></h2>
				<ul>
					<li>작성일 : <span>${post.createdAt.toString().substring(0, 10)}</span></li>
					<li>작성자 : <span>${post.author}</span></li>
					<li>조회수 : <span>${post.views}</span></li>
				</ul>
			</div>
			<!-- 게시글 본문 -->
			<div class="detail_content" role="textbox">
				<c:out value="${post.content}" escapeXml="false" />
			</div>
			
			<!-- 첨부파일 --> 
			<ul class="fileShow">
			<c:choose>
				<c:when test="${empty fileList}">
					<li style="height:46px;">
						<span style="height:46px; padding-top:12px;">첨부파일</span>
						<b style="line-height:25px;">첨부파일이 없습니다.</b>
					</li>
				</c:when>
			 <c:otherwise>
					<li style="min-height:46px;">
						<span style="min-height:45px;">첨부파일</span>
						 <b>
						 	<c:forEach var="file" items="${fileList}">
								<a href="<c:url value='/board/download.do'>
									<c:param name="fileId" value="${file.fileId}"/>
								</c:url>">
									<c:out value="${file.fileName}" />
								</a>
							</c:forEach>
						 </b>
					</li>
			 </c:otherwise>
			</c:choose>
			</ul>
			
			<div class="btn-box">
				<c:if test="${post.author eq sessionScope.loginUser.user_id}">
					<c:url var="editUrl" value='/board/EditPage.do'>
						<c:param name="boardId" value='${post.boardId}'/>
					</c:url>
					<a class="btn_edit" href="${editUrl}">수정하기</a>
					<form id="deleteBoardForm" action="<c:url value="/board/deleteBoard.do" />"
						method="post" style="dispaly:none;">
						<input type="hidden" name="boardId" value="${post.boardId}"/>
					</form>
					<a class="btn_delete" href="javascript:document.getElementById('deleteBoardForm').submit();"
						onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
				</c:if>
					<!-- 목록 버튼 -->
				<a class="btn_cancel" href="<c:url value='/main.do' />">목록</a>
			</div>
			
			<!-- 댓글 등록 (댓글 허용했을 시) -->
			<c:if test="${post.allowComment == 1}">
				<form id="commentForm" action="<c:url value='/board/addComment.do'/>" method="post">
					<input type="hidden" name="postId" value="${post.boardId}" />
					<h3 class="cmm_title" style="margin-top: 20px;">댓글
						<span>${post.commentCnt}</span>
					</h3>
						<ul>
							<li>
								<textarea id="comment" name="content" rows="5" cols="60"></textarea>				
							</li>
							<li>
								<div class="btn_comment">
									<a href="javascript:document.getElementById('commentForm').submit();" class="btn_write">댓글등록</a>
								</div>
							</li>
						</ul>
				</form>
				<table class="table_cmm">
					<colgroup>
						<col width="170px">
						<col width="750px">
						<col width="50px">
						<col width="30px">
						<col width="auto">
					</colgroup>
					<tbody id="commentBy">
						<c:choose>
							<c:when test="${empty commentList}">
								<tr>
									<td colspan="5">댓글이 없습니다.</td>
								</tr>
							</c:when>
						 <c:otherwise>
							<c:forEach var="cmm" items="${commentList}">
								<tr>
									<th>${cmm.userId}</th>
									<td>${cmm.content}</td>
									<td>${cmm.formatDate}</td>
									<td>
										<c:if test="${cmm.userId == sessionScope.loginUser.user_id}">
											<form id="deleteForm${cmm.commentId}" action="<c:url value='/board/deleteComment.do'/>" method="post" style="display:none;">
												<input type="hidden" name="postId" value="${post.boardId}" />
												<input type="hidden" name="commentId" value="${cmm.commentId}" />
											</form>
											<a href="#none" class="btn_delete_cmm"
											   onclick="if(confirm('댓글을 삭제할까요?')) document.getElementById('deleteForm${cmm.commentId}').submit(); return false;">X</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>	
						 </c:otherwise>
						</c:choose>	
					</tbody>
				</table>
			</c:if>
		</div>
	</main>
</div>
</body>
</html>