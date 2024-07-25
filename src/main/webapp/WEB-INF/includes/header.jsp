<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page session="true"%>
<header>
	<div class="logo">
		<a href="<c:url value='/calendar'/>">CompanyCal</a>
	</div>
	
	<nav class="top-nav">
		<button class="nav-btn" id="message-btn">쪽지함 (${ sessionScope.messageUnreadCount })</button>
		
		<c:if test="${authUser.userAuthority == 'admin'}">
            <a href="<c:url value='/manager' />" class="nav-btn btn-dark">관리자페이지</a>
        </c:if>
        
	</nav>

	<c:if test="${ sessionScope.authUserRole.projectRead == true }">
		<div class="search-div">
			<form action="<c:url value='/SearchProjectTasks'/>" method="get" class="search-form">
				<input type="text" name="taskProjectTitle" placeholder="search projects, tasks" required>
				<button type="submit" class="btn btn-dark">검색</button>
			</form>
		</div>
	</c:if>
	
	<div class="user-profile-container">
		<div class="user-profile">${sessionScope.userName != null ? sessionScope.userName : '사용자'}	▼</div>
		<div class="user-menu" style="display: none;">
			<a href="<c:url value='/login/logout'/>" class="menu-item" id="logoutButton">로그아웃</a>
		</div>
	</div>
</header>