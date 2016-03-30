<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*,cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    String id           = request.getParameter("id");
    String name         = request.getParameter("name");
    String pwd          = request.getParameter("pwd");
    if (id != null && (name == null || pwd == null)) {
        DataManager dm  = new DataManager();
        Map user        = dm.getUser(id);
%>
<html>
    <head>
        <title>修改用户信息</title>
    </head>
    <body>
        <form method="POST" action="mod_user.jsp?id=<%=id%>">
            <p>用户名</p>
            <input type="text" name="name" value="<%=user.get("name").toString()%>"/>
            <p>密码</p>
            <input type="password" name="pwd" value="<%=user.get("pwd").toString()%>"/>
            <br/>
            <input type="submit" value="提交" />
        </form>
    </body>
</html>
<%
    }else {
        if (id != null && name != null && pwd != null) {
            DataManager dm = new DataManager();
            dm.modifyUser(id, name, pwd);
        }
        response.sendRedirect("admin.jsp");
    }
%>

