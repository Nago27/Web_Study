<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/register.css' />"/>
</head>
<body>
<div class="join-container">
	<h2>회원가입</h2>
	
	<c:if test="${!empty joinError}">
		<div class="error">${joinError}</div>
	</c:if>
	
	<form action="<c:url value='/RegisterCheck.do' />" method="post" class="join-form">
		<div class="form-group">
			<label for="user_id">아이디</label>
			<input type="text" id="user_id" name="user_id" maxlength="30" placeholder="아이디를 입력하세요(30자 이내)"
				value="${loginVO.user_id != null ? loginVO.user_id : ''}" required />
		</div>
		
		<div class="form-group">
        	<label for="password">비밀번호</label>
            <input type="password" id="password" name="password" maxlength="255" placeholder="비밀번호" required />
        </div>
        
        <div class="form-group">
        	<label for="confirmPwd">비밀번호 확인</label>
            <input type="password" id="confirmPwd" name="confirmPwd" maxlength="255" placeholder="비밀번호 확인" 
            value="${param.confirmPwd != null ? param.confirmPwd : ''}" required />
        </div>
        
        <div class="form-group">
			<label for="name">이름</label>
			<input type="text" id="name" name="name" placeholder="이름"
				value="${loginVO.name != null ? loginVO.name : ''}" required />
		</div>
		
		<div class="form-group">
			<label for="birth">생년월일</label>
			<input type="date" id="birth" name="birth" value="${loginVO.birth != null ? loginVO.birth : ''}" required />
		</div>
		
		<div class="form-group">
			<label for="email">이메일</label>
			<input type="email" id="eamil" name="email" maxlength="50" value="${loginVO.email != null ? loginVO.email : ''}" required />
		</div>
		
		<div class="form-group">
			<label for="phone">휴대폰 번호(선택)</label>
			<input type="text" id="phone" name="phone" value="${loginVO.phone != null ? loginVO.phone : ''}" required />
		</div>
		
		<div class="form-group">
			<label for="user_se">사용자 구분</label>
			<select id="user_se" name="user_se">
				<option value="">선택하세요</option>
				<option value="GENERAL" ${loginVO.user_se == 'GENERAL'?'selected':''}>일반인</option>
				<option value="PIC" ${loginVO.user_se == 'PIC'?'selected':''}>담당자</option>
			</select>
		</div>
		
		<div class="form-actions">
        	<input type="submit" class="btn primary" value="가입하기" />
            <a href="<c:url value='/LoginUsr.do' />" class="btn secondary">로그인으로 돌아가기</a>
       	</div>
	</form>
</div>
</body>
</html>