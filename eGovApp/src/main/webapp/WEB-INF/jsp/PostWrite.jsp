<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>

<link rel="stylesheet" href="<c:url value='/css/mainStyle.css'/>"/>

<script>
document.addEventListener('DOMContentLoaded', function(){
	  var btn   = document.getElementById('attachBtn');
	  var input = document.getElementById('attachment');
	  var list  = document.getElementById('attachedList');
	  var files = [];

	  // 버튼 클릭 → 파일 선택창
	  btn.addEventListener('click', function(){
	    input.click();
	  });

	  // 파일 선택 시
	  input.addEventListener('change', function(){
	    Array.from(input.files).forEach(function(f){
	      // 중복 검사
	      var exists = files.some(function(x){
	        return x.name===f.name && x.size===f.size && x.lastModified===f.lastModified;
	      });
	      if (!exists) files.push(f);
	    });
	    render();
	  });

	  // 리스트 클릭(❌ 삭제)
	  list.addEventListener('click', function(e){
	    if (e.target.classList.contains('remove-btn')) {
	      var idx = parseInt(e.target.getAttribute('data-index'), 10);
	      files.splice(idx, 1);
	      render();
	    }
	  });

	  // 렌더 + input.files 동기화
	  function render(){
	    list.innerHTML = '';
	    var dt = new DataTransfer();
	    files.forEach(function(f, i){
	      dt.items.add(f);
	      var li = document.createElement('li');
	      li.innerHTML = 
	        '<span>'+ f.name +'</span>' +
	        '<button type="button" class="remove-btn" data-index="'+ i +'">X</button>';
	      list.appendChild(li);
	    });
	    input.files = dt.files;
	  }
	});
	
function submitPost(){
    document.getElementById('writeForm').submit();
 }
</script>
</head>
<body>
<div id="wrapper">
	<header id="header">
		<div id="user-info">
			<strong><c:out value="${name}" /></strong>님, 환영합니다.&nbsp;
			<a href="<c:url value='/Logout.do'/>">Logout</a>
		</div>
	</header>
	<nav class="gnb">
        <ul>
            <li>
                <a href="#none">게시판</a>
                <ol style="display: none; height: 200px; padding-top: 15px; margin-top: 0; padding-bottom: 20px; margin-bottom: 0;">
                    <li><a href="<c:url value='/main.do'/>">공지사항</a></li>
                </ol>
            </li>
            <li>
                <a href="#none">바로가기</a>
                <ol style="display: none; height: 200px; padding-top: 15px; margin-top: 0; padding-bottom: 20px; margin-bottom: 0;">
                    <li><a href="https://www.gongdan.go.kr">홈페이지</a></li>
                    <li><a href="<c:url value='/Attn.do'/>">근태관리</a></li>
                </ol>
            </li>
        </ul>
    </nav>
    
    <main id="main" class="board-page">
    	<div class="post_contents">
    		<h2 class="headline">공지사항</h2>
    		<form id="writeForm" action="<c:url value='/PostWrite.do'/>" method="post" enctype="multipart/form-data">
    			<!-- 서버전송용 -->
    			<input type="hidden" name="author" value="${author}" />
    			
    			<!-- 글 작성 도구 -->
    			<fieldset>
    				<legend>게시글 작성</legend>
    				<table class="table_box">
    					<colgroup>
    						<col width="100px" />
    						<col width="auto" />
    					</colgroup>
    					<tbody>
    						<tr>
    							<th>제목</th>
    							<td>
    								<input type="text" id="title" name="title" autocomplete="off" />
    							</td>
    						</tr>
    						<tr>
    							<th>내용</th>
    							<td>
    								<textarea id="content" name="content" rows="20" cols="200" ></textarea>
    							</td>
    						</tr>
    						<tr>
    							<th>파일추가</th>
    							<td>
    								<div class="file">
    									<button type="button" id="attachBtn" class="file-btn">파일첨부</button>
    									 <input type="file" id="attachment" name="uploadFiles" multiple style="display:none;" />
    									 
    									 <!-- 선택된 파일명 목록 -->
    									 <ul id="attachedList"></ul>
    								</div>
    							</td>
    						</tr>
    						<tr>
    							<th>댓글 허용</th>
    							<td>
    								<input type="checkbox" id="allowComment" name="allowComment"/>
    								<label for="comment_allow">허용</label>
    							</td>
    						</tr>
    					</tbody>
    				</table>
    				
    				<div class="btn-box">
    					<a class="btn_write" href="<c:url value='/main.do' />" onclick="submitPost(); return false;">등록</a>
    					<a class="btn_cancel" href="<c:url value='/main.do' />">취소</a>
    				</div>
    			</fieldset>
    		</form>
    	</div>
    </main>
</div>
</body>
</html>