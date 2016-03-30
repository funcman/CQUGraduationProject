<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    String id   = request.getParameter("id");
    String rank = request.getParameter("rank");
    if (id != null && rank != null) {
        DataManager dm = new DataManager();
        if (rank.equals("admin")) {
            dm.incUserRank(id);
        }else if (rank.equals("user")) {
            dm.decUserRank(id);
        }
    }
    response.sendRedirect("admin.jsp");
%>

