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
				
				<!-- 첨부파일 
				<c:if test="${not empty fileList}">
					<ul class="list_show">
						<c:forEach var="file" items="${fileList}">
							<li>
								<span>첨부파일</span>
								<a href="${file.filePath}">
									<c:out value="${file.fileName}" />
								</a>
							</li>
						</c:forEach>
					</ul>
				</c:if>
				-->
			</div>
			
			<div class="btn-box">
				<a class="btn_cancel" href="<c:url value='/main.do' />">목록</a>
			</div>
			
			<!-- 댓글 등록 (댓글 허용했을 시)
			<c:if test="${post.allowComment == true}">
				<c:forEach var="cmm" items="${comments}">
					<c:if test="${cmm.boardId == post.boardId}">
						<form id="commentForm">
							<h3 class="cmm_title">댓글</h3>
							<ul>
								<li>
									<textarea id="comment" name="comment" rows="5" cols="60"></textarea>				
								</li>
								<li>
									<div class="btn_comment">
										<a class="btn_write">댓글등록</a>
									</div>
								</li>
							</ul>
						</form>
						
						
						<table class="table_cmm">
							<colgroup>
								<col width="170px">
								<col width="750px">
								<col width="50px">
							</colgroup>
							<tbody id="commentBy">
				
							</tbody>
						</table>
					</c:if>
				</c:forEach>
			</c:if>-->
		</div>
	</main>
</div>
</body>
</html>