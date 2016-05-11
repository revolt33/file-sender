package StartUp;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class MsgBar extends JPanel{
    char orient;
    public MsgBar (char orient) {
        this.orient = orient;
    }
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D graphics2D = (Graphics2D) g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHints(qualityHints);
        int width = getWidth();
        int height = getHeight();
        GeneralPath path = new GeneralPath();
        if (orient == 'l') {
            graphics2D.setPaint(new Color(80, 150, 180));
            path.moveTo(5, 10);
            path.curveTo(5, 10, 7, 5, 0, 0);
            path.curveTo(0, 0, 12, 0, 12, 5);
            path.curveTo(12, 5, 12, 0, 20, 0);
            path.lineTo(width - 10, 0);
            path.curveTo(width - 10, 0, width, 0, width, 10);
            path.lineTo(width, height - 10);
            path.curveTo(width, height - 10, width, height, width - 10, height);
            path.lineTo(15, height);
            path.curveTo(15, height, 5, height, 5, height - 10);
            path.lineTo(5, 10);
        } else {
            graphics2D.setPaint(new Color(180, 150, 180));
            path.moveTo(width - 5, 10);
            path.curveTo(width - 5, 10, width - 7, 5, width, 0);
            path.curveTo(width, 0, width - 12, 0, width - 12, 5);
            path.curveTo(width - 12, 5, width - 12, 0, width - 20, 0);
            path.lineTo(10, 0);
            path.curveTo(10, 0, 0, 0, 0, 10);
            path.lineTo(0, height - 10);
            path.curveTo(0, height - 10, 0, height, 10, height);
            path.lineTo(width - 15, height);
            path.curveTo(width - 15, height, width - 5, height, width - 5, height - 10);
            path.lineTo(width - 5, 10);
        }
        path.closePath();
        graphics2D.fill(path);
    }
}
