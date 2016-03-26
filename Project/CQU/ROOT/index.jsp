<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cn.edu.cqu.W14217100.*" %>

<html>
    <head>
        <title>Hi, CQU!</title>
    </head>
    <body>
        <p>Hello, CQU!</p>
<%
    for (int i=0; i<3; ++i) {
%>
    <p><%=i%></p>
<%
    }

    Test test = new Test();
    test.sqliteTest();
%>
    <%=test.theNumber()%>
    </body>
</html>

