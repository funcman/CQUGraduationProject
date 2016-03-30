<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*,cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    String id           = request.getParameter("id");
    String name         = request.getParameter("name");
    String list         = request.getParameter("list");
    String introduction = request.getParameter("introduction");
    if (id != null && (name == null || list == null || introduction == null)) {
        DataManager dm  = new DataManager();
        Map drug        = dm.getDrug(id);
%>
<html>
    <head>
        <title>修改药品信息</title>
    </head>
    <body>
        <form method="POST" action="mod_drug.jsp?id=<%=id%>">
            <p>药品名</p>
            <input type="text" name="name" value="<%=drug.get("name").toString()%>"/>
            <p>类别</p>
            <input type="text" name="list" value="<%=drug.get("list").toString()%>"/>
            <p>介绍</p>
            <textarea rows="10" cols="30" name="introduction"><%=drug.get("introduction").toString()%></textarea>
            <br/>
            <input type="submit" value="提交" />
        </form>
    </body>
</html>
<%
    }else {
        if (id != null && name != null && list != null && introduction != null) {
            DataManager dm = new DataManager();
            dm.modifyDrug(id, name, list, introduction);
        }
        response.sendRedirect("admin.jsp");
    }
%>

