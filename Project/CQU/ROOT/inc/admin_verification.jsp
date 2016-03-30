<%
    {
        String state    = (String)session.getAttribute("state");
        String user     = (String)session.getAttribute("user");
        if (state == null || !state.equals("logged_in") || !(new DataManager()).isAdmin(user)) {
            response.sendRedirect("/");
            return;
        }
    }
%>

