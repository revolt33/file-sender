package StartUp;
import NetworkActivity.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
public class DiveInDirectories extends MouseAdapter{
    JPanel panel;
    File folder;
    static JPanel shareArea = ParentWindow.shareArea;
    static ArrayList<DiveInDirectories> history = new ArrayList<>();
    public DiveInDirectories (JPanel panel, File folder) {
        this.panel = panel;
        this.folder = folder;
    }
    @Override
    public void mouseEntered (MouseEvent me) {
        panel.setOpaque(true);
        shareArea.updateUI();
    }
    @Override
    public void mouseExited (MouseEvent me) {
        panel.setOpaque(false);
        shareArea.updateUI();
    }
    @Override
    public void mouseClicked (MouseEvent me) {
        if (me.getButton() == 3) {
            JFileChooser choose = new JFileChooser(folder);
            choose.setMultiSelectionEnabled(true);
            choose.showOpenDialog(ParentWindow.parent);
            File[] files = choose.getSelectedFiles();
            if (Sender.fileList == null)
                Sender.fileList = new ArrayList<>();
            if (Network.obj != null) {
                if (Receiver.obj != null) {
                    for (File file : files) {
                        Sender.fileList.add(new FileList(file, Receiver.obj));
                    }
                }
            }
        } else if (me.getButton() == 1) {
            update();
        }
    }
    void update () {
        shareArea.removeAll();
            shareArea.invalidate();
            shareArea.validate();
            ImageIcon ico = new ImageIcon("folder.jpg");
            Image icon = ico.getImage();
            icon = icon.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            ico = new ImageIcon(icon);
            File[] dirs = folder.listFiles();
            history.add(this);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 2, 2, 2);
            String dummy = "";
            int j = 0;
            for (File s : dirs) {
                if (!s.isHidden()) {
                    if (s.isDirectory()) {
                        gbc.gridx = j % 6;
                        gbc.gridy = j / 6;
                        j++;
                        JPanel dir = new JPanel(new BorderLayout());
                        dir.setForeground(Color.yellow);
                        dir.setOpaque(false);
                        dir.addMouseListener(new DiveInDirectories(dir, s));
                        JLabel folder = new JLabel(ico);
                        JPanel namePanel = new JPanel(new FlowLayout());
                        dummy = s.getName();
                        dir.setToolTipText(dummy);
                        if (dummy.length() > 8) {
                            dummy = dummy.substring(0, 7) + "..";
                        }
                        JLabel name = new JLabel(dummy);
                        namePanel.add(name);
                        dir.add(folder, BorderLayout.CENTER);
                        dir.add(namePanel, BorderLayout.SOUTH);
                        shareArea.add(dir, gbc);
                    }
                }
            }
            shareArea.updateUI();
    }
}