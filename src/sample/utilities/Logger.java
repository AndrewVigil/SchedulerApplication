package sample.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


public class Logger {
    private static final String FILENAME = "login_activity.txt";

    public Logger() {}

    public static void log (String username, boolean success) {
        try (FileWriter fw = new FileWriter(FILENAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
             pw.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failure"));
        } catch (IOException e) {
            System.out.println("Logger Error: " + e.getMessage());
        }
    }
}
