package StartUp;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.awt.Image.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class BrowseProfilePic implements ActionListener{
    ImageIcon profile;
    @Override
    public void actionPerformed (ActionEvent  ae) {
        JFileChooser choose = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "png", "jpeg");
        choose.addChoosableFileFilter(filter);
        choose.setAcceptAllFileFilterUsed(false);
        choose.setFileFilter(filter);
        choose.showOpenDialog(ParentWindow.parent);
        File image = choose.getSelectedFile();
        if (image != null) {
            profile = new ImageIcon(image.getPath());
            profile.setImage(profile.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
            UserAttributes.image.setIcon(profile);
        }
    }
}