/*
 * Test1
 * Each line should be prefixed with  * 
 test2
 */
package gui.element;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 * 
 * Kelas panel berisikan gambar
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class ImagePanel extends JPanel {
    // Atribut
    private BufferedImage image;
    
    // Konstruktor objek panel gambar dengan masukkan nama file gambar pada folder "asssets"
    public ImagePanel(String imageName) {
        try {
            image = ImageIO.read(new File("assets/" + imageName));
        } catch (IOException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
