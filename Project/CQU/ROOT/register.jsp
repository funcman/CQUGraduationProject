<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />

<html>
<%
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    String pwd  = request.getParameter("pwd");
    if (name == null || pwd == null) {
%>
    <head>
        <title>注册新用户</title>
    </head>
    <body>
        <form method="POST">
            <p>用户名<input type="text" name="name" /></p>
            <p>密码<input type="password" name="pwd" /></p>
            <input type="submit" value="提交" />
        </form>
    </body>
<%
    }else {
        DataManager dm = new DataManager();
        dm.addUser(name, pwd);
%>
    <head>
        <meta http-equiv="refresh" content="5;url=/" />
        <title>注册新用户</title>
    </head>
    <body>
        <p>注册成功！5秒钟后调整至<a href="/">首页</a></p>
    </body>
<%
    }
%>
</html>

