<%@ page import="cloud.Folder" %>
<%@ page import="cloud.File" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  List<Folder> list_folder = (List<Folder>) request.getAttribute("list_folder");
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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/index.css">
    <link rel="icon" href="<%=request.getContextPath()%>/assets/image/logo.png" type="web icon">
</head>

<body>
    <header>
        <a href="<%=request.getContextPath()%>/index.jsp"><img src="<%=request.getContextPath()%>/assets/image/logo.png"></a>
    </header>
    <main>
        <div class="container">
            <% for(Folder folder : list_folder) { %>
                <div class="card">
                    <a href="<%=request.getContextPath()%>/files.FileController?id=<%=folder.getId() %>" class="icon">
                        <span class="material-icons-sharp">folder</span>
                    </a>
                    <div class="info">
                        <h3><%=folder.getName() %></h3>
                        <div class="menu-icon" onclick="toggleMenu(event)" title="Menu">
                            &#8942;
                        </div>
                    </div>
                    <div class="menu" id="menu">
                        <ul>
                            <li><a href="<%=request.getContextPath()%>/delete.FolderController?id=<%=folder.getId() %>">Delete</a></li>
                        </ul>
                    </div>
                </div>
           <% }
            %>
        </div>
        <a href="#" class="add-btn" title="Add Folder">
            <span class="material-icons-sharp">add</span>
        </a>
    </main>
    <section class="add-section">
        <form action="<%=request.getContextPath()%>/add.FolderController" method="POST">
            <h1>Add new folder</h1>
            <input type="text" name="name" id="name" value="New Folder">
            <div class="button">
                <button type="submit">Add</button>
                <button type="reset" id="cancel">Cancel</button>
            </div>
        </form>
    </section>
</body>
<script src="<%=request.getContextPath()%>/assets/js/toggleMenu.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/add.js"></script>
</html>