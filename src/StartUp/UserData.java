package StartUp;
import javax.swing.*;
import java.io.*;
public class UserData implements Serializable{
    public String name;
    public ImageIcon profile;
    public UserData (String name, ImageIcon profile) {
        this.profile = profile;
        this.name = name;
    }
}