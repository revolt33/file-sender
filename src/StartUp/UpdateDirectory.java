package StartUp;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JTree;
import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;
public class UpdateDirectory extends MouseAdapter {

    JTree tree;
    JPanel panel;

    public UpdateDirectory(JTree tree, JPanel panel) {
        this.tree = tree;
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        int no = me.getClickCount();
        TreePath path = tree.getLeadSelectionPath();

        try {
            Object[] arr = null;
            arr = path.getPath();
            int i = 0;
            String str = "";
            for (Object p : arr) {
                if (i == 0) {
                    i++;
                    continue;
                }
                if (i == 1) {
                    i++;
                    str = p + ":\\";
                    continue;
                }
                if (i > 1) {
                    str += p + "\\";
                }
            }
            if (no == 1) {

                panel.removeAll();
                panel.invalidate();
                panel.validate();
                ImageIcon ico = new ImageIcon("folder.jpg");
                Image icon = ico.getImage();
                icon = icon.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                ico = new ImageIcon(icon);
                File file = new File(str);
                File[] dirs = file.listFiles();
                DiveInDirectories.history.add(new DiveInDirectories(null, file));
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
                            dir.setForeground(Color.WHITE);
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
                            panel.add(dir, gbc);
                        }
                    }
                }
                ParentWindow.shareArea.updateUI();
            }
        } catch (NullPointerException ex) {
            Exceptions.exceptions("StartUp\\UpdateDirectory", ex);
        }
    }
}