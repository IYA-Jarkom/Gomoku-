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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Kelas untuk membuat label
 */
public class Label extends JLabel {
    // Konstruktor label dengan ukuran default
    public Label(String text) {
        super(text, SwingConstants.CENTER);
        setForeground(Color.white);
        setFont(new Font("Jaapokki", Font.PLAIN, 30));
    }

    // Konstruktor label dengan ukuran sesuai masukkan pengguna
    public Label(String text, String color, int size) {
        super(text, SwingConstants.CENTER);
        if (color.equals("white")) {
            setForeground(Color.white);
            setFont(new Font("Jaapokki", Font.PLAIN, size));
        } else if (color.equals("green")) {
            setForeground(Color.decode("#E4FF45"));
            setFont(new Font("Jaapokki", Font.PLAIN, size));
        }
    }

    // Setter
    // Mengubah warna text pada label
    public void setColor(String color) {
        if (color.equals("white")) {
            setForeground(Color.white);
        } else if (color.equals("green")) {
            setForeground(Color.decode("#E4FF45"));
        }
    }

    // Mengubah icon pada label menjadi gambar dengan nama file sesuai masukkan pengguna
    public void setIcon(String fileName) {
        try {
            BufferedImage buttonIcon = ImageIO.read(new File("assets/" + fileName));
            ImageIcon imageIcon = new ImageIcon(buttonIcon);
            setIcon(imageIcon);
        } catch (IOException ex) {
            Logger.getLogger(ImageButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
