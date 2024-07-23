<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link type="text/css" rel="stylesheet" href='<c:url value="/css/calendar.css" />' />
<link type="text/css" rel="stylesheet" href='<c:url value="/bootstrap-5.1.3/css/bootstrap.min.css" />' />
<title>OurCalendar</title>
</head>
<body>
	<jsp:include page="/WEB-INF/includes/header.jsp" />
	<jsp:include page="/WEB-INF/includes/nav.jsp" />

	<main>
		<section class="calendar">
			<table>
				<thead>
					<tr>
						<th>일</th>
						<th>월</th>
						<th>화</th>
						<th>수</th>
						<th>목</th>
						<th>금</th>
						<th>토</th>
					</tr>
				</thead>
				<tbody>
				<!--  javascript로 생성 -->
				</tbody>
			</table>
		</section>
		<aside class="right-panel">
		
			<!-- 프로젝트 생성 버튼 추가 -->
			<div class="create-project">
				<a id="createProjectBtn" class="btn btn-primary"
					href="<c:url value='/project/create' />">프로젝트 생성</a>
			</div>
			<c:if test="${ not empty projectListByDate }">
			    <c:forEach items="${ projectListByDate }" var="vo" varStatus="status">
			        <div class="card mb-3">
			            <div class="card-body">
			                <h5 class="card-title">
			                    <a href="<c:url value='/project' />/${vo.projectId}">${ vo.projectTitle }</a>
			                </h5>
			                <p class="card-text">
			                    <fmt:formatDate value="${vo.startDate}" pattern="MM/dd" />
			                    ~
			                    <fmt:formatDate value="${vo.endDate}" pattern="MM/dd" />
			                </p>
			            </div>
			        </div>
			    </c:forEach>
			</c:if>
		</aside>
	</main>
	 <script>
	 	// 뜨는 위치 고쳐야함

	 
	 
        // 서버에서 받은 프로젝트 데이터를 JavaScript 변수로 저장
        var projectList = [
            <c:forEach items="${projectListByMonth}" var="project" varStatus="status">
                {
                    id: ${project.projectId},
                    title: "${project.projectTitle}",
                    startDate: <fmt:formatDate value="${project.startDate}" pattern="yyyyMMdd" />,
                    endDate: <fmt:formatDate value="${project.endDate}" pattern="yyyyMMdd" />,
                    projectStatus: "${project.projectStatus}"
                }<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];
    </script>
	<script src="<c:url value='/bootstrap-5.1.3/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/javascript/main.js' />"></script>
	<script src="<c:url value='/javascript/calendar.js' />"></script>
</body>
</html>