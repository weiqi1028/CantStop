/**
 * @author Dan Han, Weiqi Zhang
 * Jpanel with a background picture
 */

import java.awt.*;
import javax.swing.*;

class ImagePanel extends JPanel {
    private Image backgroundImage;
    ImagePanel(ImageIcon image) {
        backgroundImage = image.getImage();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
