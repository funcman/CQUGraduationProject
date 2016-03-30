<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    String name     = request.getParameter("name");
    String parent   = request.getParameter("parent");
    if (name != null && parent != null) {
        DataManager dm = new DataManager();
        dm.addList(name, parent);
    }
    response.sendRedirect("admin.jsp");
%>

