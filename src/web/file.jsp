<%@ page import="cloud.Folder" %>
<%@ page import="cloud.File" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Folder folder = (Folder) request.getAttribute("folder");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Noob Cloud</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/font/font.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/color.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/file.css">
    <link rel="icon" href="<%=request.getContextPath()%>/assets/image/logo.png" type="web icon">
</head>

<body>
    <header>
        <a href="<%=request.getContextPath()%>/index.jsp"><img src="<%=request.getContextPath()%>/assets/image/logo.png"></a>
    </header>
    <main>
        <div class="container">
        <% for(File file : folder.getFile()) { %>
                <div class="card">
                    <div class="icon">
                        <span class="material-icons-sharp" id="insert_drive_file">insert_drive_file</span>
                        <div class="action">
                            <a href="<%=request.getContextPath()%>/delete.FileController?id=<%=file.getId() %>" title="Delete file"><span class="material-icons-sharp">delete</span></a>
                            <a href="<%=request.getContextPath()%>/download.FileController?id=<%=file.getId() %>" title="Download file"><span class="material-icons-sharp">download</span></a>
                        </div>
                    </div>
                    <div class="info">
                        <p class="file-name"><%=file.getName() %></p>
                        <div class="file-detail">
                            <p><%=file.getDate() %></p>
                            <p><%=file.getSize() %> MB</p>
                        </div>
                    </div>
                </div>
          <% } %>
        </div>
        <a href="<%=request.getContextPath()%>/upload.jsp" class="add-btn" title="Upload file">
            <span class="material-icons-sharp">file_upload</span>
        </a>
    </main>
</body>
</html>