

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>

    <script src="${pageContext.request.contextPath}/static/js/require/require.js"></script>
    <script>
        require(['math'], function (math){
    　　　　alert(math.add(1,1));
    　　});
    </script>
<body>
<h2>Hello World!</h2>


</body>
</html>
