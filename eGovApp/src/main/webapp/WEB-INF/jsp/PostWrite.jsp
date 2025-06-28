<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>

<link rel="stylesheet" href="<c:url value='/css/mainStyle.css'/>"/>

<script>
document.addEventListener('DOMContentLoaded', function () {
    const attachedBtn = document.getElementById('attachBtn');
    const attachedInput = document.getElementById('attachment');
    const fileCountSpan = document.getElementById('fileCount');
    const attachedList = document.getElementById('attachedList');
    const deleteFileIdsInput = document.getElementById('deleteFileIds');

    let deleteFileIds = [];
    updateFileCount();

    // 파일첨부 버튼 → input[type=file] 클릭
    attachedBtn.addEventListener('click', function () {
        attachedInput.click();
    });

    // 새 파일 선택 → 리스트에 추가
    attachedInput.addEventListener('change', function () {
        Array.from(attachedInput.files).forEach(function (file) {
            const li = document.createElement('li');
            li.innerHTML = 
            '<span>' + file.name + '</span>' +
            '<button type="button" class="remove-new">X</button>';
            
            attachedList.appendChild(li);
            
         	// 삭제 버튼 이벤트
            li.querySelector('.remove-new').addEventListener('click', function() {
                li.remove();
                updateFileCount();
            });
        });
        updateFileCount();
    });

    // 기존 파일 삭제 버튼 처리
    document.querySelectorAll('.remove-existing').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const fileId = this.dataset.fileId;
            deleteFileIds.push(fileId);
            deleteFileIdsInput.value = deleteFileIds.join(',');
            this.parentElement.remove();
            updateFileCount();
        });
    });
    
    // 파일 갯수 업데이트
    function updateFileCount() {
        const total = attachedList.querySelectorAll('li').length;
        if (total != 0){ 
        	fileCountSpan.innerHTML = '첨부 파일 ' + total + '개'; 
        } else {
        	fileCountSpan.innerText = '첨부 파일 없음';
        }
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
    		<form id="writeForm" 
    			action="<c:choose>
    						<c:when test='${post.boardId > 0}'>
    							<c:url value="/board/PostEdit.do" />
    						</c:when>					
							<c:otherwise>
								<c:url value="/board/PostWrite.do" />
							</c:otherwise>    									
    					</c:choose>" 
    			method="post" enctype="multipart/form-data">
    			<!-- 서버전송용 -->
    			<input type="hidden" name="author" value="${author}" />
    			<c:if test="${post.boardId > 0}">
    				<input type="hidden" name="boardId" value="${post.boardId}" />
    			</c:if>
    			
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
    								<input type="text" id="title" name="title" autocomplete="off" 
    										value="<c:out value="${post.title}"/>" />
    							</td>
    						</tr>
    						<tr>
    							<th>내용</th>
    							<td>
    								<textarea id="content" name="content" rows="20" cols="200" 
    									style="text-align: left;">${post.content}</textarea>
    							</td>
    						</tr>
    						<tr>
    							<th>파일추가</th>
    							<td>
    								<div class="file">
    									<button type="button" id="attachBtn" class="file-btn">파일첨부</button>
    									<span id="fileCount">첨부 파일 없음</span>
    									<input type="file" id="attachment" name="uploadFiles" multiple="multiple" style="display:none;" />
    								</div>
    							</td>
    						</tr>
    						<tr>
    							<th>파일첨부</th>
    							<td>
    								 <!-- 선택된 파일명 목록 -->
    								<ul id="attachedList">
    									<c:forEach var="file" items="${existFiles}">
    										<li>
    											<a href="${file.filePath}" target="_blank" class="file-link">${file.fileName}</a>
    											<button type="button" class="remove-existing" data-file-id="${file.fileId}">X</button>
    										</li>
    									</c:forEach>
       								</ul>
       								<input type="hidden" id="deleteFileIds" name="deleteFileIds" value="" />
    							</td>
    						</tr>
    						<tr>
    							<th>댓글 허용</th>
    							<td class="allowCmm">
    								<input type="checkbox" id="allowComment" name="allowComment" value="1"/>
    								<label for="allowComment">허용</label>
    							</td>
    						</tr>
    					</tbody>
    				</table>
    				
    				<div class="btn-box">
    					<c:choose>
    						<c:when test="${post.boardId > 0}">
    							<a class="btn_write" href="javascript:document.getElementById('writeForm').submit();">수정</a>
    							<a class="btn_cancel" href="<c:url value='/main.do' />">취소</a>
    						</c:when>
    						<c:otherwise>
    							<a class="btn_write" href="<c:url value='/main.do' />" onclick="submitPost(); return false;">등록</a>
    							<a class="btn_cancel" href="<c:url value='/main.do' />">취소</a>
    						</c:otherwise>
    					</c:choose>
    				</div>
    			</fieldset>
    		</form>
    	</div>
    </main>
</div>
</body>
</html>