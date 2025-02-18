package cloud;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class File {
    private int id;
    private String name;
    private Date date;
    private double size;
    private int idFolder;


    public File(){}
    public File(String name, Date date, double size, int idFolder) {
        this.name = name;
        this.date = date;
        this.size = size;
        this.idFolder = idFolder;
    }
    public File(int id, String name, Date date, double size, int idFolder) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.size = size;
        this.idFolder = idFolder;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    } 
    public int getIdFolder() {
        return idFolder;
    }
    public void setIdFolder(int idFolder) {
        this.idFolder = idFolder;
    }

    public boolean delete(String filePath) {
        java.io.File file = new java.io.File(filePath +"/"+ this.getName());
        if (file.exists()) {
            file.deleteOnExit(); 
        } else {
            return false;
        }
        return false;
    }

    public void upload(Part part, String directory) throws IOException {
        String fileName = this.getName();

        java.io.File fileDirectory = new java.io.File(directory);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        java.io.File file = new java.io.File(fileDirectory, fileName);
        try (InputStream input = part.getInputStream()){
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (RuntimeException e) {
            throw new IOException();
        }
    }

    public void download(HttpServletResponse response, String filePath) throws Exception {
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+this.getName());
            response.setContentType("text/plain");
            InputStream is = new FileInputStream(filePath +"/"+ this.getName());
            OutputStream os = response.getOutputStream();
            int count;
            byte buf[] = new byte[4096];
            while ((count = is.read(buf)) > -1)
                os.write(buf, 0, count);
            is.close();
            os.close();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void save(Connection connection) throws Exception{
        PreparedStatement stmt = null;
        String query = "insert into file values (default , ?, ?, ?, ? )";

        try{
            connection.setAutoCommit(false);

            stmt = connection.prepareStatement(query);
            
            stmt.setString(1,this.getName());
            stmt.setDate(2, this.getDate()); 
            stmt.setDouble(3, this.getSize()); 
            stmt.setInt(4, this.getIdFolder()); 
            
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

    public void delete(Connection connection) throws Exception{
        PreparedStatement stmt = null;
        String query = "delete from file where id=?";

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

    public File getById(Connection connection)throws Exception{
        PreparedStatement stmt = null;
        ResultSet res = null;
        String sql="select * from file where id = ?";
        File file = null;

        try{
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, this.getId());
            res = stmt.executeQuery();

            while(res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");
                Date date = res.getDate("date");
                double size = res.getDouble("size");
                int idFolder = res.getInt("idFolder");

                file = new File(id, name, date, size, idFolder);
            }
        }catch(RuntimeException e){
            throw new Exception(); 
        } finally{
            if (stmt != null) {
                stmt.close();
            }
        }
        return file;
    } 

}
