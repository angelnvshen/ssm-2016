<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>  
  <head>  
    <title>测试</title>  
  </head>  
    
  <body>  
    ${user.userName}
    <br>
    ${people.userName}

  <br>

    <c:forEach items="${list}" var="people">
        ${people.userName} | ${people.age} <br>
    </c:forEach>

  </body>  
</html>  