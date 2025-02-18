# NoobCloud

NoobCloud is a Java Servlet-based file management application similar to Google Drive. It allows users to create and manage folders, upload and download files, and organize their content efficiently.

## Features

- **Folder Management**: Create, list, and delete folders.
- **File Management**: List files within a folder, download or delete selected files.
- **File Upload**:
  - Upload files through a dedicated upload page.
  - Supports multiple file selection before uploading.

## Technologies Used

- **Java 17**
- **Apache Tomcat 10** (configured to allow file uploads up to **10GB**)
- **PostgreSQL** (for database storage)

## Project Setup

### 1. Prerequisites

Ensure you have the following installed:

- **Java 17**
- **Apache Tomcat 10**
- **PostgreSQL**

### 2. Database Setup

- Database scripts are located in the **database** directory.
- Execute the scripts in your PostgreSQL database before running the application.

### 3. Build & Deployment

To build the project, simply run:

```sh
Build.bat
```

You just have to configure the path in the script to your tomcat server

```
set destination=C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps**
```
