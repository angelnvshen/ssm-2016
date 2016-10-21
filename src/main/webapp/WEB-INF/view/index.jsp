<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>

    <script src="${pageContext.request.contextPath}/static/js/lib/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/lib/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/lib/messages_zh.js"></script>
    <script>
    $.validator.setDefaults({
        submitHandler: function() {
          alert("提交事件!");
        }
    });
    $().ready(function() {
        $("#commentForm").validate();
    });
    </script>
<body>
<h2>Hello World!</h2>

    <form class="cmxform" id="commentForm" method="get" action="">
      <fieldset>
        <legend>输入您的名字，邮箱，URL，备注。</legend>
        <p>
          <label for="cname">Name (必需, 最小两个字母)</label>
          <input id="cname" name="name" minlength="2" type="text" required>
        </p>
        <p>
          <label for="cemail">E-Mail (必需)</label>
          <input id="cemail" type="email" name="email" required>
        </p>
        <p>
          <label for="curl">URL (可选)</label>
          <input id="curl" type="url" name="url">
        </p>
        <p>
          <label for="ccomment">备注 (必需)</label>
          <textarea id="ccomment" name="comment" required></textarea>
        </p>
        <p>
          <input class="submit" type="submit" value="Submit">
        </p>
      </fieldset>
    </form>
</body>
</html>
