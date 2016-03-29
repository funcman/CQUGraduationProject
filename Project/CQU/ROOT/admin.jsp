<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*,cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />

<html>
    <head>
        <title>后台</title>
    </head>
    <body>
        <h1>文章列表</h1>
<%
    DataManager dm = new DataManager();
    for (Map article : dm.allArticles()) {
%>
        <p><%=article.get("id").toString()%>, <%=article.get("name").toString()%>
            <a href="del_article.jsp?id=<%=article.get("id").toString()%>">删除</a>
            <a href="mod_article.jsp?id=<%=article.get("id").toString()%>">修改</a>
        </p>
<%
    }
%>
        <hr/>
        <h1>添加文章</h1>
        <form method="POST" action="add_article.jsp">
            <p>文章名</p>
            <input type="text" name="name" />
            <p>内容</p>
            <textarea rows="10" cols="30" name="content"></textarea>
            <br/>
            <input type="submit" value="提交" />
        </form>
    </body>
</html>

