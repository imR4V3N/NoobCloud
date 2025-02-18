package controller;

import mg.framework.servlet.Controller;
import mg.framework.servlet.annotation.Mapping;
import path.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import cloud.Folder;
import database.Connexion;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "FolderController", value = "*.FolderController")
public class FolderController extends Controller{

    @Mapping("folders")
    public void folders() throws IOException {
        PrintWriter out = response.getWriter();
        
        try {
            Connection connection = new Connexion().getConnexion();
            
            List<Folder> list_folder = Folder.getAll(connection);
            request.setAttribute("list_folder", list_folder);
            
            connection.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("folder.jsp");
            dispatcher.forward(request, response);


        } catch (Exception e) {
            out.println(e.getMessage());
        }
 
    }

    @Mapping("add")
    public void add() throws IOException {
        PrintWriter out = response.getWriter();
        String project_path = getServletContext().getRealPath("/");
        
        try {
            String name = request.getParameter("name");

            Connection connection = new Connexion().getConnexion();
            
            Folder folder = new Folder(name);
            folder.create(Path.getPath(project_path));
            folder.save(connection);

            connection.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("folders.FolderController");
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
            
            Folder folder = new Folder(id);
            Folder folder2 = folder.getById(connection); 

            folder2.delete(Path.getPath(project_path));
            folder2.delete(connection);

            connection.close();
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("folders.FolderController");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }
}
