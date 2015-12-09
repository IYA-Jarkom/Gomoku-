/*
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
package gui.element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Kelas tombol dengan ikon gambar
 */
public class ImageButton {
    JButton button;
    // Konstruktor objek tombol dengan icon gambar yang diberikan
    public ImageButton(String fileName) {
        try {
            BufferedImage buttonIcon = ImageIO.read(new File("assets/" + fileName));
            ImageIcon imageIcon = new ImageIcon(buttonIcon);
            button = new JButton(imageIcon);
        } catch (IOException ex) {
            Logger.getLogger(ImageButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
    }
    
    // Getter
    public JButton getButton() {
        return button;
    }
}
