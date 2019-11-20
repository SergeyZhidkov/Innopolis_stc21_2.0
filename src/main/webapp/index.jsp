<%@page contentType="text/html" pageEncoding="UTF-8"%> <!
DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="web.FolderBean" %>
<html>
<head><title>Домашний каталог:</title></head>
<body>
<%!FolderBean FolderBean;%>
<%
    Context context = null;
    try {
        context = new InitialContext();
        FolderBean = (FolderBean) context.lookup(
                "java:app/Home_Work/FolderBean!web.FolderBean"); }
    catch(Exception e) {
        e.printStackTrace();
    }
%><p>
<h1>Текущий каталог:<%= System.getProperty("user.dir")%></h1>
<%= FolderBean.startMethod() %><br>
</body>
</html>