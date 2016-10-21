<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<%
    String path = request.getContextPath();
%>
<script text="text/javascript">
    var ctx  = "${pageContext.request.contextPath}";
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/lib/require.js"
        data-main="${pageContext.request.contextPath}/static/js/require/problem_path"></script>

<body>
<h2>Hello World!</h2>
<p>RequireJS异步加载测试</p>
</body>
</html>
