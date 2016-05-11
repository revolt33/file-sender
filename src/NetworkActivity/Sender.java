package NetworkActivity;
import java.awt.GridBagConstraints;
import javax.swing.*;
import java.util.*;
import StartUp.*;
import java.awt.event.*;
import java.rmi.RemoteException;
public class Sender extends MouseAdapter{
    public static ArrayList<FileList> fileList;
    static int y;
    @Override
    public void mouseClicked(MouseEvent me) {
        if (fileList != null) {
            ListIterator<FileList> it = fileList.listIterator();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = y;
            while (it.hasNext()) {
                FileList obj = it.next();
                JLabel name = new JLabel(obj.getFile().getName());
                JProgressBar bar = new JProgressBar(0, 100);
                bar.setStringPainted(true);
                gbc.gridx = 0;
                ParentWindow.progress.add(name, gbc);
                gbc.gridx = 1;
                ParentWindow.progress.add(bar, gbc);
                try {
                    if (obj.getReceiver().connection()) {
                        SendFile send = new SendFile(obj.getReceiver(), obj.getFile(), bar);
                        send.start();
                    }
                } catch (RemoteException ex) {
                    bar.setString("Failed!");
                    JOptionPane.showMessageDialog(ParentWindow.parent, "Sending failed, of File: "+obj.getFile()+". Receiver Unaivalable");
                }
                gbc.gridy++;
            }
            y = gbc.gridy;
            ParentWindow.parent.invalidate();
            ParentWindow.parent.validate();
            fileList = null;
        } else {
            JOptionPane.showMessageDialog(ParentWindow.parent, "Please select some files first!");
        }
    }
}