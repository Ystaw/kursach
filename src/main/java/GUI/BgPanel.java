package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BgPanel extends JPanel {
    public void paintComponent(Graphics g){
        Image im = null;
        try {
            im = ImageIO.read(getClass().getResource("/images/v1064-32.jpg")); //установление заднего фона
        } catch (IOException e) {}
        g.drawImage(im, 0, 0, null);
    }
}