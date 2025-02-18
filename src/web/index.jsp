<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if(request.getSession() != null) {
        request.getSession().invalidate();
    }
    String pagePath = "folders.FolderController";
    response.sendRedirect(request.getContextPath()+"/"+pagePath);
%>