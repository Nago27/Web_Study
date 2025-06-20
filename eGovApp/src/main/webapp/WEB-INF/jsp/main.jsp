<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 홈페이지</title>
<link rel="stylesheet" href="<c:url value='/css/mainStyle.css'/>"/>
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
			<div class="searchBox">
				<form id="searchForm" name="searchForm" method="get" action="<c:url value='/main.do' />">
					<!-- 검색 조건 -->
					<select name="searchType">
						<option value="1" ${searchVO.searchType=='1' ? 'selected':''}>제목</option>
						<option value="2" ${searchVO.searchType=='2' ? 'selected':''}>작성자</option>
						<option value="3" ${searchVO.searchType=='3' ? 'selected':''}>내용</option>
					</select>
					<input type="text" name="searchText"  value="${fn:escapeXml(searchVO.searchText)}" autocomplete="off"/>
					<button type="submit" id="search_btn">검색</button>
					<button type="button" id="searchAll" onclick="<c:url value='/BoardView.do' />">전체보기</button>
					
					<!-- 페이징 파라미터 유지용 hidden -->
          			<input type="hidden" name="pageIndex" value="${searchVO.pageIndex}" />
          			<input type="hidden" name="pageUnit"  value="${searchVO.pageUnit}" />
          			<input type="hidden" name="pageSize"  value="${searchVO.pageSize}" />
				</form>
			</div>
			<table class="table_box">
				<colgroup>
                	<col width="100px">
                	<col width="auto">
                	<col width="120px">
                	<col width="120px">
                	<col width="70px">
                </colgroup>
                <thead>
                	<tr>
                		<th>번호</th>
                		<th>제목</th>
                		<th>작성자</th>
                		<th>작성일</th>
                		<th>조회수</th>
                	</tr>
                </thead>
                <tbody>
                	<c:forEach var="post" items="${resultList}">
                		<tr style="height:40px;">
                			<td><c:out value="${post.id}"/></td>
                			<td>
                				<a href="<c:url value='/main.do'>
                							<c:param name='id' value='${post.id}'/>
                						</c:url>">
                					<c:out value="${post.title}"/>
                				</a>
                			</td>
                			<td><c:out value="${post.author}"/></td>
                			<td><fmt:formatDate value="${post.created_at}" pattern="yyyy-MM-dd"/></td>
                			<td><c:out value="${post.views}"/></td>
                		</tr>
                	</c:forEach>
                	<c:if test="${empty resultList}">
                    <tr>
                        <td colspan="5">등록된 게시물이 없습니다.</td>
                    </tr>
                </c:if>
                </tbody>
			</table>
			<div class="num_page">
				 <ul>
    				<!-- “처음” / “이전” 버튼 -->
    				<c:if test="${paginationInfo.hasPreviousPage()}">
      					<li>
        					<a href="javascript:goPage(${paginationInfo.firstPageNo});">&laquo;&laquo;</a>
      					</li>
      					<li>
        					<a href="javascript:goPage(${paginationInfo.previousPageNo});">&laquo;</a>
      					</li>
    				</c:if>

    				<!-- 페이지 번호 반복 -->
    				<c:set var="start" value="${paginationInfo.firstPageNoOnPageList}" />
    				<c:set var="end"   value="${paginationInfo.lastPageNoOnPageList}" />
    				<c:forEach var="i" begin="${start}" end="${end}">
      					<li class="${i == paginationInfo.currentPageNo ? 'active' : ''}">
       						<a href="javascript:goPage(${i});">${i}</a>
      					</li>
    				</c:forEach>

    				<!-- “다음” / “마지막” 버튼 -->
					<c:if test="${paginationInfo.hasNextPage()}">
      					<li>
        					<a href="javascript:goPage(${paginationInfo.nextPageNo});">&raquo;</a>
      					</li>
      					<li>
        					<a href="javascript:goPage(${paginationInfo.lastPageNo});">&raquo;&raquo;</a>
      					</li>
    				</c:if>
  				</ul>
			</div>
		</div>
		<div class="write_btn_container">
			<a href="<c:url value='/WritePage.do'/>" class="write_btn">작성하기</a>
		</div>
	</main>
</div>
<script>
  function goPage(pageNo) {
    document.searchForm.pageIndex.value = pageNo;
    document.searchForm.submit();
  }
</script>
</body>
</html>