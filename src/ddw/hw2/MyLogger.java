package ddw.hw2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MyLogger {

    public static void log(String message) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)))) {
            out.println(message);
        } catch (IOException e) {
            System.err.println("Log file problem.");
        }
    }

}
