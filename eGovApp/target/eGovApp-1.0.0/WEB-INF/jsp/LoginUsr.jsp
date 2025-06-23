<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈페이지 로그인</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/login.css' />"/>
</head>
<body>
<div class="container">
	<h2>로그인</h2>
	<form action="<c:url value='/LogCheck.do' />" method="post"> 
		<input type="text" name="user_id" placeholder="아이디" />
		<input type="password" name="password" placeholder="비밀번호" />
		<input type="submit" class="login_btn" value="로그인" />
	</form>
	
	<!-- 메세지 출력 -->
	<c:if test="${!empty message}">
		<p style="color: red;">${message}</p>
	</c:if>
	
	<div class="signup">
		<p>계정이 없으신가요?</p>
		<a href="<c:url value='/RegisterPage.do' />">회원가입</a>
	</div>
</div>
</body>
</html>