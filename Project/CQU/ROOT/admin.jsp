<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*,cn.edu.cqu.W14217100.*" %>
<jsp:include page="/inc/initdb.jsp" />
<%@ include  file="/inc/admin_verification.jsp" %>

<%
    DataManager dm = new DataManager();
%>

<html>
    <head>
        <title>后台</title>
    </head>
    <body>
        <h1>用户管理</h1>
<%
    String my_name = (String)session.getAttribute("user");
    for (Map user : dm.allUsers()) {
        if (my_name.equals(user.get("name").toString())) {
            continue;
        }
%>
        <p><%=user.get("id").toString()%>, <%=user.get("name").toString()%>
<%
        if (!user.get("rank").toString().equals("sa")) {
%>
            <a href="del_user.jsp?id=<%=user.get("id").toString()%>">删除</a>
<%
        }
        if (!user.get("rank").toString().equals("sa")) {
%>
            <a href="mod_user.jsp?id=<%=user.get("id").toString()%>">修改</a>
<%
        }
        if (user.get("rank").toString().equals("user")) {
%>
            <a href="set_rank.jsp?id=<%=user.get("id").toString()%>&rank=admin">提升为管理员</a>
<%
        }else if (user.get("rank").toString().equals("admin")) {
%>
            <a href="set_rank.jsp?id=<%=user.get("id").toString()%>&rank=user">降为普通会员</a>
<%
        }
%>
        </p>
<%
    }
%>
        <hr/>
        <h1>文章列表</h1>
<%
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
        <hr/>
        <h1>类别列表</h1>
<%
    for (Map list : dm.allLists()) {
%>
        <p><%=list.get("id").toString()%>, <%=list.get("name").toString()%>
            <a href="del_list.jsp?id=<%=list.get("id").toString()%>">删除</a>
            <a href="mod_list.jsp?id=<%=list.get("id").toString()%>">修改</a>
        </p>
<%
    }
%>
        <hr/>
        <h1>添加类别</h1>
        <form method="POST" action="add_list.jsp">
            <p>类别名</p>
            <input type="text" name="name" />
            <p>父级类别</p>
            <input type="text" name="parent" />
            <br/>
            <input type="submit" value="提交" />
        </form>
        <hr/>
        <h1>药品列表</h1>
<%
    for (Map drug : dm.allDrugs()) {
%>
        <p><%=drug.get("id").toString()%>, <%=drug.get("name").toString()%>
            <a href="del_drug.jsp?id=<%=drug.get("id").toString()%>">删除</a>
            <a href="mod_drug.jsp?id=<%=drug.get("id").toString()%>">修改</a>
        </p>
<%
    }
%>
        <hr/>
        <h1>添加药品</h1>
        <form method="POST" action="add_drug.jsp">
            <p>药品名</p>
            <input type="text" name="name" />
            <p>类别</p>
            <input type="text" name="list" />
            <p>介绍</p>
            <textarea rows="10" cols="30" name="introduction"></textarea>
            <br/>
            <input type="submit" value="提交" />
        </form>
    </body>
</html>

