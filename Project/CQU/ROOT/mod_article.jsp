<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*,cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />

<%
    request.setCharacterEncoding("UTF-8");
    String id       = request.getParameter("id");
    String name     = request.getParameter("name");
    String content  = request.getParameter("content");
    if (id != null && (name == null || content == null)) {
        DataManager dm      = new DataManager();
        Map article         = dm.getArticle(id);
%>
<html>
    <head>
        <title>修改文章</title>
    </head>
    <body>
        <form method="POST" action="mod_article.jsp?id=<%=id%>">
            <p>文章名<input type="text" name="name"  value="<%=article.get("name").toString()%>"/></p>
            <p>内容<textarea rows="10" cols="30" name="content"><%=article.get("content").toString()%></textarea></p>
            <input type="submit" value="提交" />
        </form>
    </body>
</html>
<%
    }else {
        if (id != null && name != null && content != null) {
            DataManager dm = new DataManager();
            dm.modifyArticle(id, name, content);
        }
        response.sendRedirect("admin.jsp");
    }
%>

