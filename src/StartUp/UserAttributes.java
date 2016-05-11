package StartUp;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.*;
import java.io.*;
public class UserAttributes extends JDialog{
    static JTextField you;
    static JLabel image;
    static UserAttributes window;
    public UserAttributes (ParentWindow frame) {
        super (frame, "You", true);
        window = this;
    }
    public void showDialog () {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        centerPoint.x -= 200;
        centerPoint.y -= 125;
        this.setLocation(centerPoint);
        this.setLayout(new BorderLayout());
        this.setSize(400, 250);
        JPanel panel = new JPanel(new GridBagLayout());
        this.add(panel);
        JLabel select = new JLabel("Your Profile Picture:");
        UserData data = null;
        try {
            data = (UserData) new ReadFile(new FileInputStream("UserData")).readObject();
        } catch (FileNotFoundException ex) {
            Exceptions.exceptions("StartUp\\UserAttributes", ex);
        } catch (IOException | ClassNotFoundException ex) {
            Exceptions.exceptions("StartUp\\UserAttributes", ex);
        }
        image = new JLabel(data.profile);
        JButton browse = new JButton("Change");
        browse.addActionListener(new BrowseProfilePic());
        JLabel name = new JLabel("Your Profile Name:");
        JButton save = new JButton("Save");
        you = new JTextField(9);
        you.setText(data.name);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(select, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(image, gbc);
        gbc.gridx = 2;
        panel.add(browse, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(name, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(you, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(save, gbc);
        save.addActionListener(new Actions(4));
        this.setVisible(true);
    }
}