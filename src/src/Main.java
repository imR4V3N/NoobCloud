import java.io.IOException;

import cloud.Folder;
import path.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Folder folder = new Folder("New Folder");
        folder.create(Path.getPath("./Noob-Cloud"));
    }
}
