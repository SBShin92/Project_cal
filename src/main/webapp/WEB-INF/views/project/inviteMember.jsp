<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Invite Member</title>
</head>
<body>
    <h2>Invite Member</h2>

    <c:if test="${not empty message}">
        <div style="color:green;">${message}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div style="color:red;">${error}</div>
    </c:if>

    <h3>All Users</h3>
    <table border="1">
        <thead>
            <tr>
                <th>User ID</th>
                <th>User Name</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${allUsers}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.userName}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/inviteMember/add" method="post">
                            <input type="hidden" name="userId" value="${user.userId}" />
                            <input type="hidden" name="projectId" value="${projectId}" />
                            <button type="submit">Add</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/inviteMember/remove" method="post">
                            <input type="hidden" name="userId" value="${user.userId}" />
                            <input type="hidden" name="projectId" value="${projectId}" />
                            <button type="submit">Remove</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
<<<<<<< HEAD
</html>
=======
</html>
>>>>>>> refs/remotes/origin/develop
