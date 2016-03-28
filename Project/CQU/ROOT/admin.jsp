<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*,cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />

<html>
    <head>
        <title>后台</title>
    </head>
    <body>
<%
    DataManager dm = new DataManager();
    for (Map article : dm.allArticles()) {
%>
        <p><%=article.get("id").toString()%>, <%=article.get("name").toString()%>
            <a href="del_article.jsp?id=<%=article.get("id").toString()%>">删除</a>
        </p>
<%
    }
%>
        <form method="POST" action="add_article.jsp">
            <p>文章名<input type="text" name="name" /></p>
            <p>内容<textarea rows="10" cols="30" name="content"></textarea></p>
            <input type="submit" value="提交" />
        </form>
    </body>
</html>

