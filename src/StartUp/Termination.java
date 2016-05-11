package StartUp;

import java.awt.event.*;
import java.io.*;

public class Termination extends WindowAdapter{
    @Override
    public void windowClosing (WindowEvent we) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("ips");
            ObjectOutputStream obj = new ObjectOutputStream(fos);
            obj.writeObject(NetworkActivity.Network.ips);
        } catch (FileNotFoundException ex) {
            Exceptions.exceptions("StartUp\\Termination", ex);
        } catch (IOException ex) {
            Exceptions.exceptions("StartUp\\Termination", ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Exceptions.exceptions("StartUp\\Termination", ex);
            }
        }
    }
}
