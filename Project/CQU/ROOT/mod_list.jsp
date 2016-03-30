<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*,cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    request.setCharacterEncoding("UTF-8");
    String id           = request.getParameter("id");
    String name         = request.getParameter("name");
    String parent       = request.getParameter("parent");
    if (id != null && (name == null || parent == null)) {
        DataManager dm  = new DataManager();
        Map list        = dm.getList(id);
%>
<html>
    <head>
        <title>修改药品类目</title>
    </head>
    <body>
        <form method="POST" action="mod_list.jsp?id=<%=id%>">
            <p>类别名</p>
            <input type="text" name="name" value="<%=list.get("name").toString()%>"/>
            <p>父级类别</p>
            <input type="text" name="parent" value="<%=list.get("parent").toString()%>"/>
            <br/>
            <input type="submit" value="提交" />
        </form>
    </body>
</html>
<%
    }else {
        if (id != null && name != null && parent != null) {
            DataManager dm = new DataManager();
            dm.modifyList(id, name, parent);
        }
        response.sendRedirect("admin.jsp");
    }
%>

