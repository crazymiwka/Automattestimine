package ee.ttu.automaattestimine.main;

import java.io.*;
import java.nio.file.*;

public class Reader {
    public String read(Path path){
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
