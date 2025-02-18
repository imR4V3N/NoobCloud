package path;

public class Path {
    static String path = "cloud";

    public static String getPath(String project_path) {
        return project_path + path;
    }

    public static void setPath(String path) {
        Path.path = path;
    }
    
}
