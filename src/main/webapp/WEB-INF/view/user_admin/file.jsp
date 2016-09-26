<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

	<h1>上传文件</h1>
	<form method="post" action="<c:url value='/user/doUpload' />" enctype="multipart/form-data">
		<input type="file" name="file"/>
        <br />
		<input type="submit" value="上传文件"/>
		
	</form>
</body>
</html>