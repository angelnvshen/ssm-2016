<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/9/12
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    ${errorInfo}
    <form action="login" method="post">
        userName:<input type="text" name="userName" /> <br>
        password:<input type="password" name="password" /> <br>
        <input type="submit" name="submit" value="提交" />
    </form>
</body>
</html>
