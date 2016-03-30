<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    String name     = request.getParameter("name");
    String content  = request.getParameter("content");
    if (name != null && content != null) {
        DataManager dm = new DataManager();
        dm.addArticle(name, content);
    }
    response.sendRedirect("admin.jsp");
%>

