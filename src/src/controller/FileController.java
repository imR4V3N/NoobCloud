package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;

import cloud.File;
import cloud.Folder;
import database.Connexion;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import mg.framework.servlet.Controller;
import mg.framework.servlet.annotation.Mapping;
import path.Path;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 10,
    maxFileSize = 1024L * 1024L * 1024L * 10L,
    maxRequestSize = 1024L * 1024L * 1024L * 11L
)
@WebServlet(name = "FileController", value = "*.FileController")
public class FileController extends Controller{

    @Mapping("files")
    public void files() throws IOException {
        PrintWriter out = response.getWriter();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection connection = new Connexion().getConnexion();

            Folder folder = new Folder(id).getFiles(connection);
            request.setAttribute("folder", folder);

            HttpSession session = request.getSession();
            session.setAttribute("folder", folder.getById(connection));
            
            connection.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("file.jsp");
            dispatcher.forward(request, response);


        } catch (Exception e) {
            out.println(e.getMessage());
        }
 
    }

    @Mapping("delete")
    public void delete() throws IOException {
        PrintWriter out = response.getWriter();
        String project_path = getServletContext().getRealPath("/");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection connection = new Connexion().getConnexion();

            File file = new File();
            file.setId(id);
            
            File file2 = file.getById(connection);
            String folder = ((Folder)request.getSession().getAttribute("folder")).getName();
            
            file2.delete(connection);
            file2.delete(Path.getPath(project_path) +"/"+ folder);
            
            connection.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("files.FileController?id=" + file2.getIdFolder());
            dispatcher.forward(request, response);


        } catch (Exception e) {
            out.println(e.getMessage());
        }
 
    }

    @Mapping("download")
    public void download() throws IOException {
        PrintWriter out = response.getWriter();
        String project_path = getServletContext().getRealPath("/");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection connection = new Connexion().getConnexion();

            File file = new File();
            file.setId(id);
            
            File file2 = file.getById(connection);
            String folder = ((Folder)request.getSession().getAttribute("folder")).getName();
            
            file2.download(response, Path.getPath(project_path) +"/"+ folder);
            
            connection.close();
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("files.FileController");
            dispatcher.forward(request, response);


        } catch (Exception e) {
            out.println(e.getMessage());
        }
 
    }

    @Mapping("upload")
    public void upload() throws IOException {
        PrintWriter out = response.getWriter();
        String project_path = getServletContext().getRealPath("/");
        
        try {
            Connection connection = new Connexion().getConnexion();
            
            for (int i = 0; i < request.getParts().size(); i++) {
                Part filePart = request.getPart("file" + i);

                String fileName = request.getParameter("fileName" + i);
                String fileSize = request.getParameter("fileSize" + i);

                double size = Double.parseDouble(fileSize);
                Date date = Date.valueOf(java.time.LocalDate.now());
                int idFolder = ((Folder)request.getSession().getAttribute("folder")).getId();  

                File file = new File(fileName, date, size, idFolder);
                file.save(connection);

                String folderName = ((Folder)request.getSession().getAttribute("folder")).getName();
                file.upload(filePart, Path.getPath(project_path) +"/"+ folderName);

            }
            connection.close();
            
            out.write("Files uploaded successfully!");
            
        } catch (Exception e) {
            out.write("Error : " + e.getMessage());
        }
 
    }
}
