<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />

<%
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    String pwd  = request.getParameter("pwd");
    if (name != null && pwd != null) {
        DataManager dm = new DataManager();
        if (dm.isValidUser(name, pwd)) {
            session.setAttribute("state", "logged_in");
            session.setAttribute("user", name);
        }
    }
    response.sendRedirect("/");
%>

