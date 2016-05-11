package StartUp;

import java.io.*;

public class Exceptions {
    public static void exceptions(String dest, Throwable ex) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("Errors\\" + dest, true));
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace(pw);
        }
        ex.printStackTrace(pw);
        pw.flush();
        pw.close();
    }
}
