<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />

<%
    session.removeAttribute("state");
    session.removeAttribute("user");
    response.sendRedirect("/");
%>

