<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    
<div class="sidebar" id="sidebar">
	<h2>관리자 메뉴</h2>
	<div class="menu-item"><a class="menu-item" href="<c:url value='/manager/project'/> ">프로젝트 관리</a></div>
	<div class="menu-item"><a class="menu-item" href="<c:url value='/manager/user'/> ">사용자 관리</a></div>
	<div class="menu-item"><a class="menu-item" href="<c:url value='/manager/role'/> ">권한 관리</a></div>
	<div class="menu-item"><a class="menu-item" href="<c:url value='/calendar'/> ">캘린더로 돌아가기</a></div>
</div>