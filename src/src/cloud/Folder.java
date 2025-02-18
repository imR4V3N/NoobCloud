package cloud;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    private int id;
    private String name = "New Folder";
    private List<File> file;
    
    public Folder(){}
    public Folder(int id) {
        this.id = id;
    }
    public Folder(String name) {
        this.name = name;
    }
    public Folder(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Folder(int id, String name, List<File> file) {
        this.id = id;
        this.name = name;
        this.file = file;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<File> getFile() {
        return file;
    }
    public void setFile(List<File> file) {
        this.file = file;
    }

    public void create(String path) throws IOException{
        String folder = this.getName();
        Path newPath = Paths.get(path, folder);
        try {
            Files.createDirectories(newPath);
        } catch (RuntimeException e) {
            throw new IOException();
        }
    }

    public void save(Connection connection) throws Exception{
        PreparedStatement stmt = null;
        String query = "insert into folder values (default , ?)";

        try{
            connection.setAutoCommit(false);

            stmt = connection.prepareStatement(query);
            
            stmt.setString(1,this.getName());
            
            stmt.executeUpdate();
            connection.commit();

        }catch(RuntimeException e){
            connection.rollback();
            throw new Exception();
        }finally{
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void delete(String folder_path) throws Exception{
        Path folder = Paths.get(folder_path +"/"+ this.getName());
        if (Files.exists(folder)) {
            Files.walk(folder)
            .sorted((p1, p2) -> p2.compareTo(p1))
            .forEach(p -> {
                try {
                    Files.delete(p);
                } catch (Exception e) {
                    System.err.println("Error during deleting " + p + " : " + e.getMessage());   
                }
            });
        }
    }

    public void delete(Connection connection) throws Exception{
        String query = "delete from folder where id=?";
        PreparedStatement stmt = null;

        try{
            stmt=connection.prepareStatement(query);
            stmt.setInt(1,this.getId());
            
            stmt.executeUpdate();

        }catch(RuntimeException e){
            throw new Exception(); 
        }finally{
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public boolean rename_folder(String path, String newName) throws IOException{
        java.io.File oldFolder = new java.io.File(path);
        java.io.File newFolder = new java.io.File(newName);
        
        return oldFolder.renameTo(newFolder);
    }

    public void rename(Connection connection) throws Exception {
        PreparedStatement stmt=null;
        String sql="update folder set name=? where id=?";
        try{

            connection.setAutoCommit(false);
            
            stmt=connection.prepareStatement(sql);
            stmt.setString(1, this.getName());
            stmt.setInt(2, this.getId());
            
            stmt.executeUpdate();
            connection.commit();

        }catch(RuntimeException e){
            throw new Exception(); 
        }finally{
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static List<Folder> getAll(Connection connection)throws Exception{
        PreparedStatement stmt = null;
        ResultSet res = null;
        String sql="select * from folder";
        List<Folder> folders = new ArrayList<Folder>();

        try{
            stmt = connection.prepareStatement(sql);
            res = stmt.executeQuery();

            while(res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");

                Folder folder = new Folder(id, name);
                folders.add(folder);
            }
        }catch(RuntimeException e){
            throw new Exception(); 
        } finally{
            if (stmt != null) {
                stmt.close();
            }
        }
        return folders;
    }  

    public Folder getById(Connection connection)throws Exception{
        PreparedStatement stmt = null;
        ResultSet res = null;
        String sql="select * from folder where id = ?";
        Folder folder = null;

        try{
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, this.getId());
            res = stmt.executeQuery();

            while(res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");

                folder = new Folder(id, name);
            }
        }catch(RuntimeException e){
            throw new Exception(); 
        } finally{
            if (stmt != null) {
                stmt.close();
            }
        }
        return folder;
    } 

    public Folder getFiles(Connection connection) throws Exception{
        PreparedStatement stmt = null;
        ResultSet res = null;
        String sql="select * from file where idFolder = ?";
        List<File> files = new ArrayList<File>();
        Folder folder = null;
        int id = this.getId();
        String name = this.getName();

        try{
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, this.getId());
            res = stmt.executeQuery();

            while(res.next()){
                int idFile = res.getInt("id"); 
                String fileName = res.getString("name"); 
                Date date = res.getDate("date"); 
                double size = res.getDouble("size");

                File file = new File(idFile, fileName, date, size, id);
                files.add(file);
            }
            folder = new Folder(id, name, files);
        }catch(RuntimeException e){
            throw new Exception(); 
        } finally{
            if (stmt != null) {
                stmt.close();
            }
        }
        return folder;
    } 
}
