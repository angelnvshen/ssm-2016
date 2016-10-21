<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>

    <script src="${pageContext.request.contextPath}/static/js/lib/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/lib/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/lib/messages_zh.js"></script>
    <script>
   /*
    * 默认的submit
   $.validator.setDefaults({
        submitHandler: function() {
          alert("提交事件!");
        }
    });*/
    $().ready(function() {
        $("#commentForm").validate({
            rules:{
                username:{
                    required:true,
                    minlength:2
                },
                password:{
                    required:true,
                    minlength:5
                },
                confirm_password: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                },
                email: {
                    required: true,
                    email: true
                }
            },
            messages:{
                username: {
                    required: "请输入用户名",
                    minlength: "用户名必需由两个字母组成"
                },
                password: {
                    required: "请输入密码",
                    minlength: "密码长度不能小于 5 个字母"
                },
                confirm_password: {
                    required: "请输入密码",
                    minlength: "密码长度不能小于 5 个字母",
                    equalTo: "两次密码输入不一致"
                },
                email: "请输入一个正确的邮箱"
            },
            /**
             * 用其他方式替代默认的 SUBMIT
             * 如果想提交表单, 需要使用 form.submit()，而不要使用 $(form).submit()。
             */
            submitHandler:function(form){
                alert("提交事件!");
                /*form.submit();*/
                $(form).ajaxSubmit();
            },
            /**
             * debug，只验证不提交表单
             */
           /* debug:true*/
        });
    });
    </script>
<body>
<h2>Hello World!</h2>

    <form class="cmxform" id="commentForm" method="get" action="">
      <fieldset>
        <legend>输入您的名字，邮箱，URL，备注。</legend>
          <p>
              <label for="username">用户名</label>
              <input id="username" name="username" type="text">
          </p>
          <p>
              <label for="password">密码</label>
              <input id="password" name="password" type="password">
          </p>
          <p>
              <label for="confirm_password">验证密码</label>
              <input id="confirm_password" name="confirm_password" type="password">
          </p>
          <p>
              <label for="email">Email</label>
              <input id="email" name="email" type="email">
          </p>
        <p>
          <input class="submit" type="submit" value="Submit">
        </p>
      </fieldset>
    </form>
</body>
</html>
