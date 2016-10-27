<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/9/12
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
    <script src="${pageContext.request.contextPath}/static/js/lib/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/QRcode.js"></script>
    <script>
        var ctx = "${pageContext.request.contextPath}/";
    </script>
</head>
<body>

    <div style="border: 1px; border-color: aqua;width: 400px;height: 300px" id="view">
        success
    </div>
    <label id="insert"></label>
    <input type="button" value="提交" name="submit" id="submit" />
</body>
</html>
