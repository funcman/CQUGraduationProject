<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    String id = request.getParameter("id");
    if (id != null) {
        DataManager dm = new DataManager();
        dm.deleteList(id);
    }
    response.sendRedirect("admin.jsp");
%>

