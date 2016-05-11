package NetworkActivity;
import StartUp.ParentWindow;
import java.io.*;
import StartUp.Exceptions;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
public class SendFile extends Thread{
    File file;
    FileIntf receiver;
    JProgressBar bar;
    public SendFile(FileIntf receiver, File file, JProgressBar bar) {
        this.file = file;
        this.bar = bar;
        this.receiver = receiver;
    }
    @Override
    public void run () {
        send(file);
    }
    public  void send(File file) {
        if (receiver != null) {
            int len = (int) file.length();
            int loop = len / 1048576;
            int per = len / 99;
            int cal, temp;
            cal = temp = 0;
            bar.setStringPainted(true);
            int rem = len % 1048576;
            String name = file.getName();
            synchronized (receiver) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    boolean bool = receiver.fileOpen(name);
                    if (bool) {
                        byte[] b = new byte[1048576];
                        for (int i = 0; i < loop; i++) {
                            if ( receiver.connection()) {
                                fis.read(b);
                                receiver.store(b);
                                cal = (1048576 + i * 1048576) / per;
                                if (cal > temp) {
                                    temp++;
                                    bar.setValue(temp);
                                    bar.setString(temp + " % completed");
                                }
                            } else {
                                bar.setString("Failed!");
                                JOptionPane.showMessageDialog(ParentWindow.parent, "Sending failed of File: " + file + ". Receiver Unaivalable");
                                break;
                            }
                        }
                        if ( rem > 0) {
                            b = new byte[rem];
                            fis.read(b);
                            receiver.store(b);
                        }
                        bar.setValue(100);
                        bar.setString("Finished");
                        receiver.closeFile();
                    } else {
                        JOptionPane.showMessageDialog(ParentWindow.parent, "This file already exists at receiver's computer");
                        bar.setValue(100);
                        bar.setString("Failed");
                    }
                    fis.close();
                } catch (EOFException ex) {
                    Exceptions.exceptions("NetworkActivity\\SendFile", ex);
                } catch (FileNotFoundException ex) {
                    Exceptions.exceptions("NetworkActivity\\SendFile", ex);
                } catch (IOException ex) {
                    Exceptions.exceptions("NetworkActivity\\SendFile", ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(ParentWindow.parent, "Please select a computer first!");
        }
    }
}