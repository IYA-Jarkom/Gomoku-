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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Kelas panel berisikan gambar background frame
 */
public class BackgroundPanel extends JPanel {
    // Atribut
    private BufferedImage img;

    // Konstruktor objek panel dengan background default, yaitu background tampilan home
    public BackgroundPanel() {
        try {
            BufferedImage bg = ImageIO.read(new File("assets/bg-home.jpg"));
            setBackgroundImage(bg);
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
    
    // Konstruktor objek panel dengan background fileName
    public BackgroundPanel(String fileName) {
        try {
            BufferedImage bg = ImageIO.read(new File("assets/" + fileName));
            setBackgroundImage(bg);
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
    
    // Method
    @Override
    public Dimension getPreferredSize() {
        BufferedImage img = getBackgroundImage();

        Dimension size = super.getPreferredSize();
        if (img != null) {
            size.width = Math.max(size.width, img.getWidth());
            size.height = Math.max(size.height, img.getHeight());
        }
        return size;
    }

    public BufferedImage getBackgroundImage() {
        return img;
    }

    public void setBackgroundImage(BufferedImage value) {
        if (img != value) {
            BufferedImage old = img;
            img = value;
            firePropertyChange("background", old, img);
            revalidate();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bg = getBackgroundImage();
        if (bg != null) {
            int x = (getWidth() - bg.getWidth()) / 2;
            int y = (getHeight() - bg.getHeight()) / 2;
            g.drawImage(bg, x, y, this);
        }
    }
}
