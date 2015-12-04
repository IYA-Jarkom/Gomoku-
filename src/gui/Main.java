/*
 * Test1
 * Each line should be prefixed with  * 
 test2
 */
package gui;

import gui.element.*;
import gui.page.*;
import gui.window.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * TUGAS BESAR 2
 * IF3130 Jaringan Komputer
 * -RETURN OF POI-
 * 
 * Kelas utama program dengan Graphical User Interface
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Yoga Adrian Saputra - 13513030
 * @author Angela Lynn - 13513032
 */
public class Main extends JFrame{
    public Main() {
        setTitle("WAL");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //BackgroundPanel bgPanel = new BackgroundPanel();
        
        //MenuPage page = new MenuPage();
        //add(page);
//        RoomPage roomPage = new RoomPage();
//        add(roomPage);
        JLayeredPane layers = new JLayeredPane();
        layers.setOpaque(false);
        layers.setLayout(null);
        //JPanel layers = new JPanel();
        RoomPage bgPanel = new RoomPage();
        RoleWindow roleWindow = new RoleWindow();
        //CharacterWindow characterWindow = new CharacterWindow();
        //OtherWinWindow window = new OtherWinWindow();
        EmptyWindow window = new EmptyWindow();
        //layers.add(roleWindow, new Integer(1));
        layers.add(window, new Integer(1));
        layers.add(bgPanel, new Integer(0));
        add(layers);
//        bgPanel.add(roleWindow);
//        add(bgPanel);
        setVisible(true);
    }
    
    public static void main (String[] args) {
        Main mainFrame = new Main();
    }
}
