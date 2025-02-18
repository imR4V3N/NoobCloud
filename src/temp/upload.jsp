<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Noob Cloud</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/font/font.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/color.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/header.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/upload.css">
    <link rel="icon" href="<%=request.getContextPath()%>/assets/image/logo.png" type="web icon">
</head>

<body>
    <header>
        <a href="<%=request.getContextPath()%>/index.jsp"><img src="<%=request.getContextPath()%>/assets/image/logo.png"></a>
    </header>
    <main>
        <form id="uploadForm" action="upload.FileController" method="post" enctype="multipart/form-data">
            <div class="container">
                <table>
                    <thead>
                        <tr>
                            <th>Select</th>
                            <th>File name</th>
                            <th>File size</th>
                            <th>Upload status</th>
                        </tr>
                    </thead>
                    <tbody id="fileTableBody"></tbody>
                </table>
            </div>
            <div class="button">
                <label for="fileInput">
                    <input type="file" id="fileInput" multiple style="display: none;">
                    <span class="material-icons-sharp">create_new_folder</span>
                    <p>ADD</p>
                </label>
                <button type="button" id="uploadButton">
                    <span class="material-icons-sharp">file_upload</span>
                    <p>UPLOAD</p>
                </button>
            </div>
        </form>
    </main>

    <script src="<%=request.getContextPath()%>/assets/js/upload.js"></script>
</body>

</html>
