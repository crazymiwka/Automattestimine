package ee.ttu.automaattestimine.main;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Writer {
    public boolean write(String filename, List<String> contents){
        try {
            Files.write(Paths.get("D:\\Backup Miwka\\IdeaProjects\\WeatherForecast\\src\\ee\\ttu\\automaattestimine\\main\\" + filename + ".txt"),
                    contents);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}