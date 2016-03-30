<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    String name         = request.getParameter("name");
    String list         = request.getParameter("list");
    String introduction = request.getParameter("introduction");
    if (name != null && list != null && introduction != null) {
        DataManager dm = new DataManager();
        dm.addDrug(name, list, introduction);
    }
    response.sendRedirect("admin.jsp");
%>

