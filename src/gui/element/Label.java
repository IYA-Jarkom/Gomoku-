/*
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * - RETURN OF POI -
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
package gui.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * Kelas untuk membuat label
 */
public class Label extends JLabel{
    // Konstruktor label dengan ukuran default
    public Label(String text) {
        super(text);
        setForeground(Color.white);
        setFont(new Font("Jaapokki", Font.PLAIN, 30));
    }
    // Konstruktor label dengan ukuran sesuai masukkan pengguna
    public Label(String text, String color, int size) {
        super(text);
        if (color.equals("white")) {
            setForeground(Color.white);
            setFont(new Font("Jaapokki", Font.PLAIN, size));
        } else if (color.equals("green")) {
            setForeground(Color.decode("#E4FF45"));
            setFont(new Font("Jaapokki", Font.PLAIN, size));
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
