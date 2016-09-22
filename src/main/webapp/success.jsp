<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/9/12
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
    success
<shiro:hasRole name="admin">
    欢迎具有admin角色的用户<shiro:principal />
</shiro:hasRole>
<shiro:hasPermission name="student:*">
    欢迎具有所有操作user权限的用户
</shiro:hasPermission>
</body>
</html>
