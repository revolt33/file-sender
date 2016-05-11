package NetworkActivity;
import java.io.*;
public class FileList {
    private File file;
    private FileIntf ser;
    public FileList(File file, FileIntf ser) {
        this.file = file;
        this.ser = ser;
    }
    public File getFile () {
        return file;
    }
    public FileIntf getReceiver () {
        return ser;
    }
}