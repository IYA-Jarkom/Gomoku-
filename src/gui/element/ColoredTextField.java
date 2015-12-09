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
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * Kelas TextField dengan border melingkar dan berwarna hijau
 */
public class ColoredTextField extends JTextField {
    // Atribut
    
    // Kosntruktor
    public ColoredTextField() {
        super(10);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.decode("#E4FF45"), 9, true));
        setForeground(Color.decode("#E4FF45"));
        setFont(new Font("Jaapokki", Font.PLAIN, 30));
        setHorizontalAlignment(JTextField.CENTER);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
