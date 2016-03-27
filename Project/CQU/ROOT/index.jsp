<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />

<html>
    <head>
        <title>Hi, CQU!</title>
    </head>
    <body>
        <p>Hello, CQU!</p>
        <p><a href="/register.jsp">注册新用户</a></p>
        <p>管理员列表:</p>
        <ul>
<%
    DataManager dm = new DataManager();
    for (String user_name : dm.allAdmin()) {
        out.println("<li>"+user_name+"</li>");
    }
%>
        </ul>
<%
    String state = (String)session.getAttribute("state");
    if (state == null || !state.equals("logged_in")) {
%>
        <form method="POST" action="login.jsp">
            <p>用户名<input type="text" name="name" /></p>
            <p>密码<input type="password" name="pwd" /></p>
            <input type="submit" value="登录" />
        </form>
<%
    }else {
        String user_name    = (String)session.getAttribute("user");
        String goto_admin   = "";
        if (dm.isAdmin(user_name)) {
            goto_admin = "<a href=\"admin.jsp\">[进入后台]</a>";
        }
%>
        <p>你好，<%=user_name%>！<a href="logout.jsp">[登出]</a><%=goto_admin%></p>
<%
    }
%>
    </body>
</html>

